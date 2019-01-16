package feedio.v2.crawler.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import feedio.v2.crawler.services.CrawlerJauntiumService;
import feedio.v2.data.db.model.CrawlerInfoDao;
import feedio.v2.data.db.model.OnlineSourceDao;
import feedio.v2.data.db.repository.CrawlerInfoRepository;
import feedio.v2.data.db.repository.OnlineSourceRepository;

@Component
public class CrawlerJob {

	Logger logger = LoggerFactory.getLogger(CrawlerJob.class);

	@Autowired
	private OnlineSourceRepository onlineSourceTransactionService;

	@Autowired
	private CrawlerInfoRepository crawlerInfoTransactionService;

	@Autowired
	CrawlerJauntiumService crawlerJauntiumService;

	@Scheduled(initialDelay = 300000, fixedRate = 300000) // 5 min
	public void startScheduledCrawlerJob() {

		logger.info("[SCHEDULER][START]" + LocalDateTime.now().toString());

		// Get SOURCES
		Iterable<OnlineSourceDao> onlineSources = onlineSourceTransactionService.findAll();

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		StreamSupport.stream(onlineSources.spliterator(), false).forEach(onlineSource -> {
			logger.info("[SCHEDULER][SOURCE]" + onlineSource.getUrl());
			executor.execute(new Runnable() {
				public void run() {
					CrawlerInfoDao crawlerInfoDao = new CrawlerInfoDao();
					try {
						crawlerInfoDao.setOnlineSourceDao(onlineSource);
						crawlerInfoDao.setStartDate(LocalDateTime.now());
						logger.info("[SCHEDULER][SOURCE] START RUNNABLE");
						crawlerJauntiumService.crawlSource(onlineSource);
					} finally {
						crawlerInfoDao.setEndDate(LocalDateTime.now());
						long duration = Duration.between(crawlerInfoDao.getStartDate(), crawlerInfoDao.getEndDate())
							.toMillis();
						crawlerInfoDao.setDuration(duration);
						crawlerInfoTransactionService.save(crawlerInfoDao);
						logger.info("[SCHEDULER][SOURCE] END RUNNABLE");
					}
				}
			});
		});

		logger.info("[SCHEDULER][END]" + LocalDateTime.now().toString());
	}

}
