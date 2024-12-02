package ua.com.pohribnyi.jdbcpractise.service;

import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.repository.PostRepository;

public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post getById(Long id) {
		return postRepository.getById(id);
	}

	public List<Post> getALl() {
		return postRepository.getAll();
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public Post update(Post post) {
		return postRepository.update(post);
	}

	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}

}
