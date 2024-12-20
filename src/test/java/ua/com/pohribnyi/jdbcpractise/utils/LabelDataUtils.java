package ua.com.pohribnyi.jdbcpractise.utils;

import ua.com.pohribnyi.jdbcpractise.model.Label;

public class LabelDataUtils {

	public static Label getLabelTransient() {
		return Label.builder()
				.name("test label")
				.build();
	}
	public static Label getLabelPersisted() {
		return Label.builder()
				.id(1L)
				.name("test label")
				.build();
	}
	
}
