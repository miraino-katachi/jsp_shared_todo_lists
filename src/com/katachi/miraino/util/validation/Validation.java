package com.katachi.miraino.util.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Validation {

	/** リクエスト */
	protected HttpServletRequest request;
	/** エラーが発生した項目名とエラー内容を格納するMap */
	protected Map<String, String> errors;

	/**
	 * バリデーション基底クラス
	 * @param request リクエスト
	 */
	public Validation(HttpServletRequest request) {
		this.request = request;
		this.errors = new HashMap<String, String>();
	}

	/**
	 * バリデーションエラーの有無を判定します。
	 * @return true:エラーがある、false:エラーはない
	 */
	public boolean hasErrors() {
		if (this.errors.size() > 0) {
			return true;
		}
		return false;
	}
}
