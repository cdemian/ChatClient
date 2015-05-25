package app;

import java.awt.EventQueue;

import view.LoginView;

public class Client {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
