package com.katachi.miraino.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.katachi.miraino.settings.MessageSettings;

/**
 * ワンタイムトークンのチェックを行うフィルタ
 * Servlet Filter implementation class OneTimeTokenCheckFilter
 */
@WebFilter(filterName = "OneTimeTokenCheckFilter")
public class OneTimeTokenCheckFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public OneTimeTokenCheckFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * セッションに保存されているトークンとPOSTされてきたトークンを比較する（ワンタイムトークンのチェックを行う）。
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// メソッドがPOSTのときのみ処理を行う
		if (((HttpServletRequest) request).getMethod().equals("POST")) {
			HttpSession session = ((HttpServletRequest) request).getSession(true);
			// POSTされてきた値とセッションの値を比較する
			// Objects.equals(a, b)は、abそれぞれがnullであってもNullPointerExceptionが発生しない
			if (!Objects.equals(request.getParameter("token"), session.getAttribute("token"))) {
				// ログインしている場合はログアウトさせる
				session.removeAttribute("user");
				// エラーメッセージを設定し、ログインページにフォワードする
				((HttpServletRequest) request).setAttribute("error", MessageSettings.MSG_INVALID_PROCESS);
				RequestDispatcher dispatcher = ((HttpServletRequest) request)
						.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
				return;
			}
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
