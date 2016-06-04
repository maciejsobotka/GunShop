package ntbd.test;

import java.util.Properties;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import ntbd.entities.*;

import javax.jdo.Extent;

public class QueryAll {

  private static PersistenceManager pm;
  private static Transaction tx;

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      getAllObjects(pm);
      pm.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void getAllObjects(PersistenceManager pm) {
    System.out.println("The following objects are in the database:");
    tx = pm.currentTransaction();
    tx.begin();
    System.out.println("[Firearms]");
    Extent<Firearm> extent = pm.getExtent(Firearm.class,true);
    for (Iterator<Firearm> i = extent.iterator(); i.hasNext();) {
    	Firearm fa = i.next();
      System.out.println(fa);
    }

    System.out.println("[Melee Weapons]");
    Extent<MeleeWeapon> extent2 = pm.getExtent(MeleeWeapon.class,false);
    for (Iterator<MeleeWeapon> i = extent2.iterator(); i.hasNext();) {
    	MeleeWeapon mw = i.next();
      System.out.println(mw);
    }

    System.out.println("[Accessories]");
    Extent<Accessory> extent3 = pm.getExtent(Accessory.class,false);
    for (Iterator<Accessory> i = extent3.iterator(); i.hasNext();) {
    	Accessory accs = i.next();
      System.out.println(accs);
    }
    
    System.out.println("[Addresses]");
    Extent<Address> extent4 = pm.getExtent(Address.class,false);
    for (Iterator<Address> i = extent4.iterator(); i.hasNext();) {
	  Address addr = i.next();
      System.out.println(addr);
    }

    System.out.println("[Invoices]");
    Extent<Invoice> extent5 = pm.getExtent(Invoice.class,false);
    for (Iterator<Invoice> i = extent5.iterator(); i.hasNext();) {
    	Invoice inv = i.next();
      System.out.println(inv);
    }
    System.out.println("[Clients]");
    Extent<Client> extent6 = pm.getExtent(Client.class,true);
    for (Iterator<Client> i = extent6.iterator(); i.hasNext();) {
    	Client cli = i.next();
      System.out.println(cli);
    }
    tx.commit();
  }

  private static PersistenceManager getPM() throws IOException	{
	Properties properties = new Properties();
	InputStream is=CreateDb.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}


