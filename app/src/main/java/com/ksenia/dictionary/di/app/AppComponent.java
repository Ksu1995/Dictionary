package com.ksenia.dictionary.di.app;

import com.ksenia.dictionary.di.dictionary.BdModule;
import com.ksenia.dictionary.di.dictionary.DictionaryComponent;
import com.ksenia.dictionary.di.dictionary.DictionaryModule;
import com.ksenia.dictionary.di.dictionary.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

	DictionaryComponent plus(DictionaryModule dictionaryModule, BdModule bdModule, NetworkModule networkModule);
}
