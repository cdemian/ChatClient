package controller;

import java.util.ArrayList;
import java.util.List;

import networking.ClientService;

import org.omg.CORBA.RepositoryIdHelper;

import model.Message;
import model.User;

public class Controller {

	private static Controller instance;
	public Message message;
	
	public Controller(){
		
	}
	public static Controller getInstance(){
		if(instance == null){
			instance = new Controller();
		}
		return instance;
	}
	

	public void addUser(User user){
		boolean ok = ClientService.getInstance().login(user.getName(), user.getEmail(), user.getPhone());
		if(ok==true)
			System.out.println("User added:"+user);
		else
			System.out.println("User not added.");
	}
	public void sendMessage(Message message){
		ClientService.getInstance().sendMessage(message);

		System.out.println("message sent:"+message.messageInfo());
	}
	public void logout(User user){
		ClientService.getInstance().logout(user);
		System.out.println(user.getName()+" has left the chat!");
	}
	public ArrayList<User> getUsers(){
		return ClientService.getInstance().getUsers();
	}

}
