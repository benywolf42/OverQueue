package tk.brabotim.overqueue.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.entity.PendingQueueElement;
import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.QueueElement;
import tk.brabotim.overqueue.entity.Status;
import tk.brabotim.overqueue.entity.dto.QueueElementDTO;
import tk.brabotim.overqueue.repository.ClientRepository;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;
import tk.brabotim.overqueue.repository.QueueRepository;
import tk.brabotim.overqueue.repository.StatusRepository;

public class PendingQueueElementCreationModel {

	private static final Logger LOG = LogManager.getLogger();

	private static final String UTC_3 = "UTC-3";
	private static final int MAXIMUM_AMOUNT_OF_SEATS = 20;

	private ClientRepository clientRepository;
	private StatusRepository statusRepository;
	private QueueRepository queueRepository;
	private QueueElementRepository queueElementRepository;
	private PendingQueueElementRepository pendingQueueElementRepository;

	@Autowired
	public PendingQueueElementCreationModel(ClientRepository clientRepository, StatusRepository statusRepository,
			QueueRepository queueRepository, QueueElementRepository queueElementRepository,
			PendingQueueElementRepository pendingQueueElementRepository) {

		this.clientRepository = clientRepository;
		this.statusRepository = statusRepository;
		this.queueRepository = queueRepository;
		this.queueElementRepository = queueElementRepository;
		this.pendingQueueElementRepository = pendingQueueElementRepository;
	}

	public ModelAndView createPendingQueueElement(QueueElementDTO queueElementDTO) {
		LOG.info("CHAIRS ASKED: " + queueElementDTO.getChairsQuantityAsked());

		Queue queue = new Queue();
		QueueElement queueElement = new QueueElement();
		Client client = new Client();
		Optional<Status> status = statusRepository.findById(3L);
		PendingQueueElement pendingQueueElement = new PendingQueueElement();
		Integer chairsQuantityAsked = queueElementDTO.getChairsQuantityAsked();
		LocalDateTime queueElementCreationTime = LocalDateTime.now(ZoneId.of(UTC_3));
		short weekday = (short) queueElementCreationTime.getDayOfWeek().getValue();

		LOG.info("TIME ZONE: {}", queueElementCreationTime.toString());

		pendingQueueElement = pendingQueueElementRepository.findByClientPhoneNumber(queueElementDTO.getPhoneNumber());

		if (pendingQueueElement != null) {
			LOG.info("Client already on queue");
			return new ModelAndView("redirect:/recepcao?errorUserOnQueue");
		} else {
			pendingQueueElement = new PendingQueueElement();
		}

		if (queueElementDTO.getChairsQuantityAsked() % 2 != 0) {
			chairsQuantityAsked = queueElementDTO.getChairsQuantityAsked() + 1;
		}

		if (chairsQuantityAsked > MAXIMUM_AMOUNT_OF_SEATS) {
			LOG.info("Number of seats exceeds the allowed, setting the maximum value of 20");
			chairsQuantityAsked = MAXIMUM_AMOUNT_OF_SEATS;
		}

		queue = queueRepository.findQueueByPriorityStatusAndChairNumber(queueElementDTO.getHasPriority(),
				chairsQuantityAsked);

		if (queue.getIsEnabled() == (short) 0) {
			queue.setIsEnabled((short) 1);

			LOG.info("Phone number: {}", queueElementDTO.getPhoneNumber());
			if (queueElementDTO.getPhoneNumber().matches("NI\\d+")) {
				pendingQueueElement.setHasReceivedSms((short) 2);
			} else {
				pendingQueueElement.setHasReceivedSms((short) 1);
			}
		}

		client = clientRepository.findByPhoneNumber(queueElementDTO.getPhoneNumber());
		if (client == null) {
			client = new Client();
			client.setName(queueElementDTO.getName());
			client.setPhoneNumber(queueElementDTO.getPhoneNumber());
		}

		queueElement.setChairsQuantityAsked(queueElementDTO.getChairsQuantityAsked());
		queueElement.setQueueEntryTime(queueElementCreationTime);
		if (status.isPresent()) {
			queueElement.setStatus(status.get());
		}
		queueElement.setHasPriority(queueElementDTO.getHasPriority());
		queueElement.setQueue(queue);
		queueElement.setClient(client);
		queueElement.setWeekDay(weekday);

		pendingQueueElement.setChairsQuantityAsked(chairsQuantityAsked);
		pendingQueueElement.setClient(client);
		pendingQueueElement.setHasPriority(queueElementDTO.getHasPriority());
		pendingQueueElement.setQueue(queue);
		pendingQueueElement.setQueueEntryTime(queueElementCreationTime);

		queueElementRepository.save(queueElement);
		pendingQueueElementRepository.save(pendingQueueElement);

		return new ModelAndView("redirect:/recepcao");
	}
}
