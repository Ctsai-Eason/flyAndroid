package com.ctsaing.flyandroid.baseMvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;


public abstract class BaseActivity extends FragmentActivity implements BaseContract.View {

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
