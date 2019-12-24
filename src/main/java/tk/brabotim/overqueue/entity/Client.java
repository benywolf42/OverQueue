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

@Entity
public class Client {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idClient")
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = true, length = 15)
    private String phoneNumber;
    
    @Column(nullable = true, length = 40)
    private String email;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_client")
    private List<QueueElement> queueElement;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<QueueElement> getQueueElement() {
        return queueElement;
    }

    public void setQueueElement(List<QueueElement> queueElement) {
        this.queueElement = queueElement;
    }

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
