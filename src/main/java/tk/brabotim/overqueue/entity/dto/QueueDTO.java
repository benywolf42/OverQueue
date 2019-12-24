package tk.brabotim.overqueue.entity.dto;

import java.util.List;

import tk.brabotim.overqueue.entity.Queue;
import tk.brabotim.overqueue.entity.QueueElement;

public class QueueDTO {
	private Long id;
	private Integer chairsQuantityOnTable;
	private short isEnabled;
	private short hasPriority;
	private List<QueueElement> queueElement;
	
	public Queue convertToObject() {
		return new Queue(id, chairsQuantityOnTable, isEnabled, 
				hasPriority, queueElement);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
}

