package com.ksenia.dictionary.presentation.dictionary.presenter;

import com.ksenia.dictionary.business.dictionary.DictionaryInteractorException;
import com.ksenia.dictionary.business.dictionary.IDictionaryInteractor;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.model.WordTranslationWithResult;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.presentation.dictionary.view.IDictionaryView;
import com.ksenia.dictionary.utils.rx.RxSchedulersStub;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Samsonova_K on 09.06.2017.
 */

public class DictionaryPresenterTest {
	private IDictionaryView mDictionaryView;
	private IDictionaryInteractor mDictionaryInteractor;
	private DictionaryPresenter mDictionaryPresenter;
	private WordTranslationModel mWordTranslationModel;

	@Before
	public void beforeEachTest() {
		mDictionaryView = mock(IDictionaryView.class);
		mDictionaryInteractor = mock(IDictionaryInteractor.class);
		mDictionaryPresenter = new DictionaryPresenter(mDictionaryInteractor, new RxSchedulersStub());
		mWordTranslationModel = WordTranslationModel.newWordTranslationModel("mother","мама", Language.Russian);
	}

	@Test
	public void loadDictionary() {
		List<WordTranslationModel> wordTranslationModelList = new ArrayList<WordTranslationModel>() {};
		wordTranslationModelList.add(mWordTranslationModel);
		// mock interactor
		when(mDictionaryInteractor.getDictionary()).thenReturn(Single.fromCallable(()->wordTranslationModelList));
		mDictionaryPresenter.bindView(mDictionaryView);
		mDictionaryPresenter.loadDictionary();
		verify(mDictionaryInteractor).getDictionary();
		verify(mDictionaryView).updateWordList(wordTranslationModelList);
	}

	@Test
	public void addNewWordSuccess() {
		WordTranslationWithResult wordTranslationWithResult = new WordTranslationWithResult(mWordTranslationModel, true);
		// mock interactor
		when(mDictionaryInteractor.getWordTranslation("mother", Language.Russian)).thenReturn(Single.fromCallable(()->wordTranslationWithResult));
		mDictionaryPresenter.bindView(mDictionaryView);
		mDictionaryPresenter.clickAddNewWord("mother", Language.Russian);
		verify(mDictionaryInteractor).getWordTranslation("mother", Language.Russian);
		verify(mDictionaryView).addNewWord(mWordTranslationModel);
	}

	@Test
	public void addNewWordFailed() {
		WordTranslationWithResult wordTranslationWithResult = new WordTranslationWithResult(mWordTranslationModel, false);
		// mock interactor
		when(mDictionaryInteractor.getWordTranslation("mother", Language.Russian)).thenReturn(Single.fromCallable(()->wordTranslationWithResult));
		mDictionaryPresenter.bindView(mDictionaryView);
		mDictionaryPresenter.clickAddNewWord("mother", Language.Russian);
		verify(mDictionaryInteractor).getWordTranslation("mother", Language.Russian);
		verify(mDictionaryView, never()).addNewWord(mWordTranslationModel);
	}

	@Test
	public void addNewWordError() {
		// mock interactor
		when(mDictionaryInteractor.getWordTranslation("", Language.Russian)).thenReturn(Single.error(new DictionaryInteractorException()));
		mDictionaryPresenter.bindView(mDictionaryView);
		mDictionaryPresenter.clickAddNewWord("", Language.Russian);
		verify(mDictionaryInteractor).getWordTranslation("", Language.Russian);
		verify(mDictionaryView).showError();
	}
}
