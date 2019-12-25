package com.ctsaing.flyandroid.baseMvp;

import com.ctsaing.flyandroid.bean.BaseBean;
import com.ctsaing.flyandroid.net.RetrofitClient;

import java.util.Map;

import io.reactivex.Observable;

public class BaseModel implements BaseContract.Model{

	@Override
	public Observable<BaseBean> sendPost(String url, Map<String, Object> map) {
		return RetrofitClient.getInstance().getApiService().sendPost(url,map);
	}

	@Override
	public Observable<BaseBean> sendGet(String url) {
		return RetrofitClient.getInstance().getApiService().sendGet(url);
	}

}
