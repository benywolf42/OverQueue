package tk.brabotim.overqueue.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.entity.Status;
import tk.brabotim.overqueue.model.PendingQueueElementPositionModel;
import tk.brabotim.overqueue.model.TotalVoiceSms;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.StatusRepository;
import tk.brabotim.overqueue.service.ClientService;
import tk.brabotim.overqueue.service.PendingQueueElementService;
import tk.brabotim.overqueue.service.QueueElementService;
import tk.brabotim.overqueue.service.QueueService;

@Controller
public class ClientController {
	
	static final Logger logger = LogManager.getLogger(ClientController.class);
	
	private static final String UTC_3 = "UTC-3";
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	QueueElementService queueElementService;
	
	@Autowired
	PendingQueueElementService pendingQueueElementService;
	
	@Autowired
	QueueService queueService;
	
	@Autowired
	StatusRepository statusRepository;
	
	@Autowired
	PendingQueueElementRepository pendingQueueElementRepository;
	
	@GetMapping("/client")
	public ModelAndView recoverQueuePosition(
			@RequestParam String phoneNumber) {
	    logger.info("Request method: GET /client");
	    logger.info("Phone number sent: " + phoneNumber);
	    
	    return new PendingQueueElementPositionModel(
	    		clientService, pendingQueueElementService, queueElementService).recoverQueuePosition(phoneNumber);
    }
	
	@PostMapping("/client/queueElement/cancel")
	public ModelAndView deleteQueueElement(@RequestParam("id") Long id) {
		logger.info("Request method: GET /client/queueElement/cancel?id={}", id);

		Optional<PendingQueueElement> pendingQueueElement = pendingQueueElementRepository.findById(id);
		List<PendingQueueElement> pendingQueueElementsList;
		TotalVoiceSms sms = new TotalVoiceSms();
		String message = "Você é o próximo da fila! Compareça à área de recepção e fique atento para garantir seu atendimento";

		if (!pendingQueueElement.isPresent()) {
			logger.error("PendingQueueElement id was not located. ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCancel");
		}
		
		Optional<Status> status = statusRepository.findById(2L);
		if (!status.isPresent()) {
			logger.error("Status id was not located. ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCancel");
		}
		
		Optional<QueueElement> queueElement = queueElementService.findById(id);
		if (!queueElement.isPresent()) {
			logger.error("QueueElement id was not located. ID: {}", id);
			return new ModelAndView("redirect:/recepcao?errorOnCancel");
		}
		LocalDateTime queueExitTime = LocalDateTime.now(ZoneId.of(UTC_3));

		queueElement.get().setStatus(status.get());
		queueElement.get().setQueueExitTime(queueExitTime);
		pendingQueueElementRepository.delete(pendingQueueElement.get());
		
		pendingQueueElementsList = pendingQueueElementRepository
				.pendingElementsByQueue(queueElement.get().getQueue().getId());
		if (pendingQueueElementRepository.pendingElementsByQueue(queueElement.get().getQueue().getId()).isEmpty()) {
			logger.info("Empty queue, disabling queue: {}", queueElement.get().getQueue().getId());
			queueElement.get().getQueue().setIsEnabled((short) 0);
			queueElementService.save(queueElement.get());
		} else if (pendingQueueElementsList.get(0).getHasReceivedSms() == 0){
			String phoneNumber = pendingQueueElementsList.get(0).getClient().getPhoneNumber();
			
			try {
				sms.sendSms(phoneNumber, message);
			} catch (Exception e) {
				logger.error("Error sending SMS to {}", phoneNumber);
			} finally {
			    pendingQueueElementsList.get(0).setHasReceivedSms((short) 1);
			    pendingQueueElementRepository.save(pendingQueueElementsList.get(0));
			}
		}

		return new ModelAndView("redirect:/home?successOnCancel");
	}
	
}
