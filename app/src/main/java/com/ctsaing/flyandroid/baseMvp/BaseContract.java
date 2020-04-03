package com.ctsaing.flyandroid.baseMvp;

import com.ctsaing.flyandroid.bean.BaseBean;

import java.util.Map;

import io.reactivex.Observable;

public class BaseContract {

	//Model处理网络访问
	interface Model{
		Observable<BaseBean> sendPost(String url, Map<String, Object> map);
		Observable<BaseBean> sendGet(String url);
	}

	//View请求访问，并接收返回的结果
	interface View{

		void getNetData(BaseBean bean);
	}

	//接受View的网络请求发送给model，并处理modle返回的数据，再给View。
	interface Presenter{
		void presenterPost(String url, Map<String, Object> map);
		void presenterGet(String url);
	}

}
