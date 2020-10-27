package test;

import java.util.List;

import dao.TodoItemDAO;
import model.TodoItemModel;

public class TodoItem {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		TodoItemDAO dao = new TodoItemDAO();
		List<TodoItemModel> model = dao.findByUserId(1);
		for (TodoItemModel value : model) {
			System.out.println(value.getId());
		}
	}

}
