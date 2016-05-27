package ntbd.test;

import java.util.Calendar;
import java.util.Properties;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import ntbd.entities.*;

public class CreateDb {
	
	private static PersistenceManager pm;
	private static Transaction tx;
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

	public static void deleteObjects(PersistenceManager pm) 	{

	}

	public static void createObjects(PersistenceManager pm) throws IOException 	{
		List<Object> objects = new LinkedList<Object> ();
		Calendar.getInstance();
		Accessory acc = new Accessory();
		Address addr = new Address();
		Client cli = new Client();
		Firearm fa = new Firearm();
		Invoice in = new Invoice();
		MeleeWeapon mw = new MeleeWeapon();
		objects.add(acc);
		objects.add(addr);
		objects.add(cli);
		objects.add(fa);
		objects.add(in);
		objects.add(mw);

		pm.makePersistentAll (objects);
		System.out.println ("Created " + objects.size () + " new objects.");
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
