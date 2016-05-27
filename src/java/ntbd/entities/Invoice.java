package ntbd.entities;

import java.util.Date;
import java.util.Set;

public class Invoice {
	private float cost;
	private Date date;
	private Date dueDate;
	private Client client;
	private Address address;
	private Set<Weapon> weapons;
	private Set<Accessory> accessories;
	
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Set<Weapon> getWeapons() {
		return weapons;
	}
	public void setWeapons(Set<Weapon> weapons) {
		this.weapons = weapons;
	}
	public Set<Accessory> getAccessories() {
		return accessories;
	}
	public void setAccessories(Set<Accessory> accessories) {
		this.accessories = accessories;
	}
	
	public String toString() {
		return " [Invoice] " + cost;
	}
}
