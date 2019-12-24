package tk.brabotim.overqueue.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Queue {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idQueue")
    private Long id;
	
	@Column
	private Integer chairsQuantityOnTable;
	
	// 0 FOR DISABLED, 1 FOR ENABLED
	@Column(nullable = false, columnDefinition = "TINYINT(1) default 1")
	private short isEnabled;
	
	// 0 FOR CONVENTIONAL, 1 FOR PRIORITY
	@Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
	private short hasPriority;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_queue")
	private List<QueueElement> queueElement;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_queue")
	private List<PendingQueueElement> pendingQueueElement;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
	
	@Transient
	private long waitingOnQueueDuration;
	
	@Transient
	private int pendingElementsOnQueue;
	
	public Queue() {
	}
	
	public Queue(Long id, Integer chairsQuantityOnTable, short isEnabled, 
	        short hasPriority, List<QueueElement> queueElement) {
		this.id = id;
		this.chairsQuantityOnTable = chairsQuantityOnTable;
		this.isEnabled = isEnabled;
		this.hasPriority = hasPriority;
		this.queueElement = queueElement;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPendingElementsOnQueue() {
		return pendingElementsOnQueue;
	}

	public void setPendingElementsOnQueue(int pendingElementsOnQueue) {
		this.pendingElementsOnQueue = pendingElementsOnQueue;
	}

	public Integer getChairsQuantityOnTable() {
		return chairsQuantityOnTable;
	}

	public void setChairsQuantityOnTable(Integer chairsQuantityOnTable) {
		this.chairsQuantityOnTable = chairsQuantityOnTable;
	}

	public short getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(short isEnabled) {
		this.isEnabled = isEnabled;
	}

	public short getHasPriority() {
		return hasPriority;
	}

	public void setHasPriority(short hasPriority) {
		this.hasPriority = hasPriority;
	}

	public List<QueueElement> getQueueElement() {
		return queueElement;
	}

	public void setQueueElement(List<QueueElement> queueElement) {
		this.queueElement = queueElement;
	}

	public List<PendingQueueElement> getPendingQueueElement() {
		return pendingQueueElement;
	}

	public void setPendingQueueElement(List<PendingQueueElement> pendingQueueElement) {
		this.pendingQueueElement = pendingQueueElement;
	}

	public long getWaitingOnQueueDuration() {
		return waitingOnQueueDuration;
	}

	public void setWaitingOnQueueDuration(long waitingOnQueueDuration) {
		this.waitingOnQueueDuration = waitingOnQueueDuration;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}
