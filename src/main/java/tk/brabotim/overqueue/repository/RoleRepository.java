package tk.brabotim.overqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tk.brabotim.overqueue.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
