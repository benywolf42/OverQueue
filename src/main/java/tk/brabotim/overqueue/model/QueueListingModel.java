package tk.brabotim.overqueue.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.service.QueueService;

public class QueueListingModel {

    private static final int LIMIT_FOR_QUEUE_CARD = 3;

    private static final int FIVE_MINUTES_IN_MILLISECONDS = 60000;

    private static final Logger LOG = LogManager.getLogger();

    private int pendingElementsOnQueue;

    private QueueService queueService;

	private int aux;

    public QueueListingModel(QueueService queueService) {
        this.queueService = queueService;
    }

    public ModelAndView listQueues() {
        ModelAndView view = new ModelAndView("recepcao/recepcao");
        view.addObject("queueElement", new QueueElement());

        List<Queue> conventionalQueues = queueService.findConventionalEnabled();
        List<Queue> priorityQueues = queueService.findPriorityEnabled();
        
        settingQueueAttributes(conventionalQueues);
        settingQueueAttributes(priorityQueues);

        view.addObject("conventionalQueues", conventionalQueues);
        view.addObject("priorityQueues", priorityQueues);
        return view;
    }

    public void settingQueueAttributes(Iterable<Queue> queuesList) {
        long waitingOnQueueDuration;
        long timeOfEntryOnQueue;
        long now;
        
        for (Queue queue : queuesList) {
            PendingQueueElement pendingQueueElementAtFirstPosition = queue.getPendingQueueElement().get(0);

            timeOfEntryOnQueue = pendingQueueElementAtFirstPosition.getQueueEntryTime()
                    .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
            now = LocalDateTime.now().atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
            waitingOnQueueDuration = (now - timeOfEntryOnQueue) / FIVE_MINUTES_IN_MILLISECONDS;
            queue.setWaitingOnQueueDuration(waitingOnQueueDuration);

            LOG.info("ENTRY TIME: {}", pendingQueueElementAtFirstPosition.getQueueEntryTime()
                    .atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
            LOG.info("NOW: {}", LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
            LOG.info("WAITING TIME: {}", waitingOnQueueDuration);

            pendingElementsOnQueue = queue.getPendingQueueElement().size();
            LOG.info("PENDING ELEMENTS ON QUEUE: {}", pendingElementsOnQueue);
            if (pendingElementsOnQueue > LIMIT_FOR_QUEUE_CARD) {
                queue.setPendingElementsOnQueue(pendingElementsOnQueue - LIMIT_FOR_QUEUE_CARD);
            } else {
                queue.setPendingElementsOnQueue(0);
            }
            List<PendingQueueElement> pendingQueueElements = queue.getPendingQueueElement();
            aux = 0;
            pendingQueueElements.forEach(pqe->{
            	aux++;
            	pqe.setPosition(aux);
    		});
        }        
    }

}
