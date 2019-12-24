package tk.brabotim.overqueue.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class QueueElement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idQueueElement")
	private Long id;

	@NotNull
	private LocalDateTime queueEntryTime;

	private LocalDateTime queueExitTime;

	// 1 = MONDAY,..., 7 = SUNDAY
	@NotNull
	@Column(columnDefinition = "TINYINT(7)")
	private short weekDay;

	@NotNull
	@Column
	private Integer chairsQuantityAsked;

	// 0 = NORMAL, 1 = PRIORITY
	@NotNull
	@Column(columnDefinition = "TINYINT(1) default 0")
	private short hasPriority;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_status")
	private Status status;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_queue")
	private Queue queue;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_client")
	private Client client;

	@Transient
	private int position;

	@Transient
	private String phoneNumber;

	@Transient
	private String name;

	public int getPosition() {
		return position;
	}

	public void setPosition(int aux) {
		this.position = aux;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getQueueEntryTime() {
		return queueEntryTime;
	}

	public void setQueueEntryTime(LocalDateTime queueEntryTime) {
		this.queueEntryTime = queueEntryTime;
	}

	public LocalDateTime getQueueExitTime() {
		return queueExitTime;
	}

	public void setQueueExitTime(LocalDateTime queueExitTime) {
		this.queueExitTime = queueExitTime;
	}

	public Integer getChairsQuantityAsked() {
		return chairsQuantityAsked;
	}

	public void setChairsQuantityAsked(Integer chairsQuantityAsked) {
		this.chairsQuantityAsked = chairsQuantityAsked;
	}

	public short getHasPriority() {
		return hasPriority;
	}

	public void setHasPriority(short hasPriority) {
		this.hasPriority = hasPriority;
	}

	public short getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(short weekDay) {
		this.weekDay = weekDay;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
