package ua.com.pohribnyi.jdbcpractise.util.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.com.pohribnyi.jdbcpractise.model.Label;

public class LabelMapper {

	public static Label mapResultSetToLabel(ResultSet rs) throws SQLException{
		return Label.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.build();
	}
	
	public static Label mapLabelFromPostResultSet(ResultSet rs) throws SQLException {
		return Label.builder()
				.id(rs.getLong("label_id"))
				.name(rs.getString("name"))
				.build();
	}
	public static Label mapLabelFromLabelPostResultSet(ResultSet rs) throws SQLException {
		return Label.builder()
				.id(rs.getLong("label_id"))   
				.build();
	}
	
}
