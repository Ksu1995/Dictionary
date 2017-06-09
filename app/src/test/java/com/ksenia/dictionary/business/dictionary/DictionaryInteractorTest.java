package com.ksenia.dictionary.business.dictionary;

import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;

import org.junit.Before;
import org.junit.Test;

import rx.Single;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Samsonova_K on 09.06.2017.
 */

public class DictionaryInteractorTest {

	private IDictionaryRepository mDictionaryRepository;
	private DictionaryInteractor mDictionaryInteractor;

	@Before
	public void beforeEachTest() {
		mDictionaryRepository = mock(IDictionaryRepository.class);
		mDictionaryInteractor = new DictionaryInteractor(mDictionaryRepository);
	}

	@Test
	public void getWordTranslationSuccess() {
		// mock
		when(mDictionaryRepository.getWordTranslation("", "")).thenReturn(Single.fromCallable(() ->
				new WordTranslation()));
		// create TestSubscriber
		TestSubscriber<WordTranslation> testSubscriber = TestSubscriber.create();
		// call method and get result
		mDictionaryInteractor.getWordTranslation("mother", "adf").subscribe(testSubscriber);
		testSubscriber.awaitTerminalEvent();
		// test no errors was not occurred
		testSubscriber.assertNoErrors();
		testSubscriber.assertCompleted();
		// test of the received PersonalFullDataModel
		WordTranslation wordTranslationModel = testSubscriber.getOnNextEvents().get(0);
		assertThat(wordTranslationModel.getTranslation()).isEqualTo("matsyuk e.v.");
		assertThat(wordTranslationModel.getWord()).isEqualTo("123");
		assertThat(wordTranslationModel.getLanguage()).isEqualTo("456");
		assertThat(wordTranslationModel.getResponseCode()).isEqualTo("100");
	}

	@Test
	public void getWordTranslationEmptyWordError() {
		// mock

		when(mDictionaryRepository.getWordTranslation("", "")).thenReturn(Single.error(new IllegalStateException()));
		// create TestSubscriber
		TestSubscriber<WordTranslation> testSubscriber = TestSubscriber.create();
		// call method and get result
		mDictionaryInteractor.getWordTranslation("", "").subscribe(testSubscriber);
		testSubscriber.awaitTerminalEvent();
		// test error was occurred
		testSubscriber.assertError(DictionaryInteractorException.class);
	}

	@Test
	public void getWordTranslationOtherError() {
		// mock

		when(mDictionaryRepository.getWordTranslation("", "")).thenReturn(Single.error(new IllegalStateException()));
		// create TestSubscriber
		TestSubscriber<WordTranslation> testSubscriber = TestSubscriber.create();
		// call method and get result
		mDictionaryInteractor.getWordTranslation("", "").subscribe(testSubscriber);
		testSubscriber.awaitTerminalEvent();
		// test error was occurred
		testSubscriber.assertError(DictionaryInteractorException.class);
	}

	@Test
	public void getDictionaryEmpty() {
		// mock

		// create TestSubscriber

		// call method and get result

		// test no errors was not occurred

		// test of the received PersonalFullDataModel

	}

	@Test
	public void saveWordTranslationSuccess() {
		// mock
		// create TestSubscriber

		// call method and get result
		// test error was occurred
	}

	@Test
	public void saveWordTranslationFailed() {
		// mock
		// create TestSubscriber
		// call method and get result
		// test error was occurred
	}

}
