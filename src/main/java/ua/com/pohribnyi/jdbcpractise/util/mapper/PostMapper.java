package ua.com.pohribnyi.jdbcpractise.util.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.com.pohribnyi.jdbcpractise.model.Label;
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
				.writter(Writter.builder()
						.id(rs.getLong("writter_id"))
						.build())
				.status(PostStatus.values()[rs.getInt("status")])
				.labels(getLabelsFromPostResultSet(rs))
				.build();
	}
	
	public static Post mapResultSetToPostWithoutLabels(ResultSet rs) throws SQLException{
		return Post.builder()
				.id(rs.getLong("id"))
				.content(rs.getString("content"))
				.createdAt(rs.getDate("created_at"))
				.updatedAt(rs.getDate("updated_at"))
				.writter(Writter.builder()
						.id(rs.getLong("writter_id"))
						.build())
				.status(PostStatus.values()[rs.getInt("status")])
				.build();
	}
	
	public static List<Post> mapResultSetToListOfPosts(ResultSet rs) throws SQLException{
		Map<Post, List<Label>> postLabels = new HashMap<>();
		while (rs.next()) {
			Post currentPost = PostMapper.mapResultSetToPostWithoutLabels(rs);
			Label currentLabel = LabelMapper.mapLabelFromPostResultSet(rs);
			List<Label> labels = postLabels.get(currentPost);
			if (labels == null) {
				labels = new ArrayList<>(Arrays.asList(currentLabel));
				postLabels.put(currentPost, labels);
			} else {
				labels.add(currentLabel);
			}
		}
		return matchLabelsToPostFromHashMap(postLabels);
	}

	private static List<Label> getLabelsFromPostResultSet(ResultSet rs) throws SQLException {
		List<Label> labels = new ArrayList<>();
		// first call is needed to process the current line of ResultSet
		Label currentRowLabel = LabelMapper.mapLabelFromPostResultSet(rs);
		if (currentRowLabel != null)
			labels.add(currentRowLabel);
		while (rs.next()) {
			labels.add(LabelMapper.mapLabelFromPostResultSet(rs));
		}
		return labels;
	}

	private static List<Post> matchLabelsToPostFromHashMap(Map<Post, List<Label>> postLabels) {
		List<Post> matchedPosts = new ArrayList<>();
		for (Map.Entry<Post, List<Label>> mapEntry : postLabels.entrySet()) {
			Post p = mapEntry.getKey();
			p.setLabels(mapEntry.getValue());
			matchedPosts.add(p);
		}
		return matchedPosts;
	}
	
}
