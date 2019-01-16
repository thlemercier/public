package feedio.v2.data.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import feedio.v2.data.db.model.OnlineSourceDao;

public interface OnlineSourceRepository extends JpaRepository<OnlineSourceDao, Long> {

}
