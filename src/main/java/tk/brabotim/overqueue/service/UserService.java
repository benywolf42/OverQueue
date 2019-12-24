package tk.brabotim.overqueue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tk.brabotim.overqueue.entity.Role;
import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.repository.RoleRepository;
import tk.brabotim.overqueue.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public void save(User user) {
		Optional<Role> userRole = roleRepository.findById(1L);
		Optional<Role> adminRole = roleRepository.findById(2L);
		List<Role> roles = new ArrayList<>();

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

		if (userRole.isPresent() && adminRole.isPresent()) {

			roles.add(userRole.get());

			if (user.getRole().equalsIgnoreCase("ADMIN")) {

				roles.add(adminRole.get());
			}
		}

		user.setStatus((short) 1);
		user.setRoles(roles);

		userRepository.save(user);
	}
	
	public void update(User user) {
		Optional<Role> userRole = roleRepository.findById(1L);
		Optional<Role> adminRole = roleRepository.findById(2L);
		List<Role> roles = new ArrayList<>();
	

		if (userRole.isPresent() && adminRole.isPresent()) {

			roles.add(userRole.get());

			if (user.getRole().equalsIgnoreCase("ADMIN")) {
				roles.add(adminRole.get());
			}
		}

		user.setStatus((short) 1);
		user.setRoles(roles);

		userRepository.save(user);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public List<User> findAllActive() {
		return userRepository.findAllActive();
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}