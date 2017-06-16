package com.ksenia.dictionary.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ksenia.dictionary.R;
import com.ksenia.dictionary.presentation.dictionary.view.DictionaryFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		if (savedInstanceState == null) {
			// first creation of activity - add fragment to container
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.fmt_container, new DictionaryFragment());
			transaction.commit();
		}
	}
}
