package tk.brabotim.overqueue.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;

public class QueueElementCallModelTest {
    
    private static final Long ID = 1L;

    private static final String PHONE_NUMBER = "phoneNumber";

    @InjectMocks private QueueElementCallModel queueElementCall;
    
    private QueueElementRepository mockQueueElementRepository;
    private PendingQueueElementRepository mockPendingQueueElementRepository;
    
    @Before
    public void setUp() {
        mockPendingQueueElementRepository = Mockito.mock(PendingQueueElementRepository.class);
        mockQueueElementRepository = Mockito.mock(QueueElementRepository.class);
        queueElementCall = new QueueElementCallModel(mockQueueElementRepository, mockPendingQueueElementRepository);
    }

    @Test
    public void testCallReturnSuccess() {
        PendingQueueElement pendingQueueElement = new PendingQueueElement();
        QueueElement queueElement = new QueueElement();
        Client client = new Client();
        client.setPhoneNumber(PHONE_NUMBER);
        queueElement.setClient(client);
        when(mockQueueElementRepository.findById(ID)).thenReturn(Optional.of(queueElement));
        when(mockPendingQueueElementRepository.findById(ID)).thenReturn(Optional.of(pendingQueueElement));
        assertEquals("UNEXPECTED VIEW RETURN", "redirect:/recepcao", queueElementCall.call(ID).getViewName());
    }

}
