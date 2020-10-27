package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.TodoItemLogic;
import model.TodoItemModel;
import model.UserModel;
import validation.TodoValidation;

/**
 * Servlet implementation class TodoCreate
 */
@WebServlet("/TodoRegister")
public class TodoRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TodoRegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// フォワードする。
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todoRegister.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータ
		String item = request.getParameter("todoItem");
		String registrationDate = request.getParameter("registrationDate");
		String expirationDate = request.getParameter("expirationDate");
		String finishedDate = null;
		if (request.getParameter("finishedDate") != null) {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			finishedDate = format.format(date);
		}

		// バリデーションチェックを行う。
		TodoValidation validate = new TodoValidation(request);
		Map<String, String> error = validate.validate();
		// バリデーションエラーがあった時
		if (validate.hasErrors()) {
			request.setAttribute("error", error);
			// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
			Map<String, String> todoItem = new HashMap<String, String>();
			todoItem.put("todoItem", item);
			todoItem.put("registrationDate", registrationDate);
			todoItem.put("expirationDate", expirationDate);
			todoItem.put("finishedDate", finishedDate);
			request.setAttribute("todoItem", todoItem);

			// 登録ページへフォワードして終了する。
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todoRegister.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// セッションに保存したユーザーモデルを取得する。
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		// リクエストパラメータをTODOモデルに設定する。
		TodoItemModel todoItem = new TodoItemModel();
		todoItem.setUserId(user.getId());
		todoItem.setRegistrationDate(java.sql.Date.valueOf(registrationDate));
		todoItem.setExpirationDate(java.sql.Date.valueOf(expirationDate));
		if (finishedDate == null) {
			todoItem.setFinishedDate(null);
		} else {
			todoItem.setFinishedDate(Date.valueOf(finishedDate));
		}
		todoItem.setTodoItem(item);
		todoItem.setIsDeleted(0);

		// TODOを登録する
		TodoItemLogic logic = new TodoItemLogic();
		if (!logic.crate(todoItem)) {
			// エラーがあったときは、Mainへフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			request.setAttribute("todoItem", todoItem);
			dispatcher.forward(request, response);
			return;
		}

		// Mainへリダイレクト
		response.sendRedirect(request.getContextPath() + "/Main");
	}

}
