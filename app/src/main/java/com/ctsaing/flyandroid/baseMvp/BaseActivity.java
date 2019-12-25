package com.ctsaing.flyandroid.baseMvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

	public BasePresenter presenter;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new BasePresenter(this);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unBindView();
	}

}
