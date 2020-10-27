package servlet;

import java.io.IOException;
import java.util.List;

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

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/Main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODOリストを取得する。
		TodoItemLogic logic = new TodoItemLogic();
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		List<TodoItemModel> items = null;
		if (request.getParameter("key") != null) {
			// 検索キーワードがある場合
			// GETパラメータで日本語を受け取ると文字化けするので、server.xmlに下記を追記する。
			// useBodyEncodingForURI="true"
			items = logic.find(user.getId(), request.getParameter("key"));
			// 検索窓表示用
			request.setAttribute("key", request.getParameter("key"));
		} else {
			// 検索キーワードがない場合
			items = logic.find(user.getId());
		}
		request.setAttribute("items", items);

		// 今日の日付を取得する（期限日が今日を過ぎているかどうかの判断に使う）。
		java.util.Date today = new java.util.Date();
		request.setAttribute("today", today);

		// メインページへフォワードする。
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
