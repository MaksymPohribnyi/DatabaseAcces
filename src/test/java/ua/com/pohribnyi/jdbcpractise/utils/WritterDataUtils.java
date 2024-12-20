package ua.com.pohribnyi.jdbcpractise.utils;

import java.util.ArrayList;

import ua.com.pohribnyi.jdbcpractise.model.Writter;

public class WritterDataUtils {

	public static Writter getWritterTransient() {
		return Writter.builder()
				.firstName("my")
				.lastName("favorite")
				.posts(new ArrayList<>())
				.build();
	}
	public static Writter getWritterPersisted() {
		return Writter.builder()
				.id(1L)
				.firstName("my")
				.lastName("favorite")
				.posts(new ArrayList<>())
				.build();
	}
	
}
