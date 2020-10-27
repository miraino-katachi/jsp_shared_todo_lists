package com.katachi.miraino.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 文字コード設定フィルタ
 */
@WebFilter(filterName="setEncodingFilter")
public class SetEncodingFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 文字コードをUTF-8に設定します。
	 * @see GETパラメータで日本語を送信するときは、server.xmlに下記の記述が必要。<br>
	 * &lt;Connector ... <i>useBodyEncodingForURI="true</i>"/&gt;
	 */
	public void doFilter(ServletRequest request,
			ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}
