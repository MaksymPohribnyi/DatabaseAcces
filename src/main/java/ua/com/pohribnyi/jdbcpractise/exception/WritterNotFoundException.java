package ua.com.pohribnyi.jdbcpractise.exception;

public class WritterNotFoundException extends RuntimeException {

	public WritterNotFoundException(String message) {
		super(message);
	}
}
