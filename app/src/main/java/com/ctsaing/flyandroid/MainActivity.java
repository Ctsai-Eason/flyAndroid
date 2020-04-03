package com.ctsaing.flyandroid;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ctsaing.flyandroid.baseMvp.BaseActivity;
import com.ctsaing.flyandroid.bean.BaseBean;
import com.ctsaing.flyandroid.net.ServiceUrl;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		HorizontalScrollView horizontalScrollView = findViewById(R.id.main_horizon);
//		horizontalScrollView.arrowScroll(HorizontalScrollView.LAYOUT_DIRECTION_RTL);

//		LinearLayout layout = findViewById(R.id.swipe_item_content);
//		Point outSize = new Point();
//		getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);
//		layout.setLayoutParams(new LinearLayout.LayoutParams(outSize.x, LinearLayout.LayoutParams.MATCH_PARENT));


//		presenter.presenterGet(ServiceUrl.homePage);
	}

	@Override
	public void getNetData(BaseBean baseBean) {

	}
}
