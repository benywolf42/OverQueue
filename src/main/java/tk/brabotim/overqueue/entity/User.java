package tk.brabotim.overqueue.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 64)
    private String username;
    
    @NotNull
    @Column(length = 64)
    private String password;
    
    @NotNull
    @Column(length = 50)
    private String name;
    
    @NotNull
    @Column(length = 14)
    private String phoneNumber;
    
    //1 = ACTIVE, 0 = INACTIVE
    @NotNull
    @Column(columnDefinition = "TINYINT(1) default 1")
	private short status;

    @Transient
    private String passwordConfirm;
    
    @Transient 
    private String role;
    
    @Column(length = 64)
	private String passwordToken;
    
    @Column(nullable = true)
    private LocalDateTime passwordTokenCreatedTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
    
    public User() {}
    
    public User(String password, String username, 
    		String name, String phoneNumber,short status) {
		this.password = password;
		this.username = username;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	public Long getId() {
        return id;
    }

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short isStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public short getStatus() {
		return status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPasswordToken() {
		return passwordToken;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	public LocalDateTime getPasswordTokenCreatedTime() {
		return passwordTokenCreatedTime;
	}

	public void setPasswordTokenCreatedTime(LocalDateTime passwordTokenCreatedTime) {
		this.passwordTokenCreatedTime = passwordTokenCreatedTime;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}