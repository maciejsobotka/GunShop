package ntbd.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
import ntbd.test.CreateDb;
import ntbd.test.Queries;

import javax.swing.JComboBox;

public class MainWindow {

	private JFrame frmGunShop;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private PersistenceManager pm;
	private Queries qr;
	private String[] cities;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() throws IOException {
		pm = getPM();
		Query<Client> q = pm.newQuery(Client.class);
		@SuppressWarnings("unchecked")
		List<Client> result = (List<Client>) q.execute();
		cities = new String[result.size()];
	    for (int i = 0; i < result.size(); ++i) {
	    	cities[i] = (result.get(i).getAddress().getCity());
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
		textField.setBounds(10, 61, 89, 20);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText("0");
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 116, 89, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("0");
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 147, 89, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("0");
		
		textField_3 = new JTextField();
		textField_3.setBounds(10, 171, 89, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText("0");
		
		JLabel lblZ = new JLabel("Z2");
		lblZ.setBounds(10, 11, 46, 14);
		panel.add(lblZ);
		
		JLabel lblZ_1 = new JLabel("Z3");
		lblZ_1.setBounds(10, 97, 46, 14);
		panel.add(lblZ_1);
		
		JButton btnNewButton = new JButton("Query a");
		btnNewButton.setBounds(120, 26, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Query b");
		btnNewButton_1.setBounds(120, 60, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Query a");
		btnNewButton_2.setBounds(120, 115, 89, 23);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Query b");
		btnNewButton_3.setBounds(120, 146, 89, 23);
		panel.add(btnNewButton_3);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 27, 100, 20);
		panel.add(comboBox);
		for(String c : cities)
			comboBox.addItem(c);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Projects executed by employees from city " + comboBox.getSelectedItem() +":\n\n";
				/*List<Project> projs = qr.l4Query1A(comboBox.getSelectedItem().toString());
				for(Project p : projs){
					text = text + p.toString() + "\n\n";
					for(Task t : p.getTasks()){
						text = text + t.toString() + "\n";
					}
					text = text + "\n";
				}
				textPane.setText(text);*/
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Projects executed by employees working longer than " + textField.getText() +" months:\n\n";
				/*List<Project> projs = qr.l4Query1B(Integer.parseInt(textField.getText()));
				for(Project p : projs){
					text = text + p.toString() + "\n\n";
					for(Task t : p.getTasks()){
						text = text + t.toString() + "\n";
					}
					text = text + "\n";
				}
				textPane.setText(text);*/
			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Deleted employees hired in projects with all tasks ended till " + textField_1.getText() +" months ago:\n\n";
				/*List<Employee> empls = qr.l4Query2A(Integer.parseInt(textField_1.getText()));
				for(Employee e : empls){
					text = text + e.toString() + "\n\n";
				}
				textPane.setText(text);
				qr.deleteEmployees(empls);*/
			}
		});
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "Deleted departments where average salary is between " + textField_2.getText() +" and " + textField_3.getText() + ":\n\n";
				/*List<Department> depts = qr.l4Query2B(Integer.parseInt(textField_2.getText()),Integer.parseInt(textField_3.getText()));
				if(!depts.contains(null)){
					for(Department d : depts){
						text = text + d.toString() + "\n\n";
					}
					qr.deleteDepartments(depts);
				}
				textPane.setText(text);*/
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

