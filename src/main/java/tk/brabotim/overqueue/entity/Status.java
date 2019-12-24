package tk.brabotim.overqueue.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Status {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idStatus;
	
	@Column(name = "name", length = 25)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_status")
	private List<QueueElement> queueElement;
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QueueElement> getQueueElement() {
		return queueElement;
	}

	public void setQueueElement(List<QueueElement> queueElement) {
		this.queueElement = queueElement;
	}

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

}
