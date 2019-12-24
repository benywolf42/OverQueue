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
public class PendingQueueElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQueueElement")
    private Long id;

    @NotNull
    private LocalDateTime queueEntryTime;

    @NotNull
    @Column
    private Integer chairsQuantityAsked;

    @NotNull
    @Column(columnDefinition = "TINYINT(1) default 0")
    private short hasPriority;
    
    @NotNull
    @Column(columnDefinition = "TINYINT(1) default 0")
    private short hasReceivedSms;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_queue")
    private Queue queue;

    @Transient
    private int position;
    
    @Transient
    private String estimatedTimeForAttendance;

    public PendingQueueElement() {
        // empty constructor for jpa
    }

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

    public Integer getChairsNumberOnTable() {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getChairsQuantityAsked() {
        return chairsQuantityAsked;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

	public short getHasReceivedSms() {
		return hasReceivedSms;
	}

	public void setHasReceivedSms(short hasReceivedSms) {
		this.hasReceivedSms = hasReceivedSms;
	}

	public String getEstimatedTimeForAttendance() {
		return estimatedTimeForAttendance;
	}

	public void setEstimatedTimeForAttendance(String estimatedTimeForAttendance) {
		this.estimatedTimeForAttendance = estimatedTimeForAttendance;
	}

}
