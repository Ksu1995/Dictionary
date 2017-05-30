package com.ksenia.dictionary.di.dictionary;

import com.ksenia.dictionary.business.dictionary.DictionaryInteractor;
import com.ksenia.dictionary.business.dictionary.IDictionaryInteractor;
import com.ksenia.dictionary.data.repository.dictionary.DictionaryRepository;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;
import com.ksenia.dictionary.presentation.dictionary.presenter.DictionaryPresenter;
import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Samsonova_K on 30.05.2017.
 */
@Module
public class DictionaryModule {

	@Provides
	IDictionaryRepository provideDictionaryRepository() {
		return new DictionaryRepository();
	}

	@Provides
	IDictionaryPresenter provideDictionaryPresenter(IDictionaryInteractor dictionaryInteractor) {
		return new DictionaryPresenter(dictionaryInteractor);
	}

	@Provides
	IDictionaryInteractor provideDictionaryInteractor(IDictionaryRepository dictionaryRepository) {
		return new DictionaryInteractor(dictionaryRepository);
	}

}
