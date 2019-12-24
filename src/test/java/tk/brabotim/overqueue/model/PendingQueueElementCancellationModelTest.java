package tk.brabotim.overqueue.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.entity.Status;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;
import tk.brabotim.overqueue.repository.StatusRepository;

public class PendingQueueElementCancellationModelTest {

    @InjectMocks 
    private PendingQueueElementCancellationModel pendingQueueElementCancellation; 
    
    private PendingQueueElementRepository mockPendingQueueElementRepository;
    private StatusRepository mockStatusRepository;
    private QueueElementRepository mockQueueElementRepository;
    
    @Before
    public void setUp() {
        mockPendingQueueElementRepository = Mockito.mock(PendingQueueElementRepository.class);
        mockStatusRepository = Mockito.mock(StatusRepository.class);
        mockQueueElementRepository = Mockito.mock(QueueElementRepository.class);
        
        pendingQueueElementCancellation = new PendingQueueElementCancellationModel(
                mockPendingQueueElementRepository, mockStatusRepository, mockQueueElementRepository
        );
    }
    @Test
    public void testCancelQueueElementWhenElementIsLastOnQueueGivenIdReturnOK() {
        Long id = 1L;
        PendingQueueElement pendingQueueElement = new PendingQueueElement();
        QueueElement queueElement = new QueueElement();
        Queue queue = new Queue();
        Status status = new Status();
        queue.setId(id);
        queueElement.setQueue(queue);
        
        when(mockPendingQueueElementRepository.findById(id)).thenReturn(Optional.of(pendingQueueElement));
        when(mockQueueElementRepository.findById(id)).thenReturn(Optional.of(queueElement));
        when(mockPendingQueueElementRepository.pendingElementsByQueue(id)).thenReturn(new ArrayList<PendingQueueElement>());
        when(mockStatusRepository.findById(id)).thenReturn(Optional.of(status));
        assertEquals("UNEXPECTED VIEW RETURN", "redirect:/recepcao?successOnCancel", pendingQueueElementCancellation.cancelQueueElement(id).getViewName());
    }

}
