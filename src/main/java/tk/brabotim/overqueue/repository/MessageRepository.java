package tk.brabotim.overqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tk.brabotim.overqueue.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
