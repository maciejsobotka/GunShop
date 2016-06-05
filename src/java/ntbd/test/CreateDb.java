package ntbd.test;

import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.entities.*;

public class CreateDb {
	
	private static PersistenceManager pm;
	private static Transaction tx;
	private static Query<?> q;
	public static void main(String[] args) {
		try {
			pm = getPM();
		    tx = pm.currentTransaction();
		
		    tx.begin ();
		    deleteObjects(pm);
		    tx.commit();
		
		    tx.begin ();
		    createObjects(pm);
		    tx.commit();
		
		    pm.close ();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void deleteObjects (Collection<?> objects, PersistenceManager pm) 	{
		pm.deletePersistentAll (objects);
		System.out.println ("Deleted " + objects.size () + " objects.");
	}

	public static void deleteObjects(PersistenceManager pm) {
		q = pm.newQuery(Client.class);
		Collection<?> res1 = (Collection<?>) q.execute();
		for (Iterator<?> i = res1.iterator(); i.hasNext();) {
			Client d = (Client)(i.next());
			d.setAddress(null);
			d.setInvoices(null);
		}

		q = pm.newQuery(Invoice.class);
		Collection<?> res2 = (Collection<?>) q.execute();
		for (Iterator<?> i = res2.iterator(); i.hasNext();) {
			Invoice d = (Invoice)(i.next());
			d.setAccessories(null);
			d.setClient(null);
			d.setWeapons(null);
		}
	
		q = pm.newQuery(Accessory.class);
		Collection<?> res3 = (Collection<?>) q.execute();
		for (Iterator<?> i = res3.iterator(); i.hasNext();) {
			Accessory d = (Accessory)(i.next());
			d.setInvoices(null);
			d.setWeapon(null);
		}
		
		q = pm.newQuery(Address.class);
		Collection<?> res4 = (Collection<?>) q.execute();
		for (Iterator<?> i = res4.iterator(); i.hasNext();) {
			Address d = (Address)(i.next());
		  d.setClient(null);
		  d.setInvoice(null);
		}
		
		q = pm.newQuery(MeleeWeapon.class);
		Collection<?> res5 = (Collection<?>) q.execute();
		for (Iterator<?> i = res5.iterator(); i.hasNext();) {
			MeleeWeapon d = (MeleeWeapon)(i.next());
			d.setInvoices(null);
		}
		
		q = pm.newQuery(Firearm.class);
		Collection<?> res6 = (Collection<?>) q.execute();
		for (Iterator<?> i = res6.iterator(); i.hasNext();) {
			Firearm d = (Firearm)(i.next());
			d.setAccessories(null);
			d.setInvoices(null);
		}
		deleteObjects(res6,pm);
		deleteObjects(res5,pm);
		deleteObjects(res4,pm);
		deleteObjects(res3,pm);
		deleteObjects(res2,pm);
		deleteObjects(res1,pm);
	}

	public static void createObjects(PersistenceManager pm) throws IOException 	{
		List<Object> objects = new LinkedList<Object> ();
		Calendar c = Calendar.getInstance();
		int n=10;
		
		Random rnd = new Random();
		List<String> cities = Files.readAllLines(Paths.get("files", "cities.txt"));
		List<String> streets = Files.readAllLines(Paths.get("files", "streets.txt"));
		List<String> postcodes = Files.readAllLines(Paths.get("files", "stateCode.txt"));
		Address[] addresses = new Address[n*2];
		for(int i=0 ; i<addresses.length; ++i)
		{
			addresses[i] = new Address();
			addresses[i].setCity(cities.get(rnd.nextInt(cities.size())));
			addresses[i].setStreet(streets.get(rnd.nextInt(streets.size())));
			addresses[i].setPostcode(postcodes.get(rnd.nextInt(postcodes.size())));
			objects.add(addresses[i]);
		}
		  
		List<String> names = Files.readAllLines(Paths.get("files", "firstNames.txt"));
		List<String> surnames = Files.readAllLines(Paths.get("files", "lastNames.txt"));
		
		int[] ar1 = new int[names.size()];
		int[] ar2 = new int[surnames.size()];
		for(int i=0; i<ar1.length; ++i)
			ar1[i]=i;
		for(int i=0; i<ar2.length; ++i)
			ar2[i]=i;
		shuffleArray(ar1);
		shuffleArray(ar2);
		
		Client[] clients = new Client[n*2];
		for(int i=0 ; i<clients.length; ++i)
		{
			clients[i] = new Client();
			clients[i].setName(names.get(ar1[i]) + " " + surnames.get(ar2[i]));
			clients[i].setAddress(addresses[i]);
			objects.add(clients[i]);
		}

		List<String> accessories = Files.readAllLines(Paths.get("files", "accessories.txt"));
	  	List<String> codes = Files.readAllLines(Paths.get("files", "codes.txt"));
	  	
		int[] ar3 = new int[codes.size()];
		for(int i=0; i<ar3.length; ++i)
			ar3[i]=i;
		shuffleArray(ar3);
		
	  	Accessory[] accs = new Accessory[n*6];
	  	for(int i=0; i<accs.length; ++i)
	  	{
	  		accs[i] = new Accessory();
	  		accs[i].setName(accessories.get(rnd.nextInt(accessories.size())));
	  		accs[i].setCode(codes.get(ar3[i]));
			int y = rnd.nextInt(10) + 1995;
			int m = rnd.nextInt(12) + 1;
			int d = rnd.nextInt(28) + 1;
			c.set(y,m,d);
			accs[i].setAddDate(c.getTime());
	  		objects.add(accs[i]);
	  	}
		 
		Invoice[] invcs = new Invoice[n*6];
		for(int i=0 ; i<invcs.length; ++i)
		{
			invcs[i] = new Invoice();
			invcs[i].setCost(rnd.nextFloat() * 2500);
			int y = rnd.nextInt(15) + 1997;
			int m = rnd.nextInt(12) + 1;
			int d = rnd.nextInt(28) + 1;
			c.set(y,m,d);
			invcs[i].setDate(c.getTime());
			y += rnd.nextInt(2)+1;
            m = (m + rnd.nextInt(12)) % 12 + 1 ;
            d = (d + rnd.nextInt(28)) % 28 + 1;
            c.set(y,m,d);
            invcs[i].setDueDate(c.getTime());
            objects.add(invcs[i]);
		}
		
		List<String> mweapons = Files.readAllLines(Paths.get("files", "meleeWeapons.txt"));
				
		MeleeWeapon[] mweaps = new MeleeWeapon[n];
		for(int i=0; i<mweaps.length; ++i)
		{
			mweaps[i] = new MeleeWeapon();
			String weap = mweapons.get(rnd.nextInt(mweapons.size()));
			mweaps[i].setName(weap);
			mweaps[i].setCode(codes.get(ar3[accs.length+i]));
			mweaps[i].setType(weap);
			int y = rnd.nextInt(10) + 1995;
			int m = rnd.nextInt(12) + 1;
			int d = rnd.nextInt(28) + 1;
			c.set(y,m,d);
			mweaps[i].setAddDate(c.getTime());
			mweaps[i].setBladeLength(rnd.nextInt(50)+5);
			objects.add (mweaps[i]);
		}
		
		List<String> firearms = Files.readAllLines(Paths.get("files", "guns.txt"));
		List<String> wtypes = Files.readAllLines(Paths.get("files", "weapons.txt"));
		
		Firearm[] farms = new Firearm[n*2];
		for(int i=0; i<farms.length; ++i)
		{
			farms[i] = new Firearm();
			farms[i].setName(firearms.get(rnd.nextInt(firearms.size())));
			farms[i].setCode(codes.get(ar3[accs.length+mweaps.length+i]));
			farms[i].setType(wtypes.get(rnd.nextInt(wtypes.size())));
			int y = rnd.nextInt(10) + 1995;
			int m = rnd.nextInt(12) + 1;
			int d = rnd.nextInt(28) + 1;
			c.set(y,m,d);
			farms[i].setAddDate(c.getTime());
			farms[i].setBarrelLength(rnd.nextInt(30)+5);
			objects.add (farms[i]);
		}
		int xx = 0;
		for(int i=0; i<farms.length; ++i)
		{
			HashSet<Accessory> hs = new HashSet<Accessory>();
			if(i%2==0){
				hs.add(accs[i+xx]);
				hs.add(accs[i+xx+1]);
				hs.add(accs[i+xx+2]);
				hs.add(accs[i+xx+3]);
				xx+=3;
			}else{
				hs.add(accs[i+xx]);
				hs.add(accs[i+xx+1]);
				xx+=1;
			}
			farms[i].setAccessories(hs);
		}
		for(int i=0 ; i<invcs.length; ++i)
		{
			HashSet<Weapon> hs = new HashSet<Weapon>();
			HashSet<Accessory> hs2 = new HashSet<Accessory>();
			int x = rnd.nextInt(5);
			for(int j = 0; j < x; ++j){
				if(rnd.nextInt(2)==0){
					int l = rnd.nextInt(mweaps.length);
					hs.add(mweaps[l]);
				}else{
					int l = rnd.nextInt(farms.length);
					hs.add(farms[l]);
				}
			}
			x = rnd.nextInt(5);
			for(int j = 0; j < x; ++j){
				int l = rnd.nextInt(accs.length);
				hs2.add(accs[l]);
			}
			invcs[i].setWeapons(hs);
			invcs[i].setAccessories(hs2);
		}
		for(int i=0 ; i<clients.length; ++i)
		{
			HashSet<Invoice> hs = new HashSet<Invoice>();
			hs.add(invcs[i]);
			hs.add(invcs[n*2+i]);
			hs.add(invcs[n*4+i]);
			clients[i].setInvoices(hs);
		}
		pm.makePersistentAll (objects);
		System.out.println ("Created " + objects.size () + " new objects.");
	}	
	
	private static void shuffleArray(int[] ar)
  	{
  		Random rnd = ThreadLocalRandom.current();
  		for (int i = 0; i < ar.length; ++i) {
  			int index = rnd.nextInt(ar.length);
  			int a = ar[index];
  			ar[index] = ar[i];
  			ar[i] = a;
  		}
  	}

  	private static PersistenceManager getPM() throws IOException {
  		Properties properties = new Properties();
  		InputStream is=CreateDb.class.getClassLoader().getResourceAsStream("datanucleus.properties");
  		if (is == null) {
  			throw new FileNotFoundException("Could not find datanucleus.properties file that defines the Datanucles persistence setup.");
  		}
  		properties.load(is);
  		PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
  		return pmfactory.getPersistenceManager();
  	}
}
