package tk.brabotim.overqueue.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.service.ClientService;
import tk.brabotim.overqueue.service.PendingQueueElementService;
import tk.brabotim.overqueue.service.QueueElementService;

public class PendingQueueElementPositionModel {

	private static final int ONE_MINUTE_IN_MILISECONDS = 60000;

	private static final int LAST_THREE_ELEMENTS = 3;

	private static final Logger LOG = LogManager.getLogger();

	private ClientService clientService;
	private PendingQueueElementService pendingQueueElementService;
	private QueueElementService queueElementService;
	private long averageWaitingTimeInMinutes;

	@Autowired
	public PendingQueueElementPositionModel(ClientService clientService,
			PendingQueueElementService pendingQueueElementService, QueueElementService queueElementService) {

		this.clientService = clientService;
		this.pendingQueueElementService = pendingQueueElementService;
		this.queueElementService = queueElementService;
	}

	public ModelAndView recoverQueuePosition(String phoneNumber) {
		ModelAndView view = new ModelAndView("cliente/cliente");
		Client client;

		try {
			client = clientService.findByPhoneNumber(phoneNumber);
			view.addObject(client);
		} catch (IllegalArgumentException e) {

			LOG.info("Unable to find phone number");
			LOG.info(e);
			return new ModelAndView("redirect:home?error");
		}

		PendingQueueElement pendingQueueElement = pendingQueueElementService.getPendingQueueElement(phoneNumber);
		setPendingQueueElementsPosition(pendingQueueElement);
		setEstimatedWaitingTime(pendingQueueElement);
		
		view.addObject(pendingQueueElement);
		return view;
	}
	
	public void setPendingQueueElementsPosition(PendingQueueElement pendingQueueElement) {
		List<PendingQueueElement> allPendingElements = pendingQueueElementService
				.pendingElementsByQueue(pendingQueueElement.getQueue().getId());

		int aux = 0;
		for (PendingQueueElement pe : allPendingElements) {
			aux++;
			if (pe.getId().equals(pendingQueueElement.getId())) {
				pendingQueueElement.setPosition(aux);
			}
		}
		LOG.info("Element position: " + pendingQueueElement.getPosition());
	}
	
	public void setEstimatedWaitingTime(PendingQueueElement pendingQueueElement) {
		Page<QueueElement> lastDoneQueueElements = queueElementService.findLastTwoDoneByQueue(
				pendingQueueElement.getQueue().getId(), PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "id")));

		try {
		    
		    List<Long> waitingTimesOfLastThreeElementsInMilliseconds = new ArrayList<Long>();
		    
	        List<QueueElement> lastDoneElements = lastDoneQueueElements.getContent();
	        
	        if(lastDoneElements.size() >= 3) {
	        	
	        	for (QueueElement e : lastDoneElements) {
	        		System.out.println("entry time: " + e.getQueueEntryTime());
	        		System.out.println("exit time: " + e.getQueueExitTime());
	        		System.out.println("====================");
	        		waitingTimesOfLastThreeElementsInMilliseconds.add(
	        				e.getQueueExitTime().atZone(ZoneId.systemDefault())
	        				.toInstant().toEpochMilli() -
	        				e.getQueueEntryTime().atZone(ZoneId.systemDefault())
	        				.toInstant().toEpochMilli()
	        				);        	
	        	}
	        	
	        	averageWaitingTimeInMinutes = calculateAverage(waitingTimesOfLastThreeElementsInMilliseconds);
	        	
	        	LocalDateTime estimatedWaiting = pendingQueueElement.getQueueEntryTime()
	        			.plusMinutes(averageWaitingTimeInMinutes * pendingQueueElement.getPosition());
	        	
	        	pendingQueueElement.setEstimatedTimeForAttendance(estimatedWaiting.format(DateTimeFormatter.ofPattern("HH:mm")));
	        }
		    
			
		} catch (IndexOutOfBoundsException e) {
			LOG.error("There are not enough elements to calculate the estimated time");
			pendingQueueElement.setEstimatedTimeForAttendance(null);
			LOG.error(e);
		}

		LOG.info("AVERAGE WAITING TIME: {}", averageWaitingTimeInMinutes);
		LOG.info("HORÁRIO PREVISTO: {}",
				pendingQueueElement.getEstimatedTimeForAttendance());
	}

    private Long calculateAverage(List<Long> times) {
        Long sum = Long.valueOf(0);
        for (int i = 0; i < times.size(); i++) {
            sum += times.get(i);
        }
        
        return (sum / LAST_THREE_ELEMENTS)/ONE_MINUTE_IN_MILISECONDS;
    }
}