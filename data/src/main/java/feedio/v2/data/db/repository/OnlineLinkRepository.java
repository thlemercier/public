package feedio.v2.data.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import feedio.v2.data.db.model.OnlineLinkDao;

public interface OnlineLinkRepository extends JpaRepository<OnlineLinkDao, Long> {

	/**
	 * 
	 * @param url
	 * @return
	 */
	@Query("SELECT count(old.id) FROM OnlineLinkDao old WHERE old.url = ?1")
	public int existsByUrl(String url);
	
}
