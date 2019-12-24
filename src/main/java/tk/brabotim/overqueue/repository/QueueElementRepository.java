package tk.brabotim.overqueue.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tk.brabotim.overqueue.entity.QueueElement;

public interface QueueElementRepository extends JpaRepository<QueueElement, Long>{

//	@Query("SELECT qe FROM Client c JOIN c.queueElement qe WHERE c.phoneNumber = ?1")
//	QueueElement getQueueElement(String phoneNumber);
	
//	@Query("SELECT qe FROM Queue q JOIN q.queueElement qe WHERE q.id = ?1 AND qe.status = 'Pending'")
//	List<QueueElement> findByIdActive(Long id);
//	
//	@Query("SELECT qe FROM Queue q JOIN q.queueElement qe WHERE q.id = ?1 AND qe.status = 'Pending'")
//	List<QueueElement> pendingElementsByQueue(Long id);
//	
//	@Query("SELECT qe FROM Queue q JOIN q.queueElement qe WHERE q.id = ?1 AND qe.status = 'Done' AND qe.weekDay='?2'")
//	List<QueueElement> doneElementsByQueue(Long id, String weekday);
//
//	@Query("SELECT qe FROM QueueElement qe WHERE qe.id = ?1 AND qe.status = 'Pending'")
//	List<QueueElement> findByIdPending(Long id);
	
//	@Query("SELECT qe FROM QueueElement qe JOIN qe.queue q WHERE qe.status='done' AND q.id = ?1 ORDER BY qe.id")
//	Slice<QueueElement> findLastTwoDoneQueueElementsByQueue(Long idQueue, Pageable pageable);
    
    @Query("SELECT qe FROM Queue q JOIN q.queueElement qe WHERE q.id = ?1")
    List<QueueElement> findByIdQueue(Long id);

    @Query("SELECT qe FROM QueueElement qe JOIN qe.queue q WHERE qe.status=2 AND q.id = ?1")
	Page<QueueElement> findLastTwoDoneByQueue(Long idQueue, PageRequest of);
}
