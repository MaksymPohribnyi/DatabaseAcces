package ua.com.pohribnyi.jdbcpractise.view;

import java.util.List;
import java.util.Scanner;

import ua.com.pohribnyi.jdbcpractise.ApplicationContext;
import ua.com.pohribnyi.jdbcpractise.controller.WritterController;
import ua.com.pohribnyi.jdbcpractise.model.Writter;

public class WritterView {

	private final WritterController writterController;

	public WritterView(WritterController writterController) {
		this.writterController = writterController;
	}

	public void show() {

		boolean isRunning = true;
		Scanner scanner = ApplicationContext.getScanner();
		long id;

		while (isRunning) {

			System.out.println("#------------------------------------------#");
			System.out.println("/___WRITTER VIEW___/");
			System.out.println("HOW TO WORK WITH IT:");
			System.out.println("1. Create writter");
			System.out.println("2. Update writter");
			System.out.println("3. Delete writter");
			System.out.println("4. Get all writters");
			System.out.println("5. Get writter by id");
			System.out.println("0. <- Main menu");
			System.out.println("#------------------------------------------#");

			int choose = scanner.nextInt();
			scanner.nextLine();

			switch (choose) {
			case 1:
				System.out.println("Enter the first name for new Writter:");
				String firstName = scanner.nextLine();
				System.out.println("Enter the last name for new Writter:");
				scanner.nextLine();
				String lastName = scanner.nextLine();
				Writter createdWritter = writterController.createWritter(firstName, lastName);
				System.out.println("Writter created: " + createdWritter);
				break;
			case 2:
				System.out.println("Enter the writter id for update:");
				id = scanner.nextLong();
				System.out.println("Enter new first name for writter:");
				scanner.nextLine();
				String newFirstName = scanner.nextLine();
				System.out.println("Enter new last name for writter:");
				String newLastName = scanner.nextLine();
				Writter updateWritter = writterController.updateWritter(id, newFirstName, newLastName);
				System.out.println("Writter updated: " + updateWritter);
				break;
			case 3:
				System.out.println("Enter existing id: ");
				id = scanner.nextLong();
				writterController.deleteWritterById(id);
				System.out.println("Writter deleted");
				break;
			case 4:
				List<Writter> writters = writterController.getAllWritters();
				System.out.println(writters);
				break;
			case 5:
				System.out.println("Enter existing id: ");
				id = scanner.nextLong();
				Writter receivedWritter = writterController.getWritterById(id);
				System.out.println("Writter received: " + receivedWritter);
				break;
			case 0:
				isRunning = false;
				break;
			default:
				System.out.println("CHOOSED INCORRECT OPTION!");
				break;
			}
		}
	}

}
