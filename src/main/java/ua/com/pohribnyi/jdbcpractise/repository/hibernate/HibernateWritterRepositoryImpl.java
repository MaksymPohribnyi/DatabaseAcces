package ua.com.pohribnyi.jdbcpractise.repository.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ua.com.pohribnyi.jdbcpractise.exception.HibernateRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.WritterNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.repository.WritterRepository;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;

public class HibernateWritterRepositoryImpl implements WritterRepository {

	@Override
	public Writter getById(Long id) {
		try (Session session = DBUtils.openSession()) {
			Writter writter = session
					.createQuery("FROM Writter as w LEFT JOIN FETCH w.posts WHERE w.id = :writter_id", Writter.class)
					.setParameter("writter_id", id).uniqueResult();
			if (writter == null) {
				throw new WritterNotFoundException("Writter not found by id: " + id);
			}
			return writter;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Error getting Writter by id: " + id, e);
		}
	}

	@Override
	public List<Writter> getAll() {
		try (Session session = DBUtils.openSession()) {
			List<Writter> writters = session.createQuery("FROM Writter", Writter.class).getResultList();
			return writters;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Error getting all writter`s", e);
		}
	}

	@Override
	public Writter save(Writter t) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			session.persist(t);
			session.getTransaction().commit();
			return t;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Save writter failed", e);
		}
	}

	@Override
	public Writter update(Writter t) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
//			// For tests:
//			// -- checking the Hibernation response on operation with related list
//			Writter entity = session.getReference(Writter.class, t.getId());
//			List<Post> writterPosts = entity.getPosts();
//			writterPosts.removeIf((p -> p.getId() == 2));
//			writterPosts.add(null);
//			t.setPosts(writterPosts);
			session.merge(t);
			session.getTransaction().commit();
			return t;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Update writter failed", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			Writter writterToDelete = session.get(Writter.class, id);
			if (writterToDelete == null)
				throw new WritterNotFoundException("Writter not found by id: " + id);
			session.remove(writterToDelete);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw new HibernateRepoException("Delete writter failed, row wasn`t deleted by id: " + id, e);
		}
	}

}
