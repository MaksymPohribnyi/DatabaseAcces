package ua.com.pohribnyi.jdbcpractise.view;

import java.util.List;
import java.util.Scanner;

import ua.com.pohribnyi.jdbcpractise.ApplicationContext;
import ua.com.pohribnyi.jdbcpractise.controller.WritterController;
import ua.com.pohribnyi.jdbcpractise.model.Writter;

public class WritterView {

	private final WritterController writterController;
	private final Scanner scanner = ApplicationContext.getScanner();

	public WritterView(WritterController writterController) {
		this.writterController = writterController;
	}

	public void show() {

		boolean isRunning = true;

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
				createWritter();
				break;
			case 2:
				updateWritter();
				break;
			case 3:
				deleteWritter();
				break;
			case 4:
				getAllWritters();
				break;
			case 5:
				getWritterById();
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

	private void createWritter() {
		System.out.println("Enter the first name for new Writter:");
		String firstName = scanner.nextLine();
		System.out.println("Enter the last name for new Writter:");
		String lastName = scanner.nextLine();
		Writter createdWritter = writterController.createWritter(firstName, lastName);
		System.out.println("Writter created: " + createdWritter);
	}

	private void updateWritter() {
		System.out.println("Enter the writter id for update:");
		long id = scanner.nextLong();
		System.out.println("Enter new first name for writter:");
		scanner.nextLine();
		String newFirstName = scanner.nextLine();
		System.out.println("Enter new last name for writter:");
		String newLastName = scanner.nextLine();
		Writter updateWritter = writterController.updateWritter(id, newFirstName, newLastName);
		System.out.println("Writter updated: " + updateWritter);
	}

	private void deleteWritter() {
		System.out.println("Enter existing id: ");
		long id = scanner.nextLong();
		writterController.deleteWritterById(id);
		System.out.println("Writter deleted");
	}

	private void getAllWritters() {
		List<Writter> writters = writterController.getAllWritters();
		System.out.println(writters);
	}

	private void getWritterById() {
		System.out.println("Enter existing id: ");
		long id = scanner.nextLong();
		Writter receivedWritter = writterController.getWritterById(id);
		System.out.println("Writter received: " + receivedWritter);
	}

}
