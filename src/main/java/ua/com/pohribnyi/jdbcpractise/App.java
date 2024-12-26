package ua.com.pohribnyi.jdbcpractise;

import ua.com.pohribnyi.jdbcpractise.view.MainView;

public class App {

	public static void main(String[] args) {
		ApplicationContext appContext = new ApplicationContext();

		MainView mainView = new MainView(appContext);
		mainView.show();
	}
}
