package com.ksenia.dictionary.di.dictionary;

import android.content.Context;

import com.ksenia.dictionary.business.dictionary.DictionaryInteractor;
import com.ksenia.dictionary.business.dictionary.IDictionaryInteractor;
import com.ksenia.dictionary.data.repository.dictionary.DictionaryRepository;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;
import com.ksenia.dictionary.presentation.dictionary.presenter.DictionaryPresenter;
import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;
import com.ksenia.dictionary.utils.rx.IRxSchedulers;
import com.ksenia.dictionary.utils.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Samsonova_K on 30.05.2017.
 */
@Module
public class DictionaryModule {

	@Provides
	IDictionaryRepository provideDictionaryRepository(Context context) {
		return new DictionaryRepository(context);
	}

	@Provides
	IDictionaryPresenter provideDictionaryPresenter(IDictionaryInteractor dictionaryInteractor, IRxSchedulers schedulers) {
		return new DictionaryPresenter(dictionaryInteractor, schedulers);
	}

	@Provides
	IDictionaryInteractor provideDictionaryInteractor(IDictionaryRepository dictionaryRepository) {
		return new DictionaryInteractor(dictionaryRepository);
	}

	@Provides
	IRxSchedulers provideRxSchedulers() {
		return new RxSchedulers();
	}

}
