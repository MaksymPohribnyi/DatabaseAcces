package ua.com.pohribnyi.jdbcpractise.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.pohribnyi.jdbcpractise.exception.LabelNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Label;
import ua.com.pohribnyi.jdbcpractise.repository.LabelRepository;
import ua.com.pohribnyi.jdbcpractise.utils.LabelDataUtils;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {

	@Mock
	private LabelRepository labelRepository;

	@InjectMocks
	private LabelService labelService;

	@Test
	@DisplayName("Test of get label by id functionality")
	public void given_LabelId_whenGetLabelById_thenLabelIsReturned() {
		// given
		BDDMockito.given(labelRepository.getById(anyLong())).willReturn(LabelDataUtils.getLabelPersisted());
		// when
		Label obtainedLabel = labelService.getById(5L);
		// then
		assertThat(obtainedLabel).isNotNull();
	}

	@Test
	@DisplayName("Test of get label by bad id functionality")
	public void given_BadLabelId_whenGetLabelById_thenExceptionIsThrown() {
		// given
		BDDMockito.given(labelRepository.getById(anyLong())).willThrow(LabelNotFoundException.class);
		// when
		assertThrows(LabelNotFoundException.class, () -> labelService.getById(5L));
		// then
	}

	@Test
	@DisplayName("Test of get all labels functionality")
	public void given_ThreeLabels_whenGetAllLabels_thenRepoMethodIsCalled() {
		// given
		Label firstLabel = LabelDataUtils.getLabelTransient();
		Label secondLabel = LabelDataUtils.getLabelTransient();
		Label thirdLabel = LabelDataUtils.getLabelTransient();
		BDDMockito.given(labelRepository.getAll()).willReturn(List.of(firstLabel, secondLabel, thirdLabel));
		// when
		List<Label> obtainedLabels = labelService.getAll();
		// then
		assertThat(obtainedLabels).isNotEmpty();
		assertThat(obtainedLabels.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("Test of save label functionality")
	public void given_LabelToSave_whenSaveLabel_thenRepoMethodIsCalled() {
		// given
		Label labelToSave = LabelDataUtils.getLabelTransient();
		BDDMockito.given(labelRepository.save(any(Label.class))).willReturn(LabelDataUtils.getLabelPersisted());
		// when
		Label savedLabel = labelService.save(labelToSave);
		// then
		assertThat(savedLabel).isNotNull();
		verify(labelRepository, atLeastOnce()).save(any(Label.class));
	}

	@Test
	@DisplayName("Test of update label functionality")
	public void given_LabelToUpdate_whenUpdateLabel_thenRepoMethodIsCalled() {
		// given
		Label labelToUpdate = LabelDataUtils.getLabelTransient();
		BDDMockito.given(labelRepository.update(any(Label.class))).willReturn(labelToUpdate);
		// when
		Label savedLabel = labelService.update(labelToUpdate);
		// then
		assertThat(savedLabel).isNotNull();
		verify(labelRepository, atLeastOnce()).update(any(Label.class));
	}

	@Test
	@DisplayName("Test of delete by label id functionality")
	public void given_LabelIdToDelete_whenDeleteById_thenRepoMethodIsCalled() {
		// given
		// when
		labelService.deleteById(1L);
		// then
		verify(labelRepository, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	@DisplayName("Test of delete by bad label id functionality")
	public void given_BadLabelIdToDelete_whenDeleteById_thenExceptionIsThrown() {
		// given
		BDDMockito.doThrow(LabelNotFoundException.class).when(labelRepository).deleteById(anyLong());
		// when
		assertThrows(LabelNotFoundException.class, () -> labelService.deleteById(5L));
		// then
	}

}
