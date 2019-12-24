package tk.brabotim.overqueue.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueueElementDTO {
	
	@NotNull
	private Integer chairsQuantityAsked;
	
	@NotNull
	private short hasPriority;
	
	@Size(max = 14)
	private String phoneNumber;
	
	@NotNull
	private String name;

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
	
}
