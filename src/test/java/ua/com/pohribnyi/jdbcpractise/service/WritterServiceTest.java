package ua.com.pohribnyi.jdbcpractise.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.pohribnyi.jdbcpractise.exception.WritterNotFoundException;
import ua.com.pohribnyi.jdbcpractise.model.Writter;
import ua.com.pohribnyi.jdbcpractise.repository.WritterRepository;
import ua.com.pohribnyi.jdbcpractise.utils.WritterDataUtils;

@ExtendWith(MockitoExtension.class)
public class WritterServiceTest {

	@Mock
	private WritterRepository writterRepository;

	@InjectMocks
	private WritterService writterService;

	@Test
	@DisplayName("Test of get writter by id functionality")
	public void given_WritterId_whenGetWritterById_thenWritterIsReturned() {
		// given
		BDDMockito.given(writterRepository.getById(anyLong())).willReturn(WritterDataUtils.getWritterPersisted());
		// when
		Writter obtainedWritter = writterService.getById(5L);
		// then
		assertThat(obtainedWritter).isNotNull();
	}

	@Test
	@DisplayName("Test of get Writter by bad id functionality")
	public void given_BadWritterId_whenGetWritterById_thenExceptionIsThrown() {
		// given
		BDDMockito.given(writterRepository.getById(anyLong())).willThrow(WritterNotFoundException.class);
		// when
		assertThrows(WritterNotFoundException.class, () -> writterService.getById(5L));
		// then
	}

	@Test
	@DisplayName("Test of get all Writters functionality")
	public void given_ThreeWritters_whenGetAllWritters_thenRepoMethodIsCalled() {
		// given
		Writter firstWritter = WritterDataUtils.getWritterTransient();
		Writter secondWritter = WritterDataUtils.getWritterTransient();
		Writter thirdWritter = WritterDataUtils.getWritterTransient();
		BDDMockito.given(writterRepository.getAll()).willReturn(List.of(firstWritter, secondWritter, thirdWritter));
		// when
		List<Writter> obtainedWritters = writterService.getAll();
		// then
		assertThat(obtainedWritters).isNotEmpty();
		assertThat(obtainedWritters.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("Test of save Writter functionality")
	public void given_WritterToSave_whenSaveWritter_thenRepoMethodIsCalled() {
		// given
		Writter WritterToSave = WritterDataUtils.getWritterTransient();
		BDDMockito.given(writterRepository.save(any(Writter.class))).willReturn(WritterDataUtils.getWritterPersisted());
		// when
		Writter savedWritter = writterService.save(WritterToSave);
		// then
		assertThat(savedWritter).isNotNull();
		verify(writterRepository, atLeastOnce()).save(any(Writter.class));
	}

	@Test
	@DisplayName("Test of update Writter functionality")
	public void given_WritterToUpdate_whenUpdateWritter_thenRepoMethodIsCalled() {
		// given
		Writter WritterToUpdate = WritterDataUtils.getWritterTransient();
		BDDMockito.given(writterRepository.update(any(Writter.class))).willReturn(WritterToUpdate);
		// when
		Writter savedWritter = writterService.update(WritterToUpdate);
		// then
		assertThat(savedWritter).isNotNull();
		verify(writterRepository, atLeastOnce()).update(any(Writter.class));
	}

	@Test
	@DisplayName("Test of delete by Writter id functionality")
	public void given_WritterIdToDelete_whenDeleteById_thenRepoMethodIsCalled() {
		// given
		// when
		writterService.deleteById(1L);
		// then
		verify(writterRepository, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	@DisplayName("Test of delete by bad Writter id functionality")
	public void given_BadWritterIdToDelete_whenDeleteById_thenExceptionIsThrown() {
		// given
		BDDMockito.doThrow(WritterNotFoundException.class).when(writterRepository).deleteById(anyLong());
		// when
		assertThrows(WritterNotFoundException.class, () -> writterService.deleteById(5L));
		// then
	}

}
