package ua.com.pohribnyi.jdbcpractise.controller;

import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.service.WritterService;

public class WritterController {

	private final WritterService writterService;

	public WritterController(WritterService writterService) {
		this.writterService = writterService;
	}

	public Writter getWritterById(Long id) {
		return writterService.getById(id);
	}

	public List<Writter> getAllWritters() {
		return writterService.getAll();
	}

	public Writter createWritter(String firstName, String lastName) {
		return writterService.save(Writter.builder()
				.firstName(firstName)
				.lastName(lastName)
				.build());
	}

	public Writter updateWritter(Long id, String firstName, String lastName) {
		return writterService.update(Writter.builder()
				.id(id)
				.firstName(firstName)
				.lastName(lastName)
				.build());
	}

	public void deleteWritterById(Long id) {
		writterService.deleteById(id);
	}

	
}
