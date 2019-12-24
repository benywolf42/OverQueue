package tk.brabotim.overqueue.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.entity.Status;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;
import tk.brabotim.overqueue.repository.StatusRepository;

public class QueueElementConfirmationModel {
	
	private static final Logger logger = LogManager.getLogger();
	private static final String UTC_3 = "UTC-3";
	
	private StatusRepository statusRepository;
	private QueueElementRepository queueElementRepository;
	private PendingQueueElementRepository pendingQueueElementRepository;

	@Autowired
	public QueueElementConfirmationModel(StatusRepository statusRepository, QueueElementRepository queueElementRepository,
			PendingQueueElementRepository pendingQueueElementRepository) {
		
		this.statusRepository = statusRepository;
		this.queueElementRepository = queueElementRepository;
		this.pendingQueueElementRepository = pendingQueueElementRepository;
	}

	public ModelAndView confirm(Long id) {
		Optional<Status> status = statusRepository.findById(2L);
		Optional<QueueElement> queueElement = queueElementRepository.findById(id);
		Optional<PendingQueueElement> pendingQueueElement = pendingQueueElementRepository.findById(id);
		String message = "Você é o próximo da fila! Compareça à área de recepção e fique atento para garantir seu atendimento";
		TotalVoiceSms sms = new TotalVoiceSms();
		List<PendingQueueElement> pendingQueueElementsList;
		LocalDateTime queueExitTime = LocalDateTime.now(ZoneId.of(UTC_3));
		
		if (!pendingQueueElement.isPresent()) {
			logger.error("Pending queue element not found, ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnConfirm");
		}
		
		if (!status.isPresent()) {
			logger.error("Status not found, ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnConfirm");
		}
		
		if (!queueElement.isPresent()) {
			logger.error("Queue Element not found, ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnConfirm");
		}

		queueElement.get().setStatus(status.get());
		queueElement.get().setQueueExitTime(queueExitTime);
		pendingQueueElementRepository.delete(pendingQueueElement.get());

		pendingQueueElementsList = pendingQueueElementRepository
				.pendingElementsByQueue(queueElement.get().getQueue().getId());
		if (pendingQueueElementsList.isEmpty()) {
			logger.info("Empty queue, disabling queue: {}", queueElement.get().getQueue().getId());
			queueElement.get().getQueue().setIsEnabled((short) 0);
			queueElementRepository.save(queueElement.get());
		} else {
			String phoneNumber = pendingQueueElementsList.get(0).getClient().getPhoneNumber();
			
			try {
				sms.sendSms(phoneNumber, message);
				pendingQueueElementsList.get(0).setHasReceivedSms((short) 1);
				pendingQueueElementRepository.save(pendingQueueElementsList.get(0));
			} catch (Exception e) {
				logger.error("Error sending SMS to {}", phoneNumber);
				return new ModelAndView("redirect:/recepcao?errorOnConfirm");
			}
		}
		
		return new ModelAndView("redirect:/recepcao");
	}
}
