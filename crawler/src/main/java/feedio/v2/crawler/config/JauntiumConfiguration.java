package feedio.v2.crawler.config;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JauntiumConfiguration {

	private String chromeBinaryPath;

	public JauntiumConfiguration(@Value("${chrome.binary.path}") String chromeBinaryPath) {
		this.chromeBinaryPath = chromeBinaryPath;
	}

	@Bean(name = "jauntiumWebDriver")
	public WebDriver getJauntiumWebDriver() {
//		https://peter.sh/experiments/chromium-command-line-switches/
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
