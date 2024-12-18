package ua.com.pohribnyi.jdbcpractise.exception;

public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException(String message) {
		super(message);
	}
}
