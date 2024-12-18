package ua.com.pohribnyi.jdbcpractise.controller;

import java.sql.Timestamp;
import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.service.PostService;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	public Post getPostById(Long id) {
		return postService.getById(id);
	}

	public List<Post> getAllPosts() {
		return postService.getAll();
	}
	
	public Post createPost(String content, Writter writter, List<Label> labels) {
		return postService.save(
				Post.builder()
				.content(content)
				.writter(writter)
				.labels(labels)
				.status(PostStatus.UNDER_REVIEW)
				.build());
	}
	
	public Post updatePost(Long postId, String content, Writter writter, List<Label> labels, PostStatus status) {
		return postService.update(
				Post.builder()
				.id(postId)
				.content(content)
				.updatedAt(new Timestamp(System.currentTimeMillis()))
				.writter(writter)
				.labels(labels)
				.status(status)
				.build());
	}

	public void deletePostById(Long id) {
		postService.deleteById(id);
	}

	
}
