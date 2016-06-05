package ntbd.utils;

import ntbd.entities.Client;

public class ClientAccInvoice {

	private Client client;
	private double avgCost;
	
	public ClientAccInvoice(){}
	
	public ClientAccInvoice(Client client, double avgCost) {
		super();
		this.client = client;
		this.avgCost = avgCost;
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public double getAvgCost() {
		return avgCost;
	}
	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}
	
	public String toString(){
		return client.toString() + " [avgInvoice] " + avgCost;
	}
}
