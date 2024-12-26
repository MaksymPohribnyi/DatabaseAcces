package ua.com.pohribnyi.jdbcpractise.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import ua.com.pohribnyi.jdbcpractise.ApplicationContext;
import ua.com.pohribnyi.jdbcpractise.controller.LabelController;
import ua.com.pohribnyi.jdbcpractise.controller.PostController;
import ua.com.pohribnyi.jdbcpractise.controller.WritterController;
import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

public class PostView {

	private final PostController postController;
	private final WritterController writterController;
	private final LabelController labelController;

	private final Scanner scanner = ApplicationContext.getScanner();

	public PostView(PostController postController, WritterController writterController,
			LabelController labelController) {
		this.postController = postController;
		this.writterController = writterController;
		this.labelController = labelController;
	}

	/**
	 * 
	 */
	public void show() {

		boolean isRunning = true;

		while (isRunning) {

			System.out.println("#------------------------------------------#");
			System.out.println("/___POST VIEW___/");
			System.out.println("HOW TO WORK WITH IT:");
			System.out.println("1. Create post");
			System.out.println("2. Update post");
			System.out.println("3. Delete post");
			System.out.println("4. Get all posts");
			System.out.println("5. Get post by id");
			System.out.println("0. <- Main menu");
			System.out.println("#------------------------------------------#");

			int choose = scanner.nextInt();
			scanner.nextLine();

			switch (choose) {
			case 1:
				createPost();
				break;
			case 2:
				updatePost();
				break;
			case 3:
				deletePost();
				break;
			case 4:
				getAllPosts();
				break;
			case 5:
				getPostById();
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

	private void createPost() {
		System.out.println("Enter content name for new post:");
		String content = scanner.nextLine();
		System.out.println("Now choose an id from writter list:");
		List<Writter> writters = showAllWritters();
		Long choosedId = scanner.nextLong();
		Writter writter = writters.stream().filter(w -> w.getId() == choosedId).findFirst().orElse(null);
		if (writter != null) {
			System.out.println("Now choose one id from label list, or several spliting by ',':");
			List<Label> allLabels = showAllLabels();
			scanner.nextLine();
			String idLine = scanner.nextLine();
			List<String> labelIds = new ArrayList<>(Arrays.asList(idLine.split(",")));
			Set<Long> setOfLabelIds = labelIds.stream().map(Long::parseLong).collect(Collectors.toSet());
			List<Label> choosedLabels = allLabels.stream().filter(l -> setOfLabelIds.contains(l.getId()))
					.collect(Collectors.toList());
			Post createdPost = postController.createPost(content, writter, choosedLabels);
			System.out.println("Post created: " + createdPost);
		} else {
			System.out.println("Incorrect writter id. Try again");
		}
	}

	private void updatePost() {
		System.out.println("Enter existing id: ");
		long id = scanner.nextLong();
		Post postToUpd = postController.getPostById(id);
		if (postToUpd != null) {
			scanner.nextLine();
			System.out.println("Enter new content name to update post:");
			String updContent = scanner.nextLine();
			System.out.println("Now choose an id from writter list to switch writter:");
			List<Writter> AllWitters = showAllWritters();
			Long choosedWritterId = scanner.nextLong();
			Writter updWritter = AllWitters.stream().filter(w -> w.getId() == choosedWritterId).findFirst()
					.orElse(null);
			if (updWritter != null) {
				List<Label> allLabels = showAllLabels();
				scanner.nextLine();
				String idLine = scanner.nextLine();
				List<String> labelIds = new ArrayList<>(Arrays.asList(idLine.split(",")));
				Set<Long> setOfLabelIds = labelIds.stream().map(Long::parseLong).collect(Collectors.toSet());
				List<Label> choosedLabels = allLabels.stream().filter(l -> setOfLabelIds.contains(l.getId()))
						.collect(Collectors.toList());
				System.out.println("Choose new status for update post:");
				System.out.println(PostStatus.ACTIVE + " - enter: '" + PostStatus.ACTIVE.ordinal() + "' ");
				System.out.println(PostStatus.UNDER_REVIEW + " - enter: '" + PostStatus.UNDER_REVIEW.ordinal() + "' ");
				System.out.println(PostStatus.DELETED + " - enter: '" + PostStatus.DELETED.ordinal() + "' ");
				int updStatus = scanner.nextInt();
				try {
					PostStatus choosedStatus = PostStatus.values()[updStatus];
					Post updatedPost = postController.updatePost(postToUpd.getId(), updContent, updWritter,
							choosedLabels, choosedStatus);
					System.out.println("Post updated: " + updatedPost);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Incorrect status id. Try again");
				}
			} else {
				System.out.println("Incorrect writter id. Try again");
			}
		}
	}

	private void getPostById() {
		System.out.println("Enter existing id: ");
		long id = scanner.nextLong();
		Post receivedPost = postController.getPostById(id);
		System.out.println("Post received: " + receivedPost);
	}

	private void getAllPosts() {
		List<Post> posts = postController.getAllPosts();
		System.out.println(posts);
	}

	private void deletePost() {
		System.out.println("Enter existing id: ");
		long id = scanner.nextLong();
		postController.deletePostById(id);
		System.out.println("Post deleted");
	}

	private List<Label> showAllLabels() {
		System.out.printf("---------------------------%n");
		System.out.printf("| %-5s | %-15s |%n", "ID", "name");
		System.out.printf("---------------------------%n");
		List<Label> allLabels = labelController.getAllLabels();
		for (Label label : allLabels) {
			System.out.printf("| %-5s ", label.getId());
			System.out.printf("| %-15s |", label.getName());
			System.out.println();
		}
		return allLabels;
	}

	private List<Writter> showAllWritters() {
		System.out.printf("----------------------------------------%n");
		System.out.printf("| %-5s | %-10s | %-15s |%n", "ID", "first_name", "last_name");
		System.out.printf("----------------------------------------%n");
		List<Writter> writters = writterController.getAllWritters();
		for (Writter writter : writters) {
			System.out.printf("| %-5s ", writter.getId());
			System.out.printf("| %-10s ", writter.getFirstName());
			System.out.printf("| %-15s |", writter.getLastName());
			System.out.println();
		}
		return writters;
	}

}
