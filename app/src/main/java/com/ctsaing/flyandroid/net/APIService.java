package com.ctsaing.flyandroid.net;

import com.ctsaing.flyandroid.bean.BaseBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIService {

	@GET
	Observable<BaseBean> sendGet(@Url String url);

	@POST
	@FormUrlEncoded
	Observable<BaseBean> sendPost(@Url String url, @FieldMap Map<String,Object> map);

}
