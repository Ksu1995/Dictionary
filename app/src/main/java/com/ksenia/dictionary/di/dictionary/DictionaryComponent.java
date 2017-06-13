package com.ksenia.dictionary.di.dictionary;

import com.ksenia.dictionary.data.repository.dictionary.DictionaryRepository;
import com.ksenia.dictionary.presentation.dictionary.view.DictionaryFragment;

import dagger.Subcomponent;

/**
 * Created by Samsonova_K on 30.05.2017.
 */
@Subcomponent(modules = {DictionaryModule.class, BdModule.class, NetworkModule.class})
public interface DictionaryComponent {

	void inject(DictionaryFragment dictionaryFragment);

	void inject(DictionaryRepository dictionaryRepository);
}
