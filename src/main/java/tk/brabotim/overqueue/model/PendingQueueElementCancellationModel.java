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

public class PendingQueueElementCancellationModel {

	private static final Logger LOG = LogManager.getLogger();

	private static final String UTC_3 = "UTC-3";
	private static final String MESSAGE = "Você é o próximo da fila! Compareça "
			+ "à área de recepção e fique atento para garantir seu atendimento";

	private PendingQueueElementRepository pendingQueueElementRepository;
	private StatusRepository statusRepository;
	private QueueElementRepository queueElementRepository;

	@Autowired
	public PendingQueueElementCancellationModel(PendingQueueElementRepository pendingQueueElementRepository,
			StatusRepository statusRepository, QueueElementRepository queueElementRepository) {
		this.pendingQueueElementRepository = pendingQueueElementRepository;
		this.statusRepository = statusRepository;
		this.queueElementRepository = queueElementRepository;
	}

	public ModelAndView cancelQueueElement(Long id) {

		Optional<PendingQueueElement> pendingQueueElement = pendingQueueElementRepository.findById(id);
		if (!pendingQueueElement.isPresent()) {
			LOG.error("PendingQueueElement id was not located. ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCancel");
		}
		pendingQueueElementRepository.delete(pendingQueueElement.get());

		Optional<QueueElement> queueElementFound = queueElementRepository.findById(id);
		if (!queueElementFound.isPresent()) {
			LOG.error("QueueElement id was not located. ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCancel");
		}
		QueueElement queueElement = queueElementFound.get();
		updateQueueElement(queueElement);

		List<PendingQueueElement> pendingQueueElementsList = pendingQueueElementRepository
				.pendingElementsByQueue(queueElement.getQueue().getId());

		if (pendingQueueElementsList.isEmpty()) {
			disableQueue(queueElement);

		} else {
			PendingQueueElement newFirstOnQueue = pendingQueueElementsList.get(0);
			sendSms(newFirstOnQueue);
		}

		return new ModelAndView("redirect:/recepcao?successOnCancel");
	}

	public void updateQueueElement(QueueElement queueElement) {
        LocalDateTime queueExitTime = LocalDateTime.now(ZoneId.of(UTC_3));
        
        Optional<Status> status = statusRepository.findById(1L);
        if (status.isPresent()) {
        	queueElement.setStatus(status.get());
        }
        
        queueElement.setQueueExitTime(queueExitTime);
    }

	public void disableQueue(QueueElement queueElement) {
		LOG.info("Empty queue, disabling queue: {}", queueElement.getQueue().getId());
		queueElement.getQueue().setIsEnabled((short) 0);
		queueElementRepository.save(queueElement);
	}

	public void sendSms(PendingQueueElement newFirstOnQueue) {
		String phoneNumber = newFirstOnQueue.getClient().getPhoneNumber();

		LOG.info("Sending SMS to {}", phoneNumber);
		TotalVoiceSms sms = new TotalVoiceSms();
		try {
			sms.sendSms(phoneNumber, MESSAGE);
		} catch (Exception e) {
			LOG.error("Error sending SMS to {}", phoneNumber);
		} finally {
			newFirstOnQueue.setHasReceivedSms((short) 1);
			pendingQueueElementRepository.save(newFirstOnQueue);
		}
	}
}
