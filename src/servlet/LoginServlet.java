package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.katachi.miraino.settings.MessageSettings;

import logic.UserLogic;
import model.UserModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインページへフォワードする。
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// フォームからのリクエストパラメータを取得する。
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// リクエストパラメータを元に、ユーザーを検索する。
		UserLogic logic = new UserLogic();
		UserModel user = logic.find(email, password);
		if (user == null) {
			// ユーザーが見つからなかったときは、エラーメッセージを設定して、ログインページへフォワードする。
			request.setAttribute("error", MessageSettings.MSG_LOGIN_FAILURE);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		} else {
			// ユーザーが見つかったときは、ユーザーモデルをセッションに保存し、メインページへリダイレクトする。
			// ユーザーモデルがセッションに保存されていることでログイン状態を保持する。
			// セッションからユーザーモデルを削除することでログアウトとする。
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/Main");
		}
	}

}
