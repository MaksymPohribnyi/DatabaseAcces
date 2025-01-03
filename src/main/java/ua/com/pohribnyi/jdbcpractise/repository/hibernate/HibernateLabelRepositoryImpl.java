package ua.com.pohribnyi.jdbcpractise.repository.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ua.com.pohribnyi.jdbcpractise.exception.HibernateRepoException;
import ua.com.pohribnyi.jdbcpractise.exception.LabelNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.repository.LabelRepository;
import ua.com.pohribnyi.jdbcpractise.util.DBUtils;

public class HibernateLabelRepositoryImpl implements LabelRepository {

	@Override
	public Label getById(Long id) {
		try (Session session = DBUtils.openSession()) {
			Label label = session.get(Label.class, id);
			if (label == null)
				throw new LabelNotFoundException("Label not found by id: " + id);
			return label;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Error getting label by id: " + id, e);
		}
	}

	@Override
	public List<Label> getAll() {
		try (Session session = DBUtils.openSession()) {
			return session.createQuery("FROM Label", Label.class).getResultList();
		} catch (HibernateException e) {
			throw new HibernateRepoException("Error getting all label`s", e);
		}
	}

	@Override
	public Label save(Label t) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			session.persist(t);
			session.getTransaction().commit();
			return t;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Save label failed", e);
		}
	}

	@Override
	public Label update(Label t) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			session.merge(t);
			session.getTransaction().commit();
			return t;
		} catch (HibernateException e) {
			throw new HibernateRepoException("Update label failed", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try (Session session = DBUtils.openSession()) {
			session.beginTransaction();
			Label labelToDelete = session.get(Label.class, id);
			if (labelToDelete == null)
				throw new LabelNotFoundException("Label not found by id: " + id);
			session.remove(labelToDelete);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw new HibernateRepoException("Delete label failed, row wasn`t deleted by id: " + id, e);
		}
	}

}
