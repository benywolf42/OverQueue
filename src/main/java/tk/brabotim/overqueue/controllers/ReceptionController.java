package tk.brabotim.overqueue.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.dto.QueueElementDTO;
import tk.brabotim.overqueue.model.PendingQueueElementCancellationModel;
import tk.brabotim.overqueue.model.PendingQueueElementCreationModel;
import tk.brabotim.overqueue.model.QueueElementCallModel;
import tk.brabotim.overqueue.model.QueueElementConfirmationModel;
import tk.brabotim.overqueue.model.QueueListingModel;
import tk.brabotim.overqueue.repository.ClientRepository;
import tk.brabotim.overqueue.repository.PendingQueueElementRepository;
import tk.brabotim.overqueue.repository.QueueElementRepository;
import tk.brabotim.overqueue.repository.QueueRepository;
import tk.brabotim.overqueue.repository.StatusRepository;
import tk.brabotim.overqueue.service.QueueService;

@Controller
public class ReceptionController {

	private static final Logger LOG = LogManager.getLogger(ReceptionController.class);
	
	private QueueRepository queueRepository;
	private QueueElementRepository queueElementRepository;
	private QueueService queueService;
	private ClientRepository clientRepository;
	private StatusRepository statusRepository;
	private PendingQueueElementRepository pendingQueueElementRepository;
	
	@Autowired
	public ReceptionController(QueueRepository queueRepository, QueueElementRepository queueElementRepository,
            QueueService queueService, ClientRepository clientRepository, StatusRepository statusRepository,
            PendingQueueElementRepository pendingQueueElementRepository) {
        
        this.queueRepository = queueRepository;
        this.queueElementRepository = queueElementRepository;
        this.queueService = queueService;
        this.clientRepository = clientRepository;
        this.statusRepository = statusRepository;
        this.pendingQueueElementRepository = pendingQueueElementRepository;
    }

    @GetMapping("/recepcao")
	public ModelAndView homeReception() {
		LOG.info("Request method: GET /recepcao");

		return new QueueListingModel(queueService).listQueues();
	}

	@GetMapping("/recepcao/queueElement/call")
	public ModelAndView callQueueElement(@RequestParam("id") Long id) {
		LOG.info("Request method: GET /recepcao/queueElement/confirm{}", id);

		return new QueueElementCallModel(queueElementRepository, pendingQueueElementRepository).call(id);
	}

	@GetMapping("/recepcao/queueElement/confirm")
	public ModelAndView concludeQueueElement(@RequestParam("id") Long id) {

		LOG.info("Request method: GET /recepcao/queueElement/confirm{}", id);
		return new QueueElementConfirmationModel(
		        statusRepository, queueElementRepository, pendingQueueElementRepository
        ).confirm(id);
	}

	@GetMapping("/recepcao/queueElement/cancel")
	public ModelAndView deleteQueueElement(@RequestParam("id") Long id) {
		LOG.info("Request method: GET /recepcao/queueElement/cancel?id={}", id);

		return new PendingQueueElementCancellationModel(
		        pendingQueueElementRepository, statusRepository, queueElementRepository
        ).cancelQueueElement(id);
	}

	@PostMapping("/queueElement/create")
	public ModelAndView createQueueElement(@ModelAttribute("queueElement") 
	@Valid QueueElementDTO queueElementDTO, BindingResult result) {
		LOG.info("Request method: GET recepcao/queueElement/create");
	
		if (result.hasErrors()) {
			return new ModelAndView("redirect:/recepcao?errorParameters");
		}
		
		return new PendingQueueElementCreationModel(
		        clientRepository, statusRepository, 
				queueRepository, queueElementRepository, pendingQueueElementRepository
		).createPendingQueueElement(queueElementDTO);
	}

	@GetMapping("/recepcao/queueElement/findOne")
	@ResponseBody
	public Long findOneQE(Long id) {
		LOG.info("findOneQE "+id);
		
		return id;
	}
}