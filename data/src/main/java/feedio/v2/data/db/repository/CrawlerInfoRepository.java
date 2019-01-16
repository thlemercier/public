package feedio.v2.data.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import feedio.v2.data.db.model.CrawlerInfoDao;

public interface CrawlerInfoRepository extends JpaRepository<CrawlerInfoDao, Long> {

}
