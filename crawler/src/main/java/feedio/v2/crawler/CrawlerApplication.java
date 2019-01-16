package feedio.v2.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import feedio.v2.crawler.job.CrawlerJob;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackages = { "feedio.v2.data.db" })
@EnableElasticsearchRepositories(basePackages = "feedio.v2.data.elastic")
@EntityScan(basePackages = { "feedio" })
public class CrawlerApplication implements CommandLineRunner {

	@Autowired
	private CrawlerJob crawlerJob;

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

	public void run(String... args) throws Exception {
		crawlerJob.startScheduledCrawlerJob();
	}

}
