package tk.brabotim.overqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tk.brabotim.overqueue.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	User findBySpecificFieldsById(Long id);
	
	@Query("SELECT u FROM User u WHERE u.status = 1")
	List <User> findAllActive();
}
