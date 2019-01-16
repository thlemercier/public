package feedio.v2.data.db.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "online_link")
public class OnlineLinkDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private LocalDateTime crawledDate;

	private LocalDateTime scrappedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_source_id", nullable = false)
	private OnlineSourceDao onlineSourceDao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getCrawledDate() {
		return crawledDate;
	}

	public void setCrawledDate(LocalDateTime crawledDate) {
		this.crawledDate = crawledDate;
	}

	public LocalDateTime getScrappedDate() {
		return scrappedDate;
	}

	public void setScrappedDate(LocalDateTime scrappedDate) {
		this.scrappedDate = scrappedDate;
	}

	public OnlineSourceDao getOnlineSourceDao() {
		return onlineSourceDao;
	}

	public void setOnlineSourceDao(OnlineSourceDao onlineSourceDao) {
		this.onlineSourceDao = onlineSourceDao;
	}

}
