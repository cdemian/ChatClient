package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import controller.Controller;
import model.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView {

	private JFrame frmConnect;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;
	
	User user;


	/**
	 * Create the view.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		user = new User();
		frmConnect = new JFrame();
		frmConnect.setVisible(true);
		frmConnect.setTitle("Connect");
		frmConnect.setBounds(100, 100, 361, 300);
		frmConnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnect.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(58, 48, 46, 14);
		frmConnect.getContentPane().add(lblName);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(209, 45, 86, 20);
		frmConnect.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(58, 89, 46, 14);
		frmConnect.getContentPane().add(lblEmail);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(209, 86, 86, 20);
		frmConnect.getContentPane().add(emailTextField);
		emailTextField.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(58, 130, 46, 14);
		frmConnect.getContentPane().add(lblPhone);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(209, 127, 86, 20);
		frmConnect.getContentPane().add(phoneTextField);
		phoneTextField.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(nameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmConnect, "All fields are mandatory to be filled.","Fill empty fileds",JOptionPane.ERROR_MESSAGE);
					
				}
				else{
					user.setName(nameTextField.getText());
					user.setEmail(emailTextField.getText());
					user.setPhone(phoneTextField.getText());

					ClientView view = new ClientView(user);
					Controller.getInstance().addUser(user);
					JOptionPane.showMessageDialog(frmConnect, nameTextField.getText()+" is connected","Hello,"+nameTextField.getText(),JOptionPane.INFORMATION_MESSAGE);
					clearFields();

					System.out.println("Login view:"+user);

				}
					
				
			}
		});
		btnConnect.setBounds(45, 174, 250, 23);
		frmConnect.getContentPane().add(btnConnect);
	}

	protected void clearFields() {

		nameTextField.setText("");
		emailTextField.setText("");
		phoneTextField.setText("");
	}
}
