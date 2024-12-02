package ua.com.pohribnyi.jdbcpractise.service;

import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.repository.WritterRepository;

public class WritterService {

	private final WritterRepository writterRepository;

	public WritterService(WritterRepository writterRepository) {
		this.writterRepository = writterRepository;
	}

	public Writter getById(Long id) {
		return writterRepository.getById(id);
	}

	public List<Writter> getAll() {
		return writterRepository.getAll();
	}

	public Writter save(Writter writter) {
		return writterRepository.save(writter);
	}

	public Writter update(Writter writter) {
		return writterRepository.update(writter);
	}

	public void deleteById(Long id) {
		writterRepository.deleteById(id);
	}

}
