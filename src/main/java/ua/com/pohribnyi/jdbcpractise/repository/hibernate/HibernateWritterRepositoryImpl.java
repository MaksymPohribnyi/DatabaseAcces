package ua.com.pohribnyi.jdbcpractise.repository.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ua.com.pohribnyi.jdbcpractise.exception.HibernateRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.WritterNotFoundException;
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
//			// For tests:
//			t.getPosts().add(Post.builder().content("new post").writter(t).build());
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
//			// -- checking the Hibernate response on operation with related list
//			Writter entity = session.getReference(Writter.class, t.getId());
//			List<Post> writterPosts = entity.getPosts();
//			Post writterTestPost = writterPosts.stream().filter(p -> p.getContent().contains("test")).findAny()
//					.orElse(null);
//			if (writterTestPost != null) {
//				writterTestPost.setContent("testUPD");
//				writterTestPost.setUpdatedAt(new Date());
//			}
//			writterPosts.removeIf((p -> p.getId() == 24)); // if orphanRemoval = true then hard delete post from db,
//															// else we should get post and set post.wrtitter = null
//															// then post.writter_id = null in db
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
