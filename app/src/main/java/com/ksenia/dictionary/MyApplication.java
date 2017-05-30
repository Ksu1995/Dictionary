package com.ksenia.dictionary;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Ksenia on 29.05.2017.
 */

public class MyApplication extends Application {

    @NonNull
    private AppComponent mAppComponent;

    @NonNull
    public static MyApplication get(@NonNull Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = prepareAppComponent().build();
    }

    @NonNull
    private DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this));
    }

    @NonNull
    public AppComponent applicationComponent() {
        return appComponent;
    }
}
