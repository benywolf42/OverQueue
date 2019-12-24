package tk.brabotim.overqueue.model;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;

public class QueueElementCallModel {
	
	private static final Logger LOG = LogManager.getLogger();

	private QueueElementRepository queueElementRepository;
	private PendingQueueElementRepository pendingQueueElementRepository;
	
	@Autowired
	public QueueElementCallModel(QueueElementRepository queueElementRepository,
			PendingQueueElementRepository pendingQueueElementRepository) {
		
		this.queueElementRepository = queueElementRepository;
		this.pendingQueueElementRepository = pendingQueueElementRepository;
	}

	public ModelAndView call(Long id) {
		Optional<QueueElement> queueElement = queueElementRepository.findById(id);
		Optional<PendingQueueElement> pendingQueueElement = pendingQueueElementRepository.findById(id);
		TotalVoiceSms sms = new TotalVoiceSms();
		String message = "Sua mesa está pronta! Compareça à área de recepção para ser atendido.";
		
		if (!pendingQueueElement.isPresent()) {
			LOG.error("Pending queue element not found, ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCall");
		}
		
		if (!queueElement.isPresent()) {
			LOG.error("Queue element not found, ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCall");
		}

		String phoneNumber = queueElement.get().getClient().getPhoneNumber();
		try {
			sms.sendSms(phoneNumber, message);
		} catch (Exception e) {
			LOG.error("Error sending SMS to {}", phoneNumber);
			return new ModelAndView("redirect:/recepcao?errorOnCall"); 
		} finally {
		    pendingQueueElement.get().setHasReceivedSms((short) 2);
		    pendingQueueElementRepository.save(pendingQueueElement.get());
		}

		return new ModelAndView("redirect:/recepcao");
	}
}
