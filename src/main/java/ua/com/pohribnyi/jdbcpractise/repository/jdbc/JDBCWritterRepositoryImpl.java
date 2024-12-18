package ua.com.pohribnyi.jdbcpractise.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.pohribnyi.jdbcpractise.exception.JDBCRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.WritterNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.repository.WritterRepository;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;
import ua.com.pohribnyi.jdbcpractise.util.mapper.PostMapper;
import ua.com.pohribnyi.jdbcpractise.util.mapper.WritterMapper;

public class JDBCWritterRepositoryImpl implements WritterRepository {

	private static final String GET_BY_ID_SQL = "SELECT * FROM writter WHERE id = ?";
	private static final String GET_ALL_SQL = "SELECT * FROM writter";
	private static final String INSERT_SQL = "INSERT INTO writter (first_name, last_name) VALUES(?, ?)";
	private static final String UPDATE_SQL = "UPDATE writter SET first_name = ?, last_name = ? WHERE ID = ?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM writter WHERE id = ?";
	private static final String GET_POSTS_BY_WRITTER_ID_SQL = "SELECT * FROM post WHERE writter_id = ?";

	@Override
	public Writter getById(Long id) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_BY_ID_SQL)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return WritterMapper.mapResultSetWithPostsToWritter(resultSet, getPostsByWritterId(id));
			} else {
				throw new WritterNotFoundException("Writter not found by id: " + id);
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public List<Writter> getAll() {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_SQL)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Writter> writters = new ArrayList<>();
			while (resultSet.next()) {
				writters.add(WritterMapper.mapResultSetWithPostsToWritter(resultSet,
						getPostsByWritterId(resultSet.getLong("id"))));
			}
			return writters;
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public Writter save(Writter t) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(INSERT_SQL)) {
			preparedStatement.setString(1, t.getFirstName());
			preparedStatement.setString(2, t.getLastName());
			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				return WritterMapper.mapResultSetToWritter(resultSet);
			} else {
				throw new JDBCRepoException("Save writter failed, row wasn`t inserted");
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public Writter update(Writter t) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(UPDATE_SQL)) {
			preparedStatement.setString(1, t.getFirstName());
			preparedStatement.setString(2, t.getLastName());
			preparedStatement.setLong(3, t.getId());
			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				return WritterMapper.mapResultSetToWritter(resultSet);
			} else {
				throw new JDBCRepoException("Update writter failed, row wasn`t changed by id: " + t.getId());
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_BY_ID_SQL)) {
			preparedStatement.setLong(1, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new JDBCRepoException("Delete writter failed, row wasn`t deleted by id: " + id);
			}
		} catch (Exception e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	private List<Post> getPostsByWritterId(Long id) {
		List<Post> posts = new ArrayList<>();
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_POSTS_BY_WRITTER_ID_SQL)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				posts.add(PostMapper.mapResultSetToPostWithoutLabels(resultSet));
			}
			return posts;
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

}
