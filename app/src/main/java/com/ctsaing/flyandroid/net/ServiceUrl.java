package com.ctsaing.flyandroid.net;

public interface ServiceUrl {

	//验证码
	String getVerifycode = "NursingWork/Android/HomWard/SendSmsCode";

	//登录
	String login = "NursingWork/Android/HomWard/Verification";

	//首页
	String homePage = "NursingWork/Android/HomWard/FirstPage";

	//更多评论
	String moreAppraise = "NursingWork/Android/HomWard/MoreComments";

	//活动
	String eventUrl = "NursingWork/Android/HomWard/ActivePageAll";

	//订单
	String orderUrl = "NursingWork/Android/HomWard/SelectOrderingAllByUserId";

	//住院、居家
	String serviceUrl = "NursingWork/Android/HomWard/DayCare";

	//下单地区
	String getAddressUrl = "NursingWork/Android/HomWard/Address";

	//预约下单
	String sendOrder = "NursingWork/Android/HomWard/BookingOrder";

	//提交评论
	String submitAppraise = "NursingWork/Android/HomWard/InsertEvaluate";

	//获取服务协议
	String getAgreement = "NursingWork/Android/HomWard/SLA";
}
