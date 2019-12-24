package tk.brabotim.overqueue.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.Status;
import tk.brabotim.overqueue.entity.dto.QueueElementDTO;
import tk.brabotim.overqueue.repository.ClientRepository;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;
import tk.brabotim.overqueue.repository.QueueRepository;
import tk.brabotim.overqueue.repository.StatusRepository;

public class PendingQueueElementCreationModelTest {

    private static final short DISABLED_QUEUE = 0;
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final Integer CHAIRS_QUANTITY_ASKED = 4;
    private static final short CONVENTIONAL = 0;
    private static final String NAME = "NAME";
    
    @InjectMocks private PendingQueueElementCreationModel pendingQueueElementCreation;
    
    private ClientRepository mockClientRepository;
    private StatusRepository mockStatusRepository;
    private QueueRepository mockQueueRepository;
    private QueueElementRepository mockQueueElementRepository;
    private PendingQueueElementRepository mockPendingQueueElementRepository;
    
    @Before
    public void setUp() {
        mockClientRepository = Mockito.mock(ClientRepository.class);
        mockStatusRepository = Mockito.mock(StatusRepository.class);
        mockQueueRepository = Mockito.mock(QueueRepository.class);
        mockQueueElementRepository = Mockito.mock(QueueElementRepository.class);
        mockPendingQueueElementRepository = Mockito.mock(PendingQueueElementRepository.class);
        
        pendingQueueElementCreation = new PendingQueueElementCreationModel(mockClientRepository, mockStatusRepository, 
                mockQueueRepository, mockQueueElementRepository, mockPendingQueueElementRepository);
    }
    
    @Test
    public void testCreatePendingQueueElementWhenDisabledQueueGivenNewClientThenReturnOk() {
        
        Status status = new Status();
        Queue queue = new Queue();
        queue.setIsEnabled(DISABLED_QUEUE);
        
        when(mockClientRepository.findByPhoneNumber(PHONE_NUMBER)).thenReturn(new Client());
        when(mockStatusRepository.findById(3L)).thenReturn(Optional.of(status));
        when(mockPendingQueueElementRepository.findByClientPhoneNumber(PHONE_NUMBER)).thenReturn(null);
        when(mockQueueRepository.findQueueByPriorityStatusAndChairNumber(CONVENTIONAL, CHAIRS_QUANTITY_ASKED)).thenReturn(queue);
        
        QueueElementDTO dto = new QueueElementDTO();
        dto.setChairsQuantityAsked(CHAIRS_QUANTITY_ASKED);
        dto.setHasPriority(CONVENTIONAL);
        dto.setName(NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        assertEquals("UNEXPECTED VIEW RETURN", "redirect:/recepcao", pendingQueueElementCreation.createPendingQueueElement(dto).getViewName());
    }

}
