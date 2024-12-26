package ua.com.pohribnyi.jdbcpractise.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ua.com.pohribnyi.jdbcpractise.exception.JDBCRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.PostNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.repository.PostRepository;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;
import ua.com.pohribnyi.jdbcpractise.util.mapper.LabelMapper;
import ua.com.pohribnyi.jdbcpractise.util.mapper.PostMapper;

public class JDBCPostRepositoryImpl implements PostRepository {

	private static final String GET_BY_ID_SQL = "SELECT P.*,"
			+ "LP.label_id, L.name, W.first_name, W.last_name "
			+ "FROM post AS P " 
			+ "LEFT JOIN writter AS W ON P.writter_id = W.id "
			+ "LEFT JOIN label_post AS LP ON P.id = LP.post_id "
			+ "LEFT JOIN label AS L ON LP.label_id = L.id "
			+ "WHERE P.id = ?";
	private static final String GET_ALL_SQL = "SELECT P.*,"
			+ "LP.label_id, L.name, W.first_name, W.last_name "
			+ "FROM post AS P " 
			+ "LEFT JOIN writter AS W ON P.writter_id = W.id "
			+ "LEFT JOIN label_post AS LP ON P.id = LP.post_id "
			+ "LEFT JOIN label AS L ON LP.label_id = L.id ";
	private static final String SAVE_SQL = "INSERT INTO post (content, writter_id, status) VALUES (?, ?, ?)";
	private static final String UPDATE_BY_ID_SQL = 
			"UPDATE post SET content = ?, updated_at = ?, writter_id = ?, status = ? WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "UPDATE post SET status = ? WHERE id = ?";
	private static final String DELETE_POST_LABELS_BY_ID_SQL = "DELETE FROM label_post WHERE post_id = ?";
	private static final String INSERT_LABEL_POST_SQL = "INSERT INTO label_post VALUES(?, ?)";
	private static final String GET_POST_LABELS_SQL = "SELECT * FROM label_post WHERE post_id = ?";

	@Override
	public Post getById(Long id) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_BY_ID_SQL)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return PostMapper.mapResultSetToPost(resultSet);
			} else {
				throw new PostNotFoundException("Post not found by id: " + id);
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}

	}

	@Override
	public List<Post> getAll() {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_SQL)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			return PostMapper.mapResultSetToListOfPosts(resultSet);
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public Post save(Post p) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(SAVE_SQL)) {
			preparedStatement.setString(1, p.getContent());
			preparedStatement.setLong(2, p.getWritter().getId());
			preparedStatement.setInt(3, p.getStatus().ordinal());
			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				Post newPost = PostMapper.mapResultSetToPostWithoutLabels(resultSet);
				newPost.setLabels(savePostLabels(newPost.getId(), p.getLabels()));
				return newPost;
			} else {
				throw new JDBCRepoException("Save post failed, row wasn`t inserted");
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public Post update(Post p) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(UPDATE_BY_ID_SQL)) {
			preparedStatement.setString(1, p.getContent());
			preparedStatement.setTimestamp(2, new Timestamp(p.getUpdatedAt().getTime()));
			preparedStatement.setLong(3, p.getWritter().getId());
			preparedStatement.setInt(4, p.getStatus().ordinal());
			preparedStatement.setLong(5, p.getId());
			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				Post updatedPost = PostMapper.mapResultSetToPostWithoutLabels(resultSet);
				deletePostLabelsByPostId(updatedPost.getId());
				updatedPost.setLabels(savePostLabels(updatedPost.getId(), p.getLabels()));
				return updatedPost;
			} else {
				throw new JDBCRepoException("Update post failed, row wasn`t changed by id: " + p.getId());
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_BY_ID_SQL)) {
			preparedStatement.setInt(1, PostStatus.DELETED.ordinal());
			preparedStatement.setLong(2, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new JDBCRepoException("Delete post failed, row wasn`t deleted by id:" + id);
			}
			deletePostLabelsByPostId(id);
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}

	}

	private void deletePostLabelsByPostId(Long id) {
		if (!postHasLabels(id))
			return;
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_POST_LABELS_BY_ID_SQL)) {
			preparedStatement.setLong(1, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new JDBCRepoException("Delete post labels failed, row wasn`t deleted by post id:" + id);
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	private List<Label> savePostLabels(Long postId, List<Label> labels) {
		if (labels == null || labels.size() == 0) {
			return null;
		}
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(INSERT_LABEL_POST_SQL)) {
			List<Label> savedLabels = new ArrayList<>();
			for (Label curLabel : labels) {
				preparedStatement.setLong(1, curLabel.getId());
				preparedStatement.setLong(2, postId);
				if (preparedStatement.executeUpdate() == 0)
					throw new JDBCRepoException("Save post labels failed, row wasn`t inserted");
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				Label savedLabel = LabelMapper.mapLabelFromLabelPostResultSet(resultSet);
				savedLabel.setName(curLabel.getName());
				savedLabels.add(savedLabel);
			}
			return savedLabels;
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}
	
	private boolean postHasLabels(Long postId) {
		boolean postHasLabels = false;
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_POST_LABELS_SQL)) {
			preparedStatement.setLong(1, postId);
			ResultSet resultSet = preparedStatement.executeQuery();
			postHasLabels = resultSet.next();
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
		return postHasLabels;
	}

}
