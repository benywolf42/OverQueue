package tk.brabotim.overqueue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.repository.QueueRepository;

@Service
public class QueueService {

	@Autowired
	private QueueRepository queueRepository;
	
	public Queue save(Queue queue) {
		return save(queue);
	}
	
	public void update(Queue queue) {
		queueRepository.saveAndFlush(queue);
	}
	
	public void deleteById(Long id) {
		queueRepository.deleteById(id);
	}
	
	public List<Queue> findAll() {
		return queueRepository.findAll();
	}
	
	public List<Queue> findPriorityQueues() {
		return queueRepository.findPriorityQueues();
	}
	
	public List<Queue> findConventionalQueues() {
		return queueRepository.findConventionalQueues();
	}
	
	public Optional<Queue> findById(Long id) {
		return queueRepository.findById(id);
	}

	public List<Queue> findByChairsQuantityOnTableAndEnabled(int chairsNumber) {
		return queueRepository.findByChairsQuantityOnTableAndEnabled(chairsNumber);
	}
	
	public List<Queue> findByChairsQuantity(int chairsNumber) {
		return queueRepository.findByChairsQuantity(chairsNumber);
	}

	public Optional<Queue> findIdByElement(Long id) {
		return queueRepository.findIdByElement(id);
	}
	
	public List<Queue> findPriorityEnabled() {
		return queueRepository.findPriorityEnabled();
	}
	public List<Queue> findConventionalEnabled() {
		return queueRepository.findConventionalEnabled();
	}
	
	public Queue findConventionalQueueByChairNumber(short hasPriority, int chairsNumber) {
		return queueRepository.findQueueByPriorityStatusAndChairNumber(hasPriority, chairsNumber);
	}
}
