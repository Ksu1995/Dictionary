package com.ksenia.dictionary.business.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.model.WordTranslationWithResult;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

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
    public void getWordTranslationSuccessButNotInserted() {
        WordTranslation wordTranslation = new WordTranslation("ru", new String[]{"мама"}, 200);
        WordTranslationModel wordTranslationModel = WordTranslationModel.newWordTranslationModel("mother", "мама", Language.Russian);
        // mock
        when(mDictionaryRepository.getWordTranslation("mother", Language.Russian)).thenReturn(Single.fromCallable(() ->
                wordTranslation));
        when(mDictionaryRepository.saveWordTranslation(wordTranslationModel)).thenReturn(Single.fromCallable(() ->
                PutResult.newUpdateResult(0,"Table")));
        // create TestSubscriber
        TestSubscriber<WordTranslationWithResult> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getWordTranslation("mother", Language.Russian).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test of the received PersonalFullDataModel
        WordTranslationWithResult wordTranslationWithResult = testSubscriber.getOnNextEvents().get(0);
        assertThat(wordTranslationWithResult.getWordTranslationModel()).isEqualTo(wordTranslationModel);
        assertThat(!wordTranslationWithResult.isInserted());
    }

    @Test
    public void getWordTranslationSuccessAndInserted() {
        WordTranslation wordTranslation = new WordTranslation("ru", new String[]{"мама"}, 200);
        WordTranslationModel wordTranslationModel = WordTranslationModel.newWordTranslationModel("mother", "мама", Language.Russian);
        // mock
        when(mDictionaryRepository.getWordTranslation("mother", Language.Russian)).thenReturn(Single.fromCallable(() ->
                wordTranslation));
        when(mDictionaryRepository.saveWordTranslation(wordTranslationModel)).thenReturn(Single.fromCallable(() ->
                PutResult.newInsertResult(0,"Table")));
        // create TestSubscriber
        TestSubscriber<WordTranslationWithResult> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getWordTranslation("mother", Language.Russian).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test of the received PersonalFullDataModel
        WordTranslationWithResult wordTranslationWithResult = testSubscriber.getOnNextEvents().get(0);
        assertThat(wordTranslationWithResult.getWordTranslationModel()).isEqualTo(wordTranslationModel);
        assertThat(wordTranslationWithResult.isInserted());
    }

    @Test
    public void getWordTranslationEmptyWordError() {
        // mock
        when(mDictionaryRepository.getWordTranslation("", Language.Russian)).thenReturn(Single.error(new IllegalStateException()));
        // create TestSubscriber
        TestSubscriber<WordTranslationWithResult> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getWordTranslation("", Language.Russian).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test error was occurred
        testSubscriber.assertError(DictionaryInteractorException.class);
    }

    @Test
    public void getWordTranslationOtherError() {
        WordTranslation wordTranslation = new WordTranslation("ru", new String[]{"мама"}, 400);
        // mock
        when(mDictionaryRepository.getWordTranslation("", Language.Russian)).thenReturn(Single.fromCallable(() ->
                wordTranslation));
        // create TestSubscriber
        TestSubscriber<WordTranslationWithResult> testSubscriber = TestSubscriber.create();
        // call method and get result
        mDictionaryInteractor.getWordTranslation("", Language.Russian).subscribe(testSubscriber);
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
        dictionary.add(WordTranslationModel.newWordTranslationModel("mother", "мама", Language.Russian));
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
        assertThat(wordTranslationModelList.get(0)).isEqualTo(WordTranslationModel.newWordTranslationModel("mother", "мама", Language.Russian));
    }
}
