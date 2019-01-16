package feedio.v2.crawler.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.NotFound;

import feedio.v2.data.db.model.OnlineLinkDao;
import feedio.v2.data.db.model.OnlineSourceDao;
import feedio.v2.data.db.repository.OnlineLinkRepository;
import feedio.v2.data.elastic.model.OnlineLinkDocument;
import feedio.v2.data.elastic.repository.OnlineLinkSearch;

@Service
public class CrawlerJauntiumService {
	Logger logger = LoggerFactory.getLogger(CrawlerJauntiumService.class);

	@Value("${chrome.binary.path}")
	private String chromeBinaryPath;

	// TODO Extract the use of repo into a service
	@Autowired
	private OnlineLinkRepository onlineLinkTransactionService;
	@Autowired
	private OnlineLinkSearch onlineLinkSearch;

	public void crawlSource(OnlineSourceDao onlineSourceDao) {

		logger.info("[CRAWLER_SERVICE][START]" + onlineSourceDao.getUrl());
		WebDriver jauntiumWebDriver = this.getDriver();
		Browser browser = new Browser(jauntiumWebDriver);

		logger.info("[CRAWLER_SERVICE][VISIT_START]" + LocalDateTime.now().toString());

		browser.visit(onlineSourceDao.getUrl());

		logger.info("[CRAWLER_SERVICE][VISIT_END]" + LocalDateTime.now().toString());

		int attempts = 5;
		// Multiple attempts in case the driver has issues with the state of the page (element no more available for ex)
		while (attempts > 0) {
			try {
				String baseHref = null;
				try {
					Element baseElement = browser.doc.findFirst("<base>");
					baseHref = baseElement.getAt("href");
				} catch (NotFound e) {
				}

				final Optional<String> oBaseHref = Optional.ofNullable(baseHref);

				// Get all the page href links
				List<Optional<String>> hrefs = StreamSupport.stream(browser.doc.findEvery("<a>").spliterator(), false)
					.filter(Objects::nonNull)
					.map(element -> JauntiumHelper.getHrefAttribute(element))
					.filter(oHref -> oHref.isPresent())
					.collect(Collectors.toList());

				// Create Dao online link objects
				// TODO : use a query that does only one request to DB for checking existing url
				List<OnlineLinkDao> onlineLinkDaos = hrefs.stream()
					.map(href -> JauntiumHelper.buildUrl(onlineSourceDao.getUrl(), onlineSourceDao.getUrl(), href,
							oBaseHref))
					.filter(link -> link.isPresent())
					.map(oLink -> oLink.get())
					.filter(link -> JauntiumHelper.belongsToDomain(link, onlineSourceDao.getDomain()))
					// TODO move this check in a function
					.filter(link -> this.match(link, onlineSourceDao.getPattern())
							&& !this.match(link, onlineSourceDao.getExcludedPattern()))
					.distinct() // Delete duplicate urls
					.filter(url -> onlineLinkTransactionService.existsByUrl(url) == 0)
					// Create the Object to save in DB, TODO extract create in a function
					.map(link -> {
						OnlineLinkDao onlineLinkDao = new OnlineLinkDao();
						onlineLinkDao.setCrawledDate(LocalDateTime.now());
						onlineLinkDao.setOnlineSourceDao(onlineSourceDao);
						onlineLinkDao.setUrl(link);
						return onlineLinkDao;
					})
					.collect(Collectors.toList());

				browser.driver.quit();

				if (onlineLinkDaos.size() > 0) {
					onlineLinkTransactionService.saveAll(onlineLinkDaos);
					// Temporary code to test elasticsearch document insert
					onlineLinkDaos.stream().forEach(link -> {
						OnlineLinkDocument document = new OnlineLinkDocument();
						document.setUrl(link.getUrl());
						onlineLinkSearch.save(document);
					});
					// End Temporary code, will be replaced by a kafka message
				}
				attempts = 0;
			} catch (StaleElementReferenceException serE) {
				logger.info("[CRAWLER_SERVICE][GET_STALE_EXCEPTION]" + attempts);
				attempts--;
			}
		}
	}

	/**
	 * TODO has to be reworked
	 * @param link
	 * @param pattern
	 * @return
	 */
	private Boolean match(String link, String pattern) {
		if (pattern == null) {
			return false;
		}
		return link.matches(pattern);

	}

	// Create a new driver to reach the http url. Create a new everytime avoids drivers conflict.
	// TODO Find a better way than init the driver here.
	protected WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", chromeBinaryPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-infobars");
		options.addArguments("--user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("--disable-client-side-phishing-detection");
		options.addArguments("--disable-popup-blocking");
		options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		return new ChromeDriver(options);
	}

}
