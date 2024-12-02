package ua.com.pohribnyi.jdbcpractise.model;

import java.util.List;

public class Writter {

	private Integer id;
	private String firstName;
	private String lastName;
	private List<Post> posts;

	public Writter() {
	}

	public Writter(Integer id, String firstName, String lastName, List<Post> posts) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.posts = posts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
