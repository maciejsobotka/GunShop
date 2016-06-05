package ntbd.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.entities.Client;
import ntbd.entities.Firearm;
import ntbd.entities.Invoice;
import ntbd.entities.Weapon;
import ntbd.utils.BladeInCity;
import ntbd.utils.ClientAccInvoice;
import ntbd.utils.InvoicesInCity;

public class Queries {

	  private PersistenceManager pm;

	  public Queries(PersistenceManager pm){
		  this.pm = pm;
	  }
	  
	  public List<InvoicesInCity> query1(String city){
		  Transaction tx = pm.currentTransaction();
		  tx.begin();
		  @SuppressWarnings("unchecked")
		  Query<Invoice> q = pm.newQuery("SELECT count(this), sum(cost), client "
				  + "INTO ntbd.utils.InvoicesInCity FROM ntbd.entities.Invoice "
				  + "WHERE client.address.city == city "
				  + "PARAMETERS String city "
	              + "GROUP BY client ORDER BY client ASCENDING");
		  @SuppressWarnings("unchecked")
		  List<InvoicesInCity> result = (List<InvoicesInCity>) q.execute(city);
		  tx.commit();
		  return result;
	  }
	  
	  public List<BladeInCity> query2(int x){
		  @SuppressWarnings("unchecked")
		  Query<Invoice> q = pm.newQuery("SELECT client.address.city, mw.bladeLength "
				  + "INTO ntbd.utils.BladeInCity FROM ntbd.entities.Invoice "
				  + "WHERE weapons.contains(mw) && mw.bladeLength > x "
				  + "VARIABLES ntbd.entities.MeleeWeapon mw "
				  + "PARAMETERS int x "
	              + "ORDER BY client.address.city ASCENDING");
		  @SuppressWarnings("unchecked")
		  List<BladeInCity> result = (List<BladeInCity>) q.execute(x);
		  return result;
	  }
	  
	  public Set<Client> query3(int months){
		  Date d = new Date();
		  Calendar c = Calendar.getInstance();
		  c.add(Calendar.MONTH, -months);
		  d=c.getTime();
		  @SuppressWarnings("unchecked")
		  Query<Client> q = pm.newQuery("SELECT this "
				  + "FROM ntbd.entities.Client "
				  + "WHERE invoices.contains(inv) "
				  + "&& inv.date <= now "
				  + "&& inv.accessories.isEmpty() == false "
				  + "VARIABLES ntbd.entities.Invoice inv "
				  + "PARAMETERS java.util.Date now");
		  @SuppressWarnings("unchecked")
		  List<Client> result = (List<Client>) q.execute(d);
		  Set<Client> finalResult = new HashSet<Client>();
		  for(Client cl : result)
			  finalResult.add(cl);
		  return finalResult;
	  }
	  
	  public List<ClientAccInvoice> query4(){
		  @SuppressWarnings("unchecked")
		  Query<Invoice> q = pm.newQuery("SELECT client, avg(cost) "
				  + "INTO ntbd.utils.ClientAccInvoice FROM ntbd.entities.Invoice "
				  + "WHERE weapons.isEmpty() "
				  + "GROUP BY client ORDER BY client ASCENDING");
		  @SuppressWarnings("unchecked")
		  List<ClientAccInvoice> result = (List<ClientAccInvoice>) q.execute();
		  return result;
	  }
	  
	  public List<Firearm> query5(int x){
		  @SuppressWarnings("unchecked")
		  Query<Weapon> q = pm.newQuery("SELECT this "
				  + "FROM ntbd.entities.Firearm "
				  + "WHERE invoices.isEmpty() && accessories.size() == x "
				  + "PARAMETERS int x");
		  @SuppressWarnings("unchecked")
		  List<Firearm> result = (List<Firearm>) q.execute(x);
		  return result;
	  }
}
