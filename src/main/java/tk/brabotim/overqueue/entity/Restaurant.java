package tk.brabotim.overqueue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Restaurant {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idRestaurant")
    private Long id;
	
	@Column(nullable = false, length = 40)
    private String name;
	
	@Column(nullable = true, length = 15)
    private String phoneNumber;
	
	@Column(nullable = true, length = 40)
	private String email;
    
    @Column(nullable = false)
    private int toleranceTime;
    
    @Column(nullable = false)
    private int estimatedLengthOfStayInMinutes;
	
    @Lob
    @Column(length=100000)
    private byte[] image;
    
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getEstimatedLengthOfStayInMinutes() {
		return estimatedLengthOfStayInMinutes;
	}

	public void setEstimatedLengthOfStayInMinutes(int estimatedLengthOfStayInMinutes) {
		this.estimatedLengthOfStayInMinutes = estimatedLengthOfStayInMinutes;
	}

	public int getToleranceTime() {
		return toleranceTime;
	}

	public void setToleranceTime(int toleranceTime) {
		this.toleranceTime = toleranceTime;
	}

}
