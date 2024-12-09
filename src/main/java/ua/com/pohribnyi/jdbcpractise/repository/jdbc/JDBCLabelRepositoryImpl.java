package ua.com.pohribnyi.jdbcpractise.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.pohribnyi.jdbcpractise.exception.JDBCRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.LabelNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.repository.LabelRepository;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;
import ua.com.pohribnyi.jdbcpractise.util.mapper.LabelMapper;

public class JDBCLabelRepositoryImpl implements LabelRepository {

	private static final String GET_BY_ID_SQL = "SELECT * FROM label WHERE id = ?";
	private static final String GET_ALL_SQL = "SELECT * FROM label";
	private static final String SAVE_SQL = "INSERT INTO label (name) VALUES (?)";
	private static final String UPDATE_BY_ID_SQL = "UPDATE label SET name = ? WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM label WHERE id = ?";
	

	@Override
	public Label getById(Long id) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_BY_ID_SQL)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return LabelMapper.mapResultSetToLabel(resultSet);
			} else {
				throw new LabelNotFoundException("Label not found by id: " + id);
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public List<Label> getAll() {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_SQL)) {
			List<Label> labels = new ArrayList<>();
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				labels.add(LabelMapper.mapResultSetToLabel(resultSet));
			}
			return labels;
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public Label save(Label t) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(SAVE_SQL)) {
			preparedStatement.setString(1, t.getName());
			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				return LabelMapper.mapResultSetToLabel(resultSet);
			} else {
				throw new JDBCRepoException("Save label failed, row wasn`t inserted");
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

	@Override
	public Label update(Label t) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(UPDATE_BY_ID_SQL)) {
			preparedStatement.setString(1, t.getName());
			preparedStatement.setLong(2, t.getId());
			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				return LabelMapper.mapResultSetToLabel(resultSet);
				} else {
				throw new JDBCRepoException("Update label failed, row wasn`t changed by id: " + t.getId());
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
				throw new JDBCRepoException("DELETE label failed, row wasn`t deleted by id: " + id);
			}
		} catch (SQLException e) {
			throw new JDBCRepoException(e.getMessage());
		}
	}

}
