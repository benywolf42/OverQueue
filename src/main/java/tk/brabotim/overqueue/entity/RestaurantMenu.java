package tk.brabotim.overqueue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class RestaurantMenu {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Lob
    @Column(length=100000)
    private byte[] menu;
	
	@Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
	private short isActive;
	
	@OneToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
}
