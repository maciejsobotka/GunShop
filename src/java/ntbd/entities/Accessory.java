package ntbd.entities;

import java.util.Date;
import java.util.Set;

public class Accessory {
	private String name;
	private String code;
	private Date addDate;
	private Firearm weapon;
	private Set<Invoice> invoices;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Firearm getWeapon() {
		return weapon;
	}
	public void setWeapon(Firearm weapon) {
		this.weapon = weapon;
	}
	public Set<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public String toString() {
		return " [Accessory] " + name + " " + code + " " + addDate;
	}
}
