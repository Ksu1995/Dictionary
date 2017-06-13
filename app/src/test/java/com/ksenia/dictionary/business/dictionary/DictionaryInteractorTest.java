package com.ksenia.dictionary.business.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        WordTranslation wordTranslation = new WordTranslation("ru", new String[]{"мама"}, 200);
        // mock
        when(mDictionaryRepository.getWordTranslation("mother", "ru")).thenReturn(Single.fromCallable(() ->
                wordTranslation));
        // create TestSubscriber
        TestSubscriber<WordTranslation> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getWordTranslation("mother", "ru").subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test of the received PersonalFullDataModel
        WordTranslation wordTranslationModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(wordTranslationModel.getTranslation()).isEqualTo(new String[]{"мама"});
        assertThat(wordTranslationModel.getWord()).isEqualTo("mother");
        assertThat(wordTranslationModel.getLanguage()).isEqualTo("ru");
        assertThat(wordTranslationModel.getResponseCode()).isEqualTo(200);
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
        WordTranslation wordTranslation = new WordTranslation("ru", new String[]{"мама"}, 400);
        // mock
        when(mDictionaryRepository.getWordTranslation("", "")).thenReturn(Single.fromCallable(() ->
                wordTranslation));
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
        when(mDictionaryRepository.getDictionary()).thenReturn(Single.fromCallable(() -> new ArrayList<>()));
        // create TestSubscriber
        TestSubscriber<List<WordTranslationModel>> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getDictionary().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<WordTranslationModel> wordTranslationModelList = testSubscriber.getOnNextEvents().get(0);
        assertThat(wordTranslationModelList.isEmpty());


    }

    @Test
    public void getDictionaryNotEmpty() {
        List<WordTranslationModel> dictionary = new ArrayList<>();
        dictionary.add(WordTranslationModel.newWordTranslationModel("mother", "мама", "ru"));
        // mock
        when(mDictionaryRepository.getDictionary()).thenReturn(rx.Single.fromCallable(() -> dictionary));
        // create TestSubscriber
        TestSubscriber<List<WordTranslationModel>> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getDictionary().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<WordTranslationModel> wordTranslationModelList = testSubscriber.getOnNextEvents().get(0);
        assertThat(wordTranslationModelList.size() == 1);
        assertThat(wordTranslationModelList.get(0)).isEqualTo(WordTranslationModel.newWordTranslationModel("mother", "мама", "ru"));
    }

    @Test
    public void saveWordTranslationSuccess() {
        WordTranslationModel wordTranslation = WordTranslationModel.newWordTranslationModel("mother","мама", "ru");
        // mock
        when(mDictionaryRepository.saveWordTranslation(wordTranslation)).thenReturn(true);
        // call method and get result
        assertThat(mDictionaryInteractor.saveWordTranslation(wordTranslation));
    }

    @Test
    public void saveWordTranslationFailed() {
        WordTranslationModel wordTranslation = WordTranslationModel.newWordTranslationModel("mother","мама", "ru");
        // mock
        when(mDictionaryRepository.saveWordTranslation(wordTranslation)).thenReturn(false);
        // call method and get result
        assertThat(!mDictionaryInteractor.saveWordTranslation(wordTranslation));
    }

}
