package ua.com.pohribnyi.jdbcpractise.model;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Writter {

	private Long id;
	private String firstName;
	private String lastName;
	private List<Post> posts;

	@Override
	public String toString() {
		return "Writter [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", posts=" + posts + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Writter other = (Writter) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(posts, other.posts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, posts);
	}

}
