package utils.app.com.commonutlis.utils;

import java.io.Serializable;

public class Result implements Serializable {
	private static final long serialVersionUID = 6810038430975058375L;
	/**
	 * true:code=200,false:code!=200
	 */
	int code;
	/**
	 * 返回信息
	 */
	String msg;
	/**
	 * int 返回总条数
	 */
	int totalCount;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 返回数据
	 */
	String data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
