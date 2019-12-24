package tk.brabotim.overqueue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class RestaurantAddress {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idRestaurantAddress")
    private Long id;
	
	@Column(nullable = true, length = 40)
    private String street;
    
    @Column(nullable = true, length = 40)
    private String district;
    
    @Column(nullable = true, length = 10)
    private String zipCode;
    
    @Column(nullable = true, length = 40)
    private String city;
    
    @Column(nullable = true, length = 40)
    private String state;
    
    @Column(nullable = true, length = 40)
    private String country;
    
    @OneToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
    
}
