package ua.com.pohribnyi.jdbcpractise.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

	private Long id;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	private List<Label> labels;
	private PostStatus status;
	private Writter writter;

}
