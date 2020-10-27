package com.katachi.miraino.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.katachi.miraino.util.security.SecurityUtil;

/**
 * ワンタイムトークンを生成して、セッションに保存するフィルタ
 * Servlet Filter implementation class OneTimeTokenFilter
 */
@WebFilter(filterName = "OneTimeTokenFilter")
public class OneTimeTokenFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public OneTimeTokenFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * ワンタイムトークンを生成してセッションに保存するフィルタ。
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ワンタイムトークンを生成してセッションに保存する。
		if (((HttpServletRequest) request).getMethod().equals("GET")) {
			String token = SecurityUtil.generateToken();
			HttpSession session = ((HttpServletRequest) request).getSession(true);
			// JSPでも${token}で取得できる
			session.setAttribute("token", token);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
