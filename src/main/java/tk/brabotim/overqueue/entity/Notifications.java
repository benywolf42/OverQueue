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
import javax.validation.constraints.NotNull;

@Entity
public class Notifications {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idNotifications")
    private Long id;
	
	@Column(nullable = true, length = 200)
    private String message;
	
	@NotNull
    private LocalDateTime notificationTime;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(LocalDateTime notificationTime) {
		this.notificationTime = notificationTime;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}
