package view;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import model.Message;
import model.User;
import networking.ClientService;

import javax.swing.JButton;

import controller.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClientView {

	private JFrame frame;
	private JTextField receiverTextField;
	private JTextField messageTxtMessage;
	JScrollPane scrollPaneMessages;
	JScrollPane scrollPaneFriends;
	private DefaultListModel listModelMessages = new DefaultListModel();
	private DefaultListModel listModelFriends = new DefaultListModel();

	private Message msg;
	private Message friendMsg = new Message();
	private User user;
	private User friend;
	
	/**
	 * Create the application.
	 */
	public ClientView(User user) {
		this.user = user;
		initialize();
		populateFriendsList();
		populateMessagesList();
		System.out.println(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 706, 466);
		frame.setTitle(user.getName());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				Controller.getInstance().logout(user);
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JList listFriends = new JList(listModelFriends);
		listFriends.setBounds(29, 48, 186, 248);
		frame.getContentPane().add(listFriends);
		

		scrollPaneFriends = new JScrollPane(listFriends);
		scrollPaneFriends.setBounds(29, 48, 186, 248);
		frame.getContentPane().add(scrollPaneFriends);
		
		listFriends.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					friend = (User) listFriends.getSelectedValue();
					receiverTextField.setText(friend.getName());
				}
			}
		});
		
		JLabel lblAvailableFriends = new JLabel("Available Friends");
		lblAvailableFriends.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvailableFriends.setBounds(29, 21, 186, 14);
		frame.getContentPane().add(lblAvailableFriends);
		
		JList listMessages = new JList(listModelMessages);
		listMessages.setBounds(290, 48, 269, 248);
		frame.getContentPane().add(listMessages);
		

		scrollPaneMessages = new JScrollPane(listMessages);
		scrollPaneMessages.setBounds(290, 48, 269, 248);
		frame.getContentPane().add(scrollPaneMessages);
		
		listMessages.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					friendMsg = (Message) listMessages.getSelectedValue();
					receiverTextField.setText(friendMsg.getReceiver());
				}
			}
		});
		
		JLabel lblMessages = new JLabel("Messages");
		lblMessages.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessages.setBounds(290, 21, 269, 14);
		frame.getContentPane().add(lblMessages);
		
		JLabel lblSendMessage = new JLabel("Send Message");
		lblSendMessage.setBounds(29, 354, 155, 14);
		frame.getContentPane().add(lblSendMessage);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(29, 388, 46, 14);
		frame.getContentPane().add(lblTo);
		
		receiverTextField = new JTextField();
		receiverTextField.setBounds(85, 385, 86, 20);
		frame.getContentPane().add(receiverTextField);
		receiverTextField.setColumns(10);
		
		messageTxtMessage = new JTextField();
		messageTxtMessage.setBounds(221, 351, 338, 51);
		frame.getContentPane().add(messageTxtMessage);
		messageTxtMessage.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(receiverTextField.getText().isEmpty() || messageTxtMessage.getText().isEmpty() || user.getName().isEmpty()){
					JOptionPane.showMessageDialog(frame, "All fields are mandatory to be filled.","Fill empty fileds",JOptionPane.ERROR_MESSAGE);
				}
				else{
					msg = new Message();
					msg.setSender(user.getName());
					msg.setReceiver(receiverTextField.getText());
					msg.setMessage(messageTxtMessage.getText());
					//clearFields();
					Controller.getInstance().sendMessage(msg);
					JOptionPane.showMessageDialog(frame, " Message sent to "+receiverTextField.getText());
					populateMessagesList();
					System.out.println(user);
					System.out.println("Client sent:"+msg.messageInfo());

				}

			}
		});
		btnNewButton.setBounds(591, 354, 89, 48);
		frame.getContentPane().add(btnNewButton);
	}

	protected void clearFields() {

		receiverTextField.setText("");
		messageTxtMessage.setText("");
	}
	protected void populateMessagesList(){
		listModelMessages.clear();
		Message msg = new Message();
		msg.setSender("Mihai");
		msg.setReceiver("John");
		msg.setMessage("Buna Radu!");
		
		listModelMessages.addElement(msg);
		//listModelMessages.addElement(ClientService.getInstance().receiveMessage());
		
		
	}
	protected void populateFriendsList(){
		
		listModelFriends.clear();

		for(User user:Controller.getInstance().getUsers())
			listModelFriends.addElement(user);
		
	}
}
