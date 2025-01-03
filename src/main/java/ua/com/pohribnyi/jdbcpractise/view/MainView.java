package ua.com.pohribnyi.jdbcpractise.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import ua.com.pohribnyi.jdbcpractise.ApplicationContext;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;

public class MainView {

	private final ApplicationContext applicationContext;

	public MainView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void show() {

		Scanner scanner = ApplicationContext.getScanner();

		boolean isRunning = true;

		while (isRunning) {
			System.out.println("#------------------------------------------#");
			System.out.println("/___MAIN MENU___/");
			System.out.println("CHOOSE THE ENTITY YOU WOULD TO WORK WITH:");
			System.out.println("1. -> Label");
			System.out.println("2. -> Writter");
			System.out.println("3. -> Post");
			System.out.println("0. EXIT()");
			System.out.println("#------------------------------------------#");

			int choose = scanner.nextInt();
			switch (choose) {
			case 1 -> applicationContext.getLabelView().show();
			case 2 -> applicationContext.getWritterlView().show();
			case 3 -> applicationContext.getPostView().show();
			case 0 -> {
				isRunning = false;
				System.out.println("GOOD BYE :)");
			}
			default -> System.out.println("CHOOSED INCORRECT OPTION!");
			}
		}
	}

}
