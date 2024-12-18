package ua.com.pohribnyi.jdbcpractise.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

	@Override
	public String toString() {
		return "Post [id=" + id + ", content=" + content + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", labels={" + labels + "}, status=" + status + ", writter=" + writter + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && Objects.equals(labels, other.labels) && status == other.status
				&& Objects.equals(updatedAt, other.updatedAt) && Objects.equals(writter, other.writter);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createdAt, id, labels, status, updatedAt, writter);
	}

}
