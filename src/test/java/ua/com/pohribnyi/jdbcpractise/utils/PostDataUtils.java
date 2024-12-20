package ua.com.pohribnyi.jdbcpractise.utils;

import java.util.ArrayList;
import java.util.Date;

import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

public class PostDataUtils {

	public static Post getPostTransient() {
		return Post.builder()
				.content("test content")
				.createdAt(new Date())
				.updatedAt(new Date())
				.status(PostStatus.UNDER_REVIEW)
				.labels(new ArrayList<>())
				.writter(new Writter())
				.build();
	}
	public static Post getPostPersisted() {
		return Post.builder()
				.id(1L)
				.content("test content")
				.createdAt(new Date())
				.updatedAt(new Date())
				.status(PostStatus.UNDER_REVIEW)
				.labels(new ArrayList<>())
				.writter(new Writter())
				.build();
	}
	
	
}
