package tk.brabotim.overqueue.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.entity.Status;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;
import tk.brabotim.overqueue.repository.StatusRepository;

public class QueueElementConfirmationModelTest {
    
    private static final Long ID = 1L;

    private static final String PHONE_NUMBER = "phoneNumber";

    @InjectMocks
    private QueueElementConfirmationModel testModel;

    private StatusRepository mockStatusRepository;
    private QueueElementRepository mockQueueElementRepository;
    private PendingQueueElementRepository mockPendingQueueElementRepository;

    @Before
    public void setUp() {
        mockStatusRepository = Mockito.mock(StatusRepository.class);
        mockQueueElementRepository = Mockito.mock(QueueElementRepository.class);
        mockPendingQueueElementRepository = Mockito.mock(PendingQueueElementRepository.class);
        testModel = new QueueElementConfirmationModel(mockStatusRepository, 
                mockQueueElementRepository, mockPendingQueueElementRepository);
    }

    @Test
    public void testConfirmReturnSuccess() {
        Status status = new Status();
        Queue queue = new Queue();
        Client client = new Client();
        client.setPhoneNumber(PHONE_NUMBER);
        PendingQueueElement pendingQueueElement = new PendingQueueElement();
        pendingQueueElement.setClient(client);
        QueueElement queueElement = new QueueElement();
        List<PendingQueueElement> pendingQueueElements = new ArrayList<PendingQueueElement>();
        pendingQueueElements.add(pendingQueueElement);
        queueElement.setQueue(queue);
        when(mockStatusRepository.findById(2L)).thenReturn(Optional.of(status));
        when(mockQueueElementRepository.findById(ID)).thenReturn(Optional.of(queueElement));
        when(mockPendingQueueElementRepository.findById(ID)).thenReturn(Optional.of(pendingQueueElement));
        when(mockPendingQueueElementRepository.pendingElementsByQueue(queueElement.getQueue().getId()))
            .thenReturn(pendingQueueElements);
        
        assertEquals("UNEXPECTED VIEW RETURN", "redirect:/recepcao", testModel.confirm(ID).getViewName());
    }
    
}
