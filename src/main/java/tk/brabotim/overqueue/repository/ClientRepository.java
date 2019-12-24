package tk.brabotim.overqueue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tk.brabotim.overqueue.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	@Query("SELECT c FROM PendingQueueElement pqe JOIN pqe.client c WHERE c.phoneNumber = ?1")
	Client findByPhoneNumber(String phoneNumber);
	
	@Query("SELECT c FROM Client c WHERE c.name = ?1 AND c.phoneNumber = ?2")
	Client findByNameAndPhoneNumber(String name, String phoneNumber);
	
	@Query("SELECT c FROM Queue q JOIN q.pendingQueueElement pqe JOIN pqe.client c WHERE q.id = ?1")
	List<Client> getClientsByIdQueue(Long idQueue);

//	@Query("SELECT c, qe FROM Client c JOIN c.queueElement qe WHERE qe.id = ?1")
//	List<Client> findByQueueElementId(Long id);
}