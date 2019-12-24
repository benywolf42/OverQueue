package tk.brabotim.overqueue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;

@Service
public class PendingQueueElementService {

	@Autowired
	PendingQueueElementRepository PendingQueueElementRepository;
	
	public PendingQueueElement getPendingQueueElement(String phoneNumber) {
		return PendingQueueElementRepository.getPendingQueueElement(phoneNumber);
	}
	
	public List<PendingQueueElement> pendingElementsByQueue(Long id) {
		return PendingQueueElementRepository.pendingElementsByQueue(id);
	}
}
