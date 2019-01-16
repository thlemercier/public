package feedio.v2.data.db.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "online_source")
public class OnlineSourceDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String domain;

	@Column(nullable = false)
	private String pattern;

	private String excludedPattern;

	@OneToMany(mappedBy = "onlineSourceDao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OnlineLinkDao> onlineLinkDaos;

	@OneToMany(mappedBy = "onlineSourceDao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("endDate DESC")
	private List<CrawlerInfoDao> crawlerInfoDaos;

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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<OnlineLinkDao> getOnlineLinkDaos() {
		return onlineLinkDaos;
	}

	public void setOnlineLinkDaos(List<OnlineLinkDao> onlineLinkDaos) {
		this.onlineLinkDaos = onlineLinkDaos;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getExcludedPattern() {
		return excludedPattern;
	}

	public void setExcludedPattern(String excludedPattern) {
		this.excludedPattern = excludedPattern;
	}

	public List<CrawlerInfoDao> getCrawlerInfoDaos() {
		return crawlerInfoDaos;
	}

	public void setCrawlerInfoDaos(List<CrawlerInfoDao> crawlerInfoDaos) {
		this.crawlerInfoDaos = crawlerInfoDaos;
	}

}
