package tk.brabotim.overqueue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.repository.ClientRepository;

@Service
public class ClientService {	
	
	@Autowired
	private ClientRepository clientRepository;
	
	public void save(Client client) {
		clientRepository.save(client);
	}
	
	public void update(Client client) {
		clientRepository.saveAndFlush(client);
	}

	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findByPhoneNumber(String phoneNumber) {
		return clientRepository.findByPhoneNumber(phoneNumber);
	}
	
	public Optional<Client> findById(Long id) {
		return clientRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
	
	public Client findByNameAndPhoneNumber(String name, String phoneNumber) {
		return clientRepository.findByNameAndPhoneNumber(name, phoneNumber);
	}
//TODO	
//	public List<Client> findByQueueElementId(Long id) {
//		return clientRepository.findByQueueElementId(id);
//	}

}
