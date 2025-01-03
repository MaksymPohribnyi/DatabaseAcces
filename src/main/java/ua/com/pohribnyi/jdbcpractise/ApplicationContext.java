package ua.com.pohribnyi.jdbcpractise;

import java.util.Scanner;

import ua.com.pohribnyi.jdbcpractise.controller.LabelController;
import ua.com.pohribnyi.jdbcpractise.controller.PostController;
import ua.com.pohribnyi.jdbcpractise.controller.WritterController;
import ua.com.pohribnyi.jdbcpractise.repository.LabelRepository;
import ua.com.pohribnyi.jdbcpractise.repository.PostRepository;
import ua.com.pohribnyi.jdbcpractise.repository.WritterRepository;
import ua.com.pohribnyi.jdbcpractise.repository.hibernate.HibernateLabelRepositoryImpl;
import ua.com.pohribnyi.jdbcpractise.repository.hibernate.JDBCPostRepositoryImpl;
import ua.com.pohribnyi.jdbcpractise.repository.hibernate.JDBCWritterRepositoryImpl;
import ua.com.pohribnyi.jdbcpractise.service.LabelService;
import ua.com.pohribnyi.jdbcpractise.service.PostService;
import ua.com.pohribnyi.jdbcpractise.service.WritterService;
import ua.com.pohribnyi.jdbcpractise.view.LabelView;
import ua.com.pohribnyi.jdbcpractise.view.PostView;
import ua.com.pohribnyi.jdbcpractise.view.WritterView;

public class ApplicationContext {

	private final LabelRepository labelRepository = new HibernateLabelRepositoryImpl();
	private final LabelService labelService = new LabelService(labelRepository);
	private final LabelController labelController = new LabelController(labelService);
	private final LabelView labelView = new LabelView(labelController);

	private final WritterRepository writterRepository = new JDBCWritterRepositoryImpl();
	private final WritterService writterService = new WritterService(writterRepository);
	private final WritterController writterController = new WritterController(writterService);
	private final WritterView writterView = new WritterView(writterController);

	private final PostRepository postRepository = new JDBCPostRepositoryImpl();
	private final PostService postService = new PostService(postRepository);
	private final PostController postController = new PostController(postService);
	private final PostView postView = new PostView(postController, writterController, labelController);

	private static final Scanner SCANNER = new Scanner(System.in);

	public static Scanner getScanner() {
		return SCANNER;
	}

	public LabelView getLabelView() {
		return labelView;
	}
	public WritterView getWritterlView() {
		return writterView;
	}
	public PostView getPostView() {
		return postView;
	}
	
}
