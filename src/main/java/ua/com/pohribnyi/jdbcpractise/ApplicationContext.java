package ua.com.pohribnyi.jdbcpractise;

import java.sql.SQLException;
import java.util.Scanner;

import ua.com.pohribnyi.jdbcpractise.controller.LabelController;
import ua.com.pohribnyi.jdbcpractise.repository.LabelRepository;
import ua.com.pohribnyi.jdbcpractise.repository.impl.JDBCLabelRepositoryImpl;
import ua.com.pohribnyi.jdbcpractise.service.LabelService;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;
import ua.com.pohribnyi.jdbcpractise.view.LabelView;

public class ApplicationContext {

	private final LabelRepository labelRepository = new JDBCLabelRepositoryImpl();
	private final LabelService labelService = new LabelService(labelRepository);
	private final LabelController labelController = new LabelController(labelService);
	private final LabelView labelView = new LabelView(labelController);
	private static final Scanner SCANNER = new Scanner(System.in);

	public static Scanner getScanner() {
		return SCANNER;
	}

	public void start() {

		boolean isRunning = true;

		while (isRunning) {
			System.out.println("#------------------------------------------#");
			System.out.println("/___MAIN MENU___/");
			System.out.println("CHOOSE THE ENTITY YOU WOULD TO WORK WITH:");
			System.out.println("1. -> Label");
			System.out.println("2. -> Post");
			System.out.println("3. -> Writter");
			System.out.println("4. EXIT()");
			System.out.println("#------------------------------------------#");

			int choose = SCANNER.nextInt();
			switch (choose) {
			case 1 -> labelView.show();
			case 4 -> {
				isRunning = false;
				try {
					DBUtils.getConnection().close();
					System.out.println("GOOD BYE :)");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			default -> System.out.println("CHOOSED INCORRECT OPTION!");
			}
		}
	}

}
