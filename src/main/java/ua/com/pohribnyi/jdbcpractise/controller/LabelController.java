package ua.com.pohribnyi.jdbcpractise.controller;

import java.util.List;

import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.service.LabelService;

public class LabelController {

	private final LabelService labelService;

	public LabelController(LabelService service) {
		this.labelService = service;
	}

	public Label getLabelById(Long id) {
		return labelService.getById(id);
	}

	public List<Label> getAllLabels() {
		return labelService.getAll();
	}

	public Label createLabel(String name) {
		return labelService.save(Label.builder()
				.name(name)
				.build());
	}

	public Label updateLabel(Long id, String name) {
		return labelService.update(Label.builder()
				.id(id)
				.name(name)
				.build());
	}

	public void deleteLabelById(Long id) {
		labelService.deleteById(id);
	}

}
