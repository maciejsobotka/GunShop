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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import ntbd.entities.Client;
import ntbd.entities.Firearm;
import ntbd.test.CreateDb;
import ntbd.test.Queries;
import ntbd.utils.BladeInCity;
import ntbd.utils.ClientAccInvoice;
import ntbd.utils.InvoicesInCity;

import javax.swing.JComboBox;

public class CRUDWindow {

	private JFrame frmGunShop;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private PersistenceManager pm;
	private Queries qr;
	private Set<String> cities;

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
		qr = new Queries(pm);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGunShop = new JFrame();
		frmGunShop.setBounds(100, 100, 590, 300);
		frmGunShop.setTitle("Gun Shop");
		frmGunShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGunShop.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmGunShop.getContentPane().add(panel);
		panel.setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(219, 11, 345, 241);
		
	    JScrollPane scroll = new JScrollPane(textPane);
	    scroll.setBounds(219, 11, 345, 241);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll);
		
		textField = new JTextField();
		textField.setBounds(10, 74, 89, 20);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText("0");
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 121, 89, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("0");
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 215, 89, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("0");
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(10, 11, 46, 14);
		panel.add(lblCity);
		
		JLabel lblBlade = new JLabel("Blade length:");
		lblBlade.setBounds(10, 58, 63, 14);
		panel.add(lblBlade);
		
		JLabel lblMonths = new JLabel("Months:");
		lblMonths.setBounds(10, 105, 46, 14);
		panel.add(lblMonths);
		
		JLabel lblNumOfAccessories = new JLabel("Num of accessories:");
		lblNumOfAccessories.setBounds(10, 200, 100, 14);
		panel.add(lblNumOfAccessories);
		
		JButton btnNewButton = new JButton("Query 1");
		btnNewButton.setBounds(120, 26, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Query 2");
		btnNewButton_1.setBounds(120, 73, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Query 3");
		btnNewButton_2.setBounds(120, 120, 89, 23);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Query 4");
		btnNewButton_3.setBounds(120, 167, 89, 23);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Query 5");
		btnNewButton_4.setBounds(120, 214, 89, 23);
		panel.add(btnNewButton_4);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 27, 100, 20);
		panel.add(comboBox);
		
		for(String c : cities)
			comboBox.addItem(c);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Invoices and sum cost of clients from city " + comboBox.getSelectedItem() +":\n\n";
				List<InvoicesInCity> iic = qr.query1(comboBox.getSelectedItem().toString());
				for(InvoicesInCity i : iic){
					text = text + i.toString() 
						+ i.getClient().getAddress().toString()
						+ "\n\n";
				}
				text = text + "\n";
				textPane.setText(text);
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Cities in which clients bought blades longer than " + textField.getText() +" cm:\n\n";
				List<BladeInCity> bic = qr.query2(Integer.parseInt(textField.getText()));
				for(BladeInCity b : bic){
					text = text + b.toStirng() + "\n\n";
				}
				textPane.setText(text);
			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Clients with invoice older than " + textField_1.getText() +" months that contains accessories:\n\n";
				Set<Client> cls = qr.query3(Integer.parseInt(textField_1.getText()));
				for(Client c : cls){
					text = text + c.toString() + "\n\n";
				}
				textPane.setText(text);
			}
		});
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Clients and avgCost of invoices for accessories only:\n\n";
				List<ClientAccInvoice> cai = qr.query4();
				for(ClientAccInvoice d : cai){
					text = text + d.toString() + "\n\n";
				}
				textPane.setText(text);
			}
		});
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Weapons with " + textField_2.getText() +" accessories that were never bought:\n\n";
				List<Firearm> wps = qr.query5(Integer.parseInt(textField_2.getText()));
				for(Firearm w : wps){
					text = text + w.toString() + " [AccNum] " + w.getAccessories().size() + "\n\n";
				}
				textPane.setText(text);
			}
		});
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