package ua.com.pohribnyi.jdbcpractise.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;

public class DBUtils {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration()
					.addAnnotatedClass(Label.class)
					.addAnnotatedClass(Writter.class)
					.addAnnotatedClass(Post.class)
					.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

}
