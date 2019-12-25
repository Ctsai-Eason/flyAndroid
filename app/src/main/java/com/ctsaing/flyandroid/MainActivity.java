package com.ctsaing.flyandroid;

import android.os.Bundle;

import com.ctsaing.flyandroid.baseMvp.BaseActivity;
import com.ctsaing.flyandroid.bean.BaseBean;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		presenter.presenterGet("");
	}

	@Override
	public void getNetData(BaseBean baseBean) {

	}
}
