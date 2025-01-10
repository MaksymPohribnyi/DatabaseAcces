package ua.com.pohribnyi.jdbcpractise.repository.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import ua.com.pohribnyi.jdbcpractise.exception.HibernateRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.PostNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Post;
import ua.com.pohribnyi.jdbcpractise.repository.PostRepository;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

public class HibernatePostRepositoryImpl implements PostRepository {

	@Override
	public Post getById(Long id) {
		try (Session session = DBUtils.openSession()) {
			Post post = session.createQuery("FROM Post as P LEFT JOIN FETCH P.labels WHERE P.id = :post_id", Post.class)
					.setParameter("post_id", id).uniqueResult();
			if (post == null)
				throw new PostNotFoundException("Post not found by id: " + id);
			post.getWritter().getFirstName(); // instead of Hibernate.initialize(post.getWritter)
			return post;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Error getting Post by id: " + id, e);
		}
	}

	@Override
	public List<Post> getAll() {
		try (Session session = DBUtils.openSession()) {
			// Some practice with Criteria API
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
			CriteriaQuery<Post> cqAll = criteriaQuery.select(criteriaQuery.from(Post.class));
			return session.createQuery(cqAll).getResultList();
		} catch (HibernateException e) {
			throw new HibernateRepoException("Error getting all post`s", e);
		}
	}

	@Override
	public Post save(Post t) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			session.persist(t);
			session.getTransaction().commit();
			return t;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Save post failed", e);
		}
	}

	@Override
	public Post update(Post t) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			session.merge(t);
			session.getTransaction().commit();
			return t;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Update post failed", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			Post postToDelete = session.get(Post.class, id);
			if (postToDelete == null)
				throw new PostNotFoundException("Post not found by id: " + id);
			postToDelete.setStatus(PostStatus.DELETED);
			postToDelete.getLabels().clear();
			session.merge(postToDelete);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw new HibernateRepoException("Delete post failed, row wasn`t deleted by id:" + id, e);
		}
	}

}
