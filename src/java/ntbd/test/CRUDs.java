package ntbd.test;

import java.util.Calendar;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.entities.Accessory;
import ntbd.entities.Client;
import ntbd.entities.Firearm;
import ntbd.entities.Invoice;

public class CRUDs {

	  private PersistenceManager pm;

	  public CRUDs(PersistenceManager pm){
		  this.pm = pm;
	  }
	  
	  public Client updateClient(String oldName, String newName, String newCity, String newStreet, String newPostCode){
		  Transaction tx = pm.currentTransaction();
		  tx.begin();
		  @SuppressWarnings("unchecked")
		  Query<Client> q = pm.newQuery("SELECT UNIQUE this "
					  + "FROM ntbd.entities.Client "
					  + "WHERE name == n "
					  + "PARAMETERS String n ");
		  Client result = (Client) q.execute(oldName);
		  result.setName(newName);
		  result.getAddress().setCity(newCity);
		  result.getAddress().setStreet(newStreet);
		  result.getAddress().setPostcode(newPostCode);
		  tx.commit();
		  return result;
	  }
	  
	  public String deleteClient(String name){
		  Transaction tx = pm.currentTransaction();
		  tx.begin();
		  @SuppressWarnings("unchecked")
		  Query<Client> q = pm.newQuery("SELECT UNIQUE this "
					  + "FROM ntbd.entities.Client "
					  + "WHERE name == n "
					  + "PARAMETERS String n ");
		  Client result = (Client) q.execute(name);
		  String text = "Deleted:\n" + result.toString()
		  				+ "\n" + result.getAddress().toString();
		  for(Invoice i : result.getInvoices())
  				text += "\n" + i.toString();
		  pm.deletePersistentAll(result.getInvoices());
		  pm.deletePersistent(result.getAddress());
		  pm.deletePersistent(result);
		  tx.commit();
		  return text;
	  }

	public Accessory addAccessory(String name, String code, String gunName, int gunNum) {
		Transaction tx = pm.currentTransaction();
		tx.begin();
		@SuppressWarnings("unchecked")
		Query<Firearm> q = pm.newQuery("SELECT this "
				  	+ "FROM ntbd.entities.Firearm "
				  	+ "WHERE name == n "
				  	+ "PARAMETERS String n ");
		@SuppressWarnings("unchecked")
		List<Firearm> result = (List<Firearm>) q.execute(gunName);
		Accessory acc = new Accessory();
		acc.setName(name);
		acc.setCode(code);
		Calendar c = Calendar.getInstance();
		acc.setAddDate(c.getTime());
		result.get(gunNum).getAccessories().add(acc);
		pm.makePersistent(acc);
		tx.commit();
		return acc;
	}

	public String deleteAccessory(String gun, String name, int gunNum) {
		Transaction tx = pm.currentTransaction();
		tx.begin();
		@SuppressWarnings("unchecked")
		Query<Accessory> q = pm.newQuery("SELECT this "
			  	+ "FROM ntbd.entities.Accessory "
			  	+ "WHERE name == n2 && weapon.name == n "
			  	+ "PARAMETERS String n, String n2 ");
		@SuppressWarnings("unchecked")
		List<Accessory> result = (List<Accessory>) q.execute(gun, name);
		String text = "Deleted:\n" + result.get(gunNum).toString();
		pm.deletePersistent(result.get(gunNum));
		tx.commit();
		return text;
	}

	public String deleteFirearm(String name, int gunNum) {
		Transaction tx = pm.currentTransaction();
		tx.begin();
		@SuppressWarnings("unchecked")
		Query<Firearm> q = pm.newQuery("SELECT this "
				  	+ "FROM ntbd.entities.Firearm "
				  	+ "WHERE name == n "
				  	+ "PARAMETERS String n ");
		@SuppressWarnings("unchecked")
		List<Firearm> result = (List<Firearm>) q.execute(name);
		String text = "Deleted:\n" + result.get(gunNum).toString();
		for(Accessory i : result.get(gunNum).getAccessories())
				text += "\n" + i.toString();
		pm.deletePersistentAll(result.get(gunNum).getAccessories());
		pm.deletePersistent(result.get(gunNum));
		tx.commit();
		return text;
	}
}
