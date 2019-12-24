package tk.brabotim.overqueue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tk.brabotim.overqueue.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long>{

	@Query("SELECT s FROM Status s JOIN s.queueElement qe WHERE qe.id = ?1")
	Optional<Status> findByQueueElementId(Long queueElementId);
}
