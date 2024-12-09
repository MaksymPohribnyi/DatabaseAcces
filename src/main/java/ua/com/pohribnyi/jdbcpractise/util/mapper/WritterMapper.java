package ua.com.pohribnyi.jdbcpractise.util.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;

public class WritterMapper {

	public static Writter mapResultSetToWritter(ResultSet rs) throws SQLException{
		return Writter.builder()
				.id(rs.getLong("id"))
				.firstName(rs.getString("first_name"))
				.lastName(rs.getString("last_name"))
				.build();
	}

	public static Writter mapResultSetWithPostsToWritter(ResultSet rs, List<Post> posts) throws SQLException{
		return Writter.builder()
				.id(rs.getLong("id"))
				.firstName(rs.getString("first_name"))
				.lastName(rs.getString("last_name"))
				.posts(posts)
				.build();
	}
}