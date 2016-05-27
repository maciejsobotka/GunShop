package ntbd.entities;

import java.util.Set;

public class Client {	
	private String name;
	private Address address;
	private Set<Invoice> invoices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public String toString() {
		return " [Client] " + name;
	}
}
