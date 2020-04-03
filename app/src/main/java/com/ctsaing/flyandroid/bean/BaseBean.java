package com.ctsaing.flyandroid.bean;

import com.google.gson.JsonPrimitive;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseBean {

	private boolean flag;
	private String message;
	private JsonPrimitive data;
}
