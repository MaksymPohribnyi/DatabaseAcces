package ua.com.pohribnyi.jdbcpractise.service;

import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.repository.LabelRepository;

public class LabelService {

	private final LabelRepository labelRepository;

	public LabelService(LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
	}

	public Label getById(Long id) {
		return labelRepository.getById(id);
	}

	public List<Label> getAll() {
		return labelRepository.getAll();
	}

	public Label save(Label label) {
		return labelRepository.save(label);
	}

	public Label update(Label label) {
		return labelRepository.update(label);
	}

	public void deleteById(Long id) {
		labelRepository.deleteById(id);
	}

}
