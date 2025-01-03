package ua.com.pohribnyi.jdbcpractise.exception;

public class HibernateRepoException extends RuntimeException {

	public HibernateRepoException(String message, Throwable cause) {
		super(message, cause);
	}

}
