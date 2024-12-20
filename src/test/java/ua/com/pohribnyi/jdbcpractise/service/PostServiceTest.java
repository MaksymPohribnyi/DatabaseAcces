package ua.com.pohribnyi.jdbcpractise.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.pohribnyi.jdbcpractise.exception.PostNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.repository.PostRepository;
import ua.com.pohribnyi.jdbcpractise.utils.PostDataUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	@Test
	@DisplayName("Test of get Post by id functionality")
	public void given_PostId_whenGetPostById_thenPostIsReturned() {
		// given
		BDDMockito.given(postRepository.getById(anyLong())).willReturn(PostDataUtils.getPostPersisted());
		// when
		Post obtainedPost = postService.getById(5L);
		// then
		assertThat(obtainedPost).isNotNull();
	}

	@Test
	@DisplayName("Test of get Post by bad id functionality")
	public void given_BadPostId_whenGetPostById_thenExceptionIsThrown() {
		// given
		BDDMockito.given(postRepository.getById(anyLong())).willThrow(PostNotFoundException.class);
		// when
		assertThrows(PostNotFoundException.class, () -> postService.getById(5L));
		// then
	}

	@Test
	@DisplayName("Test of get all Posts functionality")
	public void given_ThreePosts_whenGetAllPosts_thenRepoMethodIsCalled() {
		// given
		Post firstPost = PostDataUtils.getPostTransient();
		Post secondPost = PostDataUtils.getPostTransient();
		Post thirdPost = PostDataUtils.getPostTransient();
		BDDMockito.given(postRepository.getAll()).willReturn(List.of(firstPost, secondPost, thirdPost));
		// when
		List<Post> obtainedPosts = postService.getAll();
		// then
		assertThat(obtainedPosts).isNotEmpty();
		assertThat(obtainedPosts.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("Test of save Post functionality")
	public void given_PostToSave_whenSavePost_thenRepoMethodIsCalled() {
		// given
		Post PostToSave = PostDataUtils.getPostTransient();
		BDDMockito.given(postRepository.save(any(Post.class))).willReturn(PostDataUtils.getPostPersisted());
		// when
		Post savedPost = postService.save(PostToSave);
		// then
		assertThat(savedPost).isNotNull();
		verify(postRepository, atLeastOnce()).save(any(Post.class));
	}

	@Test
	@DisplayName("Test of update Post functionality")
	public void given_PostToUpdate_whenUpdatePost_thenRepoMethodIsCalled() {
		// given
		Post PostToUpdate = PostDataUtils.getPostTransient();
		BDDMockito.given(postRepository.update(any(Post.class))).willReturn(PostToUpdate);
		// when
		Post savedPost = postService.update(PostToUpdate);
		// then
		assertThat(savedPost).isNotNull();
		verify(postRepository, atLeastOnce()).update(any(Post.class));
	}

	@Test
	@DisplayName("Test of delete by Post id functionality")
	public void given_PostIdToDelete_whenDeleteById_thenRepoMethodIsCalled() {
		// given
		// when
		postService.deleteById(1L);
		// then
		verify(postRepository, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	@DisplayName("Test of delete by bad Post id functionality")
	public void given_BadPostIdToDelete_whenDeleteById_thenExceptionIsThrown() {
		// given
		BDDMockito.doThrow(PostNotFoundException.class).when(postRepository).deleteById(anyLong());
		// when
		assertThrows(PostNotFoundException.class, () -> postService.deleteById(5L));
		// then
	}

}
