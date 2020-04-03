package com.ctsaing.flyandroid.baseMvp;

import android.util.Log;

import com.ctsaing.flyandroid.bean.BaseBean;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {

	private static final String TAG = "BasePresenter";

	private WeakReference<T> weakReference;
	private BaseModel baseModel;

	public BasePresenter(T baseView) {
		weakReference = new WeakReference<>(baseView);
		baseModel = new BaseModel();
	}

	public void unBindView() {
		if (weakReference != null) {
			weakReference.clear();
			weakReference = null;
			Log.i(TAG, "BasePresenter is on unBindView(),it means the View is on onDestroy(),maybe cause null exception!");
		}
	}

	public T getView() {
		if (weakReference != null) {
			return weakReference.get();
		}
		return null;
	}

	@Override
	public void presenterPost(String url, Map<String, Object> map) {
		//发送网络请求，处理数据
		baseModel.sendPost(url, map).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<BaseBean>() {
					@Override
					public void onSubscribe(Disposable d) {
						System.out.println("Disposable");
					}

					@Override
					public void onNext(BaseBean baseBean) {
						System.out.println(baseBean.toString());
						if (getView() != null)
							getView().getNetData(baseBean);
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("onError" + e.getMessage());
					}

					@Override
					public void onComplete() {
						System.out.println("onComplete");
					}
				});
	}

	@Override
	public void presenterGet(String url) {
		baseModel.sendGet(url).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<BaseBean>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(BaseBean baseBean) {
						System.out.println(baseBean.toString());
						if (getView() != null)
							getView().getNetData(baseBean);
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("onError" + e.getMessage());
					}

					@Override
					public void onComplete() {

					}
				});
	}
}
