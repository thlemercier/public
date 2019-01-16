package feedio.v2.data.db.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "crawler_info")
public class CrawlerInfoDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private Long duration;

	@ManyToOne(optional = false)
	@JoinColumn(name = "online_source_id", referencedColumnName = "id")
	private OnlineSourceDao onlineSourceDao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public OnlineSourceDao getOnlineSourceDao() {
		return onlineSourceDao;
	}

	public void setOnlineSourceDao(OnlineSourceDao onlineSourceDao) {
		this.onlineSourceDao = onlineSourceDao;
	}

}
