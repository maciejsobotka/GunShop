package ntbd.utils;

import ntbd.entities.Client;

public class InvoicesInCity {
	private Long numOfInvoices;
	private float sumCost;
	private Client client;
	
	public InvoicesInCity(){}
	
	public InvoicesInCity(Long numOfInvoices, float sumCost, Client client) {
		super();
		this.numOfInvoices = numOfInvoices;
		this.sumCost = sumCost;
		this.setClient(client);
	}
	
	public Long getNumOfInvoices() {
		return numOfInvoices;
	}
	public void setNumOfInvoices(Long numOfInvoices) {
		this.numOfInvoices = numOfInvoices;
	}
	public float getSumCost() {
		return sumCost;
	}
	public void setSumCost(float sumCost) {
		this.sumCost = sumCost;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String toString(){
		return " [numOfInvoices] " + numOfInvoices
				+ " [sumCost] " + sumCost
				+ client.toString();
	}


}
