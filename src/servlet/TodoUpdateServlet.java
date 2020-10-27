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

import com.katachi.miraino.util.validation.ValidationUtil;

import logic.TodoItemLogic;
import model.TodoItemModel;
import model.UserModel;
import validation.TodoValidation;

/**
 * Servlet implementation class TodoUpdate
 */
@WebServlet("/TodoUpdate")
public class TodoUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TodoUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// idがint型でなかったら、メインページへリダイレクトする。
		if (!ValidationUtil.isInteger(request.getParameter("id"))) {
			response.sendRedirect(request.getContextPath() + "/Main");
			return;
		}

		// 指定IDのTODOリストを取得する。
		TodoItemLogic logic = new TodoItemLogic();
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		TodoItemModel model = logic.find(Integer.parseInt(request.getParameter("id")), user.getId());

		if (model == null) {
			response.sendRedirect(request.getContextPath() + "/Main");
			return;
		}

		// アップデートページへフォワードする
		request.setAttribute("todoItem", model);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todoUpdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// idがine型でないときは、無条件でMainページ送りにする。
		if (!ValidationUtil.isInteger(request.getParameter("id"))) {
			response.sendRedirect(request.getContextPath() + "/Main");
			return;
		}
		// リクエストパラメータ
		int id = Integer.parseInt(request.getParameter("id"));
		String item = request.getParameter("todoItem");
		String registrationDate = request.getParameter("registrationDate");
		String expirationDate = request.getParameter("expirationDate");
		String finishedDate = null;
		if (request.getParameter("finishedDate") != null) {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			finishedDate = format.format(date);
		}
		String isDeleted = "0";
		if (request.getParameter("isDeleted") != null) {
			isDeleted = "1";
		}

		// バリデーションチェック
		TodoValidation validate = new TodoValidation(request);
		Map<String, String> error = validate.validate();
		// バリデーションエラーがあった時
		if (validate.hasErrors()) {
			request.setAttribute("error", error);
			// リクエストパラメータをMapに保存してformのvalue値に使う
			Map<String, String> todoItem = new HashMap<String, String>();
			todoItem.put("todoItem", item);
			todoItem.put("registrationDate", registrationDate);
			todoItem.put("expirationDate", expirationDate);
			todoItem.put("finishedDate", finishedDate);
			todoItem.put("isDeleted", isDeleted);
			request.setAttribute("todoItem", todoItem);

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/todoUpdate.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// セッションに保存したユーザーモデルを取得する。
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		// リクエストパラメータをTODOモデルに設定する。
		TodoItemModel todoItem = new TodoItemModel();
		todoItem.setId(id);
		todoItem.setUserId(user.getId());
		todoItem.setRegistrationDate(java.sql.Date.valueOf(registrationDate));
		todoItem.setExpirationDate(java.sql.Date.valueOf(expirationDate));
		if (finishedDate == null) {
			todoItem.setFinishedDate(null);
		} else {
			todoItem.setFinishedDate(Date.valueOf(finishedDate));
		}
		todoItem.setTodoItem(item);
		todoItem.setIsDeleted(Integer.parseInt(isDeleted));

		// TODOを更新する
		TodoItemLogic logic = new TodoItemLogic();
		if (!logic.update(todoItem)) {
			// エラーがあったときは、Mainへフォワードする
			request.setAttribute("todoItem", todoItem);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// Mainへリダイレクト
		response.sendRedirect(request.getContextPath() + "/Main");
	}

}
