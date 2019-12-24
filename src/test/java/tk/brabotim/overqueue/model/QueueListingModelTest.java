package tk.brabotim.overqueue.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.service.QueueService;

public class QueueListingModelTest {
    
    @InjectMocks private QueueListingModel queueListing;

    @Test
    public void testListQueuesWhenEnabledQueuesGivenOneElementThenReturnView() {
        QueueService mockQueueService = Mockito.mock(QueueService.class);
        queueListing = new QueueListingModel(mockQueueService);
        Queue conventionalQueue = new Queue();
        Queue priorityQueue = new Queue();
        List<Queue> conventionalQueuesList = new ArrayList<Queue>();
        List<Queue> priorityQueuesList = new ArrayList<Queue>();
        PendingQueueElement pendingQueueElement = new PendingQueueElement();
        List<PendingQueueElement> pendingQueueElements = new ArrayList<PendingQueueElement>(); 
        pendingQueueElement.setQueueEntryTime(LocalDateTime.now());
        pendingQueueElements.add(pendingQueueElement);
        conventionalQueue.setPendingQueueElement(pendingQueueElements);
        priorityQueue.setPendingQueueElement(pendingQueueElements);
        
        conventionalQueuesList.add(conventionalQueue);
        priorityQueuesList.add(priorityQueue);
        
        when(mockQueueService.findConventionalEnabled()).thenReturn(conventionalQueuesList);
        when(mockQueueService.findPriorityEnabled()).thenReturn(priorityQueuesList);
        
        queueListing.listQueues();
        
        verify(mockQueueService).findConventionalEnabled();
        verify(mockQueueService).findPriorityEnabled();
        assertEquals("PENDING ELEMENTS ON QUEUE ABOVE THE LIMIT FOR CARD", 0, conventionalQueue.getPendingElementsOnQueue());
        assertEquals("PENDING ELEMENT ON THE WRONG POSITION", 1, pendingQueueElements.get(0).getPosition());
        assertEquals("UNEXPECTED VIEW RETURN", "recepcao/recepcao", queueListing.listQueues().getViewName());
    }

}
