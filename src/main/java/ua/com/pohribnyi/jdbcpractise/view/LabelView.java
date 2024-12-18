package ua.com.pohribnyi.jdbcpractise.view;

import java.util.List;
import java.util.Scanner;

import ua.com.pohribnyi.jdbcpractise.ApplicationContext;
import ua.com.pohribnyi.jdbcpractise.controller.LabelController;
import ua.com.pohribnyi.jdbcpractise.model.Label;

public class LabelView {

	private final LabelController labelController;

	public LabelView(LabelController controller) {
		this.labelController = controller;
	}

	public void show() {

		boolean isRunning = true;
		Scanner scanner = ApplicationContext.getScanner();
		long id;

		while (isRunning) {

			System.out.println("#------------------------------------------#");
			System.out.println("/___LABEL VIEW___/");
			System.out.println("HOW TO WORK WITH IT:");
			System.out.println("1. Create label");
			System.out.println("2. Update label");
			System.out.println("3. Delete label");
			System.out.println("4. Get all labels");
			System.out.println("5. Get label by id");
			System.out.println("0. <- Main menu");
			System.out.println("#------------------------------------------#");

			int choose = scanner.nextInt();
			scanner.nextLine();

			switch (choose) {
			case 1:
				System.out.println("Enter the name for new label:");
				String name = scanner.nextLine();
				Label createdLabel = labelController.createLabel(name);
				System.out.println("Label created: " + createdLabel);
				break;
			case 2:
				System.out.println("Enter the label id for update:");
				id = scanner.nextLong();
				scanner.nextLine();
				System.out.println("Enter new name for label:");
				String newName = scanner.nextLine();
				Label updatedLabel = labelController.updateLabel(id, newName);
				System.out.println("Label updated: " + updatedLabel);
				break;
			case 3:
				System.out.println("Enter existing id: ");
				id = scanner.nextLong();
				labelController.deleteLabelById(id);
				System.out.println("Label deleted");
				break;
			case 4:
				List<Label> labels = labelController.getAllLabels();
				System.out.println(labels);
				break;
			case 5:
				System.out.println("Enter existing id: ");
				id = scanner.nextLong();
				Label receivedLabel = labelController.getLabelById(id);
				System.out.println("Label received: " + receivedLabel);
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
