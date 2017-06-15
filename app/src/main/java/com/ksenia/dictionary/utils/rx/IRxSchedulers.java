package com.ksenia.dictionary.utils.rx;

import rx.Scheduler;

/**
 * Created by Samsonova_K on 15.06.2017.
 */

public interface IRxSchedulers {

	Scheduler getMainThreadScheduler();

	Scheduler getIOScheduler();

	Scheduler getComputationScheduler();

}
