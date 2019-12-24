package tk.brabotim.overqueue.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import tk.brabotim.overqueue.entity.Queue;

public class QueueServiceTest {

	private QueueService queueService;
	private Queue queue = new Queue();
	private List<Queue> queuesList = new ArrayList<Queue>();
	
	@Before
	public void before() {
		queueService = mock(QueueService.class);
	}
	
	@Test
	public void testFindAll() {
		when(queueService.findAll()).thenReturn(queuesList);
		assertEquals(queuesList, queueService.findAll());
	}

	@Test
	public void testFindActive() {
		when(queueService.findConventionalEnabled()).thenReturn(queuesList);
		assertEquals(queuesList, queueService.findConventionalEnabled());
	}

	@Test
	public void testFindNormalActive() {
		when(queueService.findConventionalQueues()).thenReturn(queuesList);
		assertEquals(queuesList, queueService.findConventionalQueues());
	}

	@Test
	public void testFindById() {
		Optional<Queue> queueFoundById = null;
		when(queueService.findById(1L)).thenReturn(queueFoundById);
		assertEquals(queueFoundById, queueService.findById(1L));
	}
//TODO
//	@Test
//	public void testFindByChairsNumberOnTable() {
//		when(queueService.findByChairsQuantityOnTableAndEnabled(4)).thenReturn(queuesList);
//		assertEquals(queuesList, queueService.findByChairsQuantityOnTableAndEnabled(4));
//	}

	@Test
	public void testFindByChairsNumber() {
		Optional<Queue> queueFoundByChairsNumber = null;
		when(queueService.findById(1L)).thenReturn(queueFoundByChairsNumber);
		assertEquals(queueFoundByChairsNumber, queueService.findById(1L));
	}

}
