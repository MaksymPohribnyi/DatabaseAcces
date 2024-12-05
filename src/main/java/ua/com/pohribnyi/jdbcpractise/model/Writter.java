package ua.com.pohribnyi.jdbcpractise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Writter {

	private Integer id;
	private String firstName;
	private String lastName;
	private List<Post> posts;

}
