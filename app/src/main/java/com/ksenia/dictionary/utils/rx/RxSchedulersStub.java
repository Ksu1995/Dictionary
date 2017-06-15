package com.ksenia.dictionary.utils.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Samsonova_K on 15.06.2017.
 */

public class RxSchedulersStub implements IRxSchedulers {

	@Override
	public Scheduler getMainThreadScheduler() {
		return Schedulers.immediate();
	}

	@Override
	public Scheduler getIOScheduler() {
		return Schedulers.immediate();
	}

	@Override
	public Scheduler getComputationScheduler() {
		return Schedulers.immediate();
	}
}
