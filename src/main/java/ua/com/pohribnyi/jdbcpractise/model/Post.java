package ua.com.pohribnyi.jdbcpractise.model;

import java.util.Date;
import java.util.List;

import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

public class Post {

	private Integer id;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	private List<Label> labels;
	private PostStatus status;

	public Post() {
	};

	public Post(Integer id, String content, Date createdAt, Date updatedAt, List<Label> labels, PostStatus status) {
		super();
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.labels = labels;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public PostStatus getStatus() {
		return status;
	}

	public void setStatus(PostStatus status) {
		this.status = status;
	}

}
