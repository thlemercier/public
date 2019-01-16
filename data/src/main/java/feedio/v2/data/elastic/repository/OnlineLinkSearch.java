package feedio.v2.data.elastic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import feedio.v2.data.elastic.model.OnlineLinkDocument;

public interface OnlineLinkSearch extends ElasticsearchRepository<OnlineLinkDocument, String> {

	/**
	 * 
	 * @param url
	 * @param pageable
	 * @return
	 */
	Page<OnlineLinkDocument> findByUrl(String url, Pageable pageable);
}
