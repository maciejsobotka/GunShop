package ntbd.entities;

import java.util.Date;
import java.util.Set;

public class Weapon {
	protected String name;
	protected String code;
	protected String type;
	protected Date addDate;
	protected Set<Accessory> accessories;
	protected Set<Invoice> invoices;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Set<Accessory> getAccessories() {
		return accessories;
	}
	public void setAccessories(Set<Accessory> accessories) {
		this.accessories = accessories;
	}
	public Set<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public String toString() {
		return " [Weapon] " + name + " " + code + " " + type;
	}
}
