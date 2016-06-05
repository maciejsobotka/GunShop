package ntbd.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntbd.entities.Accessory;
import ntbd.entities.Client;
import ntbd.entities.Firearm;
import ntbd.test.CRUDs;
import ntbd.test.CreateDb;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class CRUDWindow {

	private JFrame frmGunShop;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblStreet;
	private JLabel lblPostCode;
	private PersistenceManager pm;
	private CRUDs cruds;
	private Set<String> cities;
	private String text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDWindow window = new CRUDWindow();
					window.frmGunShop.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public CRUDWindow() throws IOException {
		pm = getPM();
		cities = new HashSet<String>();
		Query<Client> q = pm.newQuery(Client.class);
		@SuppressWarnings("unchecked")
		List<Client> result = (List<Client>) q.execute();
	    for (int i = 0; i < result.size(); ++i) {
	    	cities.add(result.get(i).getAddress().getCity());
	    }
		cruds = new CRUDs(pm);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGunShop = new JFrame();
		frmGunShop.setBounds(100, 100, 590, 326);
		frmGunShop.setTitle("Gun Shop CRUDs");
		frmGunShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGunShop.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmGunShop.getContentPane().add(panel);
		panel.setLayout(null);
		
		createPanel1();
		createPanel2();
		createPanel3();
		createPanel4();
		createPanel5();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 574, 288);
		tabbedPane.addTab( "Update Client", panel1 );
		tabbedPane.addTab( "Delete Client", panel2 );
		tabbedPane.addTab( "Add Accessory", panel3 );
		tabbedPane.addTab( "Delete Accessory", panel4 );
		tabbedPane.addTab( "Delete Firearm", panel5 );
		panel.add(tabbedPane);
		
		
	}
	
	private void createPanel1()
	{
		panel1 = new JPanel();
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(219, 11, 345, 241);
		
	    JScrollPane scroll = new JScrollPane(textPane);
	    scroll.setBounds(219, 11, 345, 241);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel1.add(scroll);
		
		textField = new JTextField();
		textField.setBounds(105, 72, 86, 20);
		panel1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(105, 103, 86, 20);
		panel1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(105, 134, 86, 20);
		panel1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(105, 165, 86, 20);
		panel1.add(textField_3);
		textField_3.setColumns(10);
		
		lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 75, 46, 14);
		panel1.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("City:");
		lblNewLabel_1.setBounds(10, 106, 46, 14);
		panel1.add(lblNewLabel_1);
		
		lblStreet = new JLabel("Street:");
		lblStreet.setBounds(10, 137, 46, 14);
		panel1.add(lblStreet);
		
		lblPostCode = new JLabel("Post Code:");
		lblPostCode.setBounds(10, 168, 65, 14);
		panel1.add(lblPostCode);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(9, 11, 104, 20);
		panel1.add(comboBox);
		
		Query<Client> q = pm.newQuery(Client.class);
		@SuppressWarnings("unchecked")
		List<Client> result = (List<Client>) q.execute();
		
		for(Client c : result)
			comboBox.addItem(c.getName());
		
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
				text = "";
		    	@SuppressWarnings("unchecked")
				Query<Client> q = pm.newQuery("SELECT UNIQUE this "
						  + "FROM ntbd.entities.Client "
						  + "WHERE name == n "
						  + "PARAMETERS String n ");
				Client result = (Client) q.execute(comboBox.getSelectedItem().toString());
		    	textField.setText(result.getName());
		    	textField_1.setText(result.getAddress().getCity());
		    	textField_2.setText(result.getAddress().getStreet());
		    	textField_3.setText(result.getAddress().getPostcode());
		    	text += "Changed Client named "
		    			+ comboBox.getSelectedItem().toString() + "\nFROM:\n"
		    			+ result.toString() + " " + result.getAddress().toString();
		    }
		});
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client c = cruds.updateClient(comboBox.getSelectedItem().toString(),
						textField.getText(),
						textField_1.getText(),
						textField_2.getText(),
						textField_3.getText());
				text+="\nTO:\n" + c.toString() + " " + c.getAddress().toString() + "\n";
				textPane.setText(text);
			}
		});
		btnUpdate.setBounds(102, 211, 89, 23);
		panel1.add(btnUpdate);
	}
	
	private void createPanel2()
	{
		panel2 = new JPanel();
		panel2.setBackground(Color.LIGHT_GRAY);
		panel2.setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(219, 11, 345, 241);
		
	    JScrollPane scroll = new JScrollPane(textPane);
	    scroll.setBounds(219, 11, 345, 241);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel2.add(scroll);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(9, 11, 104, 20);
		panel2.add(comboBox);
		
		Query<Client> q = pm.newQuery(Client.class);
		@SuppressWarnings("unchecked")
		List<Client> result = (List<Client>) q.execute();
		
		for(Client c : result)
			comboBox.addItem(c.getName());
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				text = "";
				text+= cruds.deleteClient(comboBox.getSelectedItem().toString());
				textPane.setText(text);
			}
		});
		btnDelete.setBounds(102, 211, 89, 23);
		panel2.add(btnDelete);
	}
	
	private void createPanel3()
	{
		panel3 = new JPanel();
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(219, 11, 345, 241);
		
	    JScrollPane scroll = new JScrollPane(textPane);
	    scroll.setBounds(219, 11, 345, 241);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel3.add(scroll);
		
		textField_4 = new JTextField();
		textField_4.setBounds(105, 72, 86, 20);
		panel3.add(textField_4);
		textField_2.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(105, 103, 86, 20);
		panel3.add(textField_5);
		textField_3.setColumns(10);
		
		textField_6 = new JTextField("1");
		textField_6.setBounds(105, 165, 86, 20);
		panel3.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 75, 46, 14);
		panel3.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Code:");
		lblNewLabel_1.setBounds(10, 106, 46, 14);
		panel3.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Gun:");
		lblNewLabel_2.setBounds(10, 137, 46, 14);
		panel3.add(lblNewLabel_2);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(105, 134, 104, 20);
		panel3.add(comboBox);
		
		Query<Firearm> q = pm.newQuery(Firearm.class);
		@SuppressWarnings("unchecked")
		List<Firearm> result = (List<Firearm>) q.execute();
		Set<String> farms = new HashSet<String>();
		for(Firearm c : result)
			farms.add(c.getName());
		
		for(String s : farms)
			comboBox.addItem(s);
		
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
				text = "";
				@SuppressWarnings("unchecked")
				Query<Firearm> q = pm.newQuery("SELECT this "
						  	+ "FROM ntbd.entities.Firearm "
						  	+ "WHERE name == n "
						  	+ "PARAMETERS String n ");
				@SuppressWarnings("unchecked")
				List<Firearm> result = (List<Firearm>) q.execute(comboBox.getSelectedItem().toString());
		    	text += "Choose for which "
		    			+ comboBox.getSelectedItem().toString() + " gun is the accessory - numbers from 1 to "
		    			+ result.size() + ":\n\n";
    			textPane.setText(text);
		    }
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				text = "";
				Accessory acc = cruds.addAccessory(textField_4.getText(),
						textField_5.getText(),
						comboBox.getSelectedItem().toString(),
						Integer.parseInt(textField_6.getText())-1);
				text += "Added Accessory:\n\n" + acc.toString();
				textPane.setText(text);
			}
		});
		btnAdd.setBounds(102, 211, 89, 23);
		panel3.add(btnAdd);
	}
	
	private void createPanel4()
	{
		panel4 = new JPanel();
		panel4.setBackground(Color.LIGHT_GRAY);
		panel4.setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(219, 11, 345, 241);
		
	    JScrollPane scroll = new JScrollPane(textPane);
	    scroll.setBounds(219, 11, 345, 241);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel4.add(scroll);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(9, 11, 104, 20);
		panel4.add(comboBox);
		
		JComboBox<String> comboBox2 = new JComboBox<String>();
		comboBox2.setBounds(9, 42, 104, 20);
		panel4.add(comboBox2);
		
		textField_7 = new JTextField("1");
		textField_7.setBounds(9, 73, 86, 20);
		panel4.add(textField_7);
		textField_7.setColumns(10);
		
		Query<Accessory> q = pm.newQuery(Accessory.class);
		@SuppressWarnings("unchecked")
		List<Accessory> result = (List<Accessory>) q.execute();
		Set<String> accs = new HashSet<String>();
		for(Accessory c : result)
			accs.add(c.getName());
		
		for(String s : accs)
			comboBox.addItem(s);
		
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
		    	comboBox2.removeAllItems();
				@SuppressWarnings("unchecked")
				Query<Accessory> q = pm.newQuery("SELECT this "
						  	+ "FROM ntbd.entities.Accessory "
						  	+ "WHERE name == n "
						  	+ "PARAMETERS String n ");
				@SuppressWarnings("unchecked")
				List<Accessory> result = (List<Accessory>) q.execute(comboBox.getSelectedItem().toString());
				Set<String> farms = new HashSet<String>();
				for(Accessory c : result)
					farms.add(c.getWeapon().getName());
				
				for(String s : farms)
					comboBox2.addItem(s);
		    }
		});
		
		comboBox2.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
		    	if(comboBox2.getSelectedItem() != null){
				text = "";
				@SuppressWarnings("unchecked")
				Query<Accessory> q = pm.newQuery("SELECT this "
						  	+ "FROM ntbd.entities.Accessory "
						  	+ "WHERE name == n2 && weapon.name == n "
						  	+ "PARAMETERS String n, String n2 ");
				@SuppressWarnings("unchecked")
				List<Accessory> result = (List<Accessory>) q.execute(comboBox2.getSelectedItem().toString(), comboBox.getSelectedItem().toString());
		    	text += "Choose for which "
		    			+ comboBox2.getSelectedItem().toString() + " gun is the accessory - numbers from 1 to "
		    			+ result.size() + ":\n\n";
    			textPane.setText(text);
		    	}
		    }
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				text = "";
				text+= cruds.deleteAccessory(comboBox2.getSelectedItem().toString(),
						comboBox.getSelectedItem().toString(),
						Integer.parseInt(textField_7.getText())-1);
				textPane.setText(text);
			}
		});
		btnDelete.setBounds(102, 211, 89, 23);
		panel4.add(btnDelete);
	}
	
	private void createPanel5()
	{
		panel5 = new JPanel();
		panel5.setBackground(Color.LIGHT_GRAY);
		panel5.setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(219, 11, 345, 241);
		
	    JScrollPane scroll = new JScrollPane(textPane);
	    scroll.setBounds(219, 11, 345, 241);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel5.add(scroll);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(9, 11, 104, 20);
		panel5.add(comboBox);
		
		Query<Firearm> q = pm.newQuery(Firearm.class);
		@SuppressWarnings("unchecked")
		List<Firearm> result = (List<Firearm>) q.execute();
		Set<String> farms = new HashSet<String>();
		for(Firearm c : result)
			farms.add(c.getName());
		
		for(String s : farms)
			comboBox.addItem(s);
		
		textField_8 = new JTextField("1");
		textField_8.setBounds(9, 42, 86, 20);
		panel5.add(textField_8);
		textField_8.setColumns(10);
		
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
				text = "";
				@SuppressWarnings("unchecked")
				Query<Firearm> q = pm.newQuery("SELECT this "
						  	+ "FROM ntbd.entities.Firearm "
						  	+ "WHERE name == n "
						  	+ "PARAMETERS String n ");
				@SuppressWarnings("unchecked")
				List<Firearm> result = (List<Firearm>) q.execute(comboBox.getSelectedItem().toString());
		    	text += "Choose for which "
		    			+ comboBox.getSelectedItem().toString() + " gun is the accessory - numbers from 1 to "
		    			+ result.size() + ":\n\n";
    			textPane.setText(text);
		    }
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				text = "";
				text += cruds.deleteFirearm(comboBox.getSelectedItem().toString(),
						Integer.parseInt(textField_8.getText())-1);
				textPane.setText(text);
			}
		});
		btnDelete.setBounds(102, 211, 89, 23);
		panel5.add(btnDelete);
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