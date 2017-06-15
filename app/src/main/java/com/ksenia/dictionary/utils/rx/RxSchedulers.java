package com.ksenia.dictionary.utils.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Samsonova_K on 15.06.2017.
 */

public class RxSchedulers implements IRxSchedulers {

	@Override
	public Scheduler getMainThreadScheduler() {
		return AndroidSchedulers.mainThread();
	}

	@Override
	public Scheduler getIOScheduler() {
		return Schedulers.io();
	}

	@Override
	public Scheduler getComputationScheduler() {
		return Schedulers.computation();
	}

}
