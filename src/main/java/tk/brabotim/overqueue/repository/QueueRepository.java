package tk.brabotim.overqueue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tk.brabotim.overqueue.entity.Queue;

public interface QueueRepository extends JpaRepository<Queue, Long> {
	@Query("SELECT u FROM Queue u WHERE u.hasPriority = 1")
	List<Queue> findPriorityQueues();
	
	@Query("SELECT u FROM Queue u WHERE u.hasPriority = 0")
	List<Queue> findConventionalQueues();
	
	@Query("SELECT q FROM Queue q WHERE q.chairsQuantityOnTable = ?1 AND q.isEnabled = 1")
	List<Queue> findByChairsQuantityOnTableAndEnabled(int chairsNumber);
	
	@Query("SELECT q FROM Queue q WHERE q.chairsQuantityOnTable = ?1")
	List<Queue> findByChairsQuantity(int chairsNumber);

	@Query("SELECT q FROM Queue q JOIN q.pendingQueueElement pqe WHERE pqe.id = ?1")
	Optional<Queue> findIdByElement(Long id);	
	
	@Query("SELECT u FROM Queue u WHERE u.hasPriority = 1 and isEnabled = 1")
	List<Queue> findPriorityEnabled();
	
	@Query("SELECT u FROM Queue u WHERE u.hasPriority = 0 and isEnabled = 1")
	List<Queue> findConventionalEnabled();
	
	@Query("SELECT q FROM Queue q WHERE q.hasPriority = ?1 and q.chairsQuantityOnTable = ?2")
	Queue findQueueByPriorityStatusAndChairNumber(short hasPriority, int chairsNumber);

	@Query("SELECT q FROM Queue q JOIN q.queueElement qe WHERE qe.id = ?1")
	Queue findIdByQueueElement(Long id);
}