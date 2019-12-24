package tk.brabotim.overqueue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.brabotim.overqueue.entity.Role;
import tk.brabotim.overqueue.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public void save(Role role) {
		roleRepository.save(role);
	}
	
	public void update(Role role) {
		roleRepository.saveAndFlush(role);
	}
	
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}
	
	public List<Role> findaAll() {
		return roleRepository.findAll();
	}
	
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}
	
}
