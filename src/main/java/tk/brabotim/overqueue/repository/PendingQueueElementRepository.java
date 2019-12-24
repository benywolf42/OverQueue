package tk.brabotim.overqueue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tk.brabotim.overqueue.entity.PendingQueueElement;

public interface PendingQueueElementRepository extends JpaRepository<PendingQueueElement, Long>{
	
	@Query("SELECT pqe FROM PendingQueueElement pqe JOIN pqe.client c WHERE c.phoneNumber = ?1")
	PendingQueueElement getPendingQueueElement(String phoneNumber);

	@Query("SELECT pqe FROM Queue q JOIN q.pendingQueueElement pqe WHERE q.id = ?1")
	List<PendingQueueElement> pendingElementsByQueue(Long id);
	
	@Query("SELECT pqe FROM PendingQueueElement pqe JOIN pqe.client c WHERE c.phoneNumber = ?1")
	PendingQueueElement findByClientPhoneNumber(String phoneNumber);
	
	@Query("SELECT pqe FROM PendingQueueElement pqe JOIN pqe.client c JOIN c.queueElement qe WHERE qe.id = ?1")
	PendingQueueElement findByQueueElement(Long queueElementId);
	
//	@Query("SELECT pqe FROM Client c JOIN c.pendingQueueElement pqe WHERE c.phoneNumber = ?1 and pqe.status.pending=1")
//	PendingQueueElement getQueueElement(String phoneNumber);
	
}
