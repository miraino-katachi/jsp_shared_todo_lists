package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.katachi.miraino.settings.DatabaseSettings;
import com.katachi.miraino.settings.MessageSettings;

import logic.UserLogic;
import model.UserModel;
import validation.UserValidation;

/**
 * Servlet implementation class UserCreate
 */
@WebServlet("/UserRegister")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// フォワードする。
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータ
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");

		// バリデーションチェックを行う。
		UserValidation validate = new UserValidation(request);
		Map<String, String> error = validate.validate();
		// バリデーションエラーがあった時
		if (validate.hasErrors()) {
			request.setAttribute("error", error);
			// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
			Map<String, String> user = new HashMap<String, String>();
			user.put("email", email);
			user.put("password", password);
			user.put("name", name);
			request.setAttribute("user", user);

			// ユーザー登録ページへフォワードして終了する。
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// リクエストパラメータをユーザーモデルに設定する
		UserModel user = new UserModel();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);

		// ユーザーを登録する
		UserLogic logic = new UserLogic();
		int ret = logic.create(user);

		// 実行結果により処理を切り替える
		switch (ret) {
		case DatabaseSettings.DB_EXECUTION_SUCCESS:
			// データベース操作成功のとき、ログインページへリダイレクトして終了する。
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		case DatabaseSettings.DB_EXECUTION_FAILURE_ERR_DUP_KEYNAME:
			// ユニークKEYが重複（メールアドレスが重複）しいているとき、エラーメッセージをリクエストスコープに保存する。
			request.setAttribute("db_error", String.format(MessageSettings.MSG_ER_DUP_KEYNAME, user.getEmail()));
			break;
		default:
			// その他エラーのとき、エラーメッセージをリクエストスコープに保存する。
			request.setAttribute("db_error", MessageSettings.MSG_ERROR_OCCURRED);
			break;
		}

		// リクエストスコープにMapに保存したリクエストパラメータを保存する
		request.setAttribute("user", user);

		//ユーザー登録ページへフォワードする。
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
		dispatcher.forward(request, response);
	}

}
