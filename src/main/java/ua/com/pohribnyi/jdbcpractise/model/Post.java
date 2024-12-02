package ua.com.pohribnyi.jdbcpractise.model;

import java.util.Date;
import java.util.List;

public class Post {

	private Integer id;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	private List<Label> labels;

}
