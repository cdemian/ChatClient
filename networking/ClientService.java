package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import model.Message;
import model.User;
import exceptions.UserAlreadyInUse;

public class ClientService {
	private static ClientService instance;
	private Socket socket;
	private DataOutputStream os;
	private DataInputStream is;

	public static ClientService getInstance(){
		if(instance == null)
			instance = new ClientService();
		return instance;
	}
	public ClientService() {
		connectToServer();
	}

	public void connectToServer() {
		try {
			socket = new Socket("localhost", 6066);
			socket.setKeepAlive(true);
			os = new DataOutputStream(socket.getOutputStream());
			is = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Cannot connect to server");
			e.printStackTrace();
		}
	}

	public void logout(User user) {
		try {
			if (socket.isConnected() == false) {
				// logout message: int of value -1
				os.writeInt(-1);
				os.flush();
				
				os.writeInt(user.getName().length());
				os.flush();
				os.writeBytes(user.getName());
				os.flush();
				
				os.writeInt(user.getEmail().length());
				os.flush();
				os.writeBytes(user.getEmail());
				os.flush();
				
				os.writeInt(user.getPhone().length());
				os.flush();
				os.writeBytes(user.getPhone());
				os.flush();
				socket.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean login(String name, String email,String phone) {
		try {
			int action = 0;
			os.writeInt(action);
			os.flush();

			os.writeInt(name.length());
			os.flush();
			os.writeBytes(name);
			os.flush();
			
			os.writeInt(email.length());
			os.flush();
			os.writeBytes(email);
			os.flush();
			
			os.writeInt(phone.length());
			os.flush();
			os.writeBytes(phone);
			os.flush();
			
			int res = is.readInt();

			if (res == 1) {
				return true;
			}

			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}



	public void sendMessage(Message msg) {
		try {
			int act = 2;
			os.writeInt(act);
			os.flush();
			os.writeInt(msg.getSender().length());
			os.flush();
			os.writeBytes(msg.getSender());
			os.flush();
			os.writeInt(msg.getReceiver().length());
			os.flush();
			os.writeBytes(msg.getReceiver());
			os.flush();
			os.writeInt(msg.getMessage().length());
			os.flush();
			os.writeBytes(msg.getMessage());

			int response;
			response = is.readInt();

			
			System.out.println("result for update: " + response);
		} catch (Exception e) {
						System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public ArrayList<User> getUsers() {
		try {
			System.out.println("Requesting user list");
			ArrayList<User> users = new ArrayList<User>();

			int act = 3;
			os.writeInt(act);
			os.flush();


			int n = is.readInt();
			System.out.println("Reading list, nr of users: " + n);
			for (int i = 0; i < n; i++) {
				byte[] name = new byte[1024];
				byte[] email = new byte[1024];
				byte[] phone = new byte[1024];

				int len = is.readInt();
				is.readFully(name, 0, len);
				String sName = new String(name, "UTF-8");
				sName = sName.substring(0, len);


				len = is.readInt();
				is.readFully(email, 0, len);
				String sEmail = new String(email, "UTF-8");
				sEmail = sEmail.substring(0, len);


				len = is.readInt();
				is.readFully(phone, 0, len);
				String sPhone = new String(phone, "UTF-8");
				sPhone = sPhone.substring(0, len);


				User user = new User();
				user.setName(sName);
				user.setEmail(sEmail);
				user.setPhone(sPhone);

				users.add(user);
				System.out.println("Finished reading user: " + user.toString());
			}

			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	//returns the received message from server
	public Message receiveMessage() {
		Message msg = new Message();

		try {
			System.out.println("Client:Performing receive message");
			byte[] sender = new byte[255];
			byte[] receiver = new byte[255];
			byte[] message = new byte[255];

			int len = is.readInt();
			is.readFully(sender, 0, len);
			String sSender = new String(sender, "UTF-8");
			sSender = sSender.substring(0, len);
			
			len = is.readInt();
			is.readFully(receiver, 0, len);
			String sReceiver = new String(receiver, "UTF-8");
			sReceiver = sReceiver.substring(0, len);
			
			len = is.readInt();
			is.readFully(message, 0, len);
			String sMessage = new String(message, "UTF-8");
			sMessage = sMessage.substring(0, len);
			
			msg.setSender(sSender);
			msg.setReceiver(sReceiver);
			msg.setMessage(sMessage);
			
			System.out.println("Client:Message recive: "+msg.messageInfo());
			
		} catch (IOException e) {
			System.out.println(":Error at reading action from client");
			e.printStackTrace();
		}
		return	msg;		

	}

}
