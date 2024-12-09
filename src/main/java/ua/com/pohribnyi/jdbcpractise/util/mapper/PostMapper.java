package ua.com.pohribnyi.jdbcpractise.util.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

public class PostMapper {

	public static Post mapResultSetToPost(ResultSet rs) throws SQLException{
		return Post.builder()
				.id(rs.getLong("id"))
				.content(rs.getString("content"))
				.createdAt(rs.getDate("created_at"))
				.updatedAt(rs.getDate("updated_at"))
				.writter(Writter.builder().id(rs.getLong("writter_id")).build())
				.status(PostStatus.values()[rs.getInt("status")])
				.build();
	}
	
}
