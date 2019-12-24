package tk.brabotim.overqueue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.repository.QueueElementRepository;

@Service
public class QueueElementService {

	@Autowired
	QueueElementRepository queueElementRepository;
	
	public void save(QueueElement queueElement) {
		queueElementRepository.save(queueElement);
	}
	
	public void update(QueueElement queueElement) {
		queueElementRepository.saveAndFlush(queueElement);
	}
	
	public void deleteById(Long id) {
		queueElementRepository.deleteById(id);
	}
	
	public List<QueueElement> findAll() {
		return queueElementRepository.findAll();
	}
	
	public Optional<QueueElement> findById(Long id) {
		return queueElementRepository.findById(id);
	}

	public Page<QueueElement> findLastTwoDoneByQueue(Long idQueue, PageRequest of) {
		return queueElementRepository.findLastTwoDoneByQueue(idQueue, of);
	}

//	public QueueElement getQueueElement(String phoneNumber) {
//		return queueElementRepository.getQueueElement(phoneNumber);
//	}
//	
//	public List<QueueElement> findByIdActive(Long id) {
//		return queueElementRepository.findByIdActive(id);
//	}
//	
//	public List<QueueElement> findByIdPending(Long id) {
//		return queueElementRepository.findByIdPending(id);
//	}
//	
//	public List<QueueElement> pendingElementsByQueue(Long id) {
//		return queueElementRepository.pendingElementsByQueue(id);
//	}
//	
//	public List<QueueElement> doneElementsByQueue(Long id, String weekday) {
//		return queueElementRepository.doneElementsByQueue(id, weekday);
//	}
	
}
