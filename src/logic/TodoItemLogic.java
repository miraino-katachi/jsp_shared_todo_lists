package logic;

import java.util.List;

import dao.TodoItemDAO;
import model.TodoItemModel;

/**
 * TODOアイテムロジッククラス
 */
public class TodoItemLogic {

	/**
	 * TODOアイテムを1件追加します。
	 * @param model TODOアイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean crate(TodoItemModel model) {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.create(model);
	}

	/**
	 * TODOアイテムを全件取得します。
	 * @return TODOアイテムモデルのArrayList
	 */
	public List<TodoItemModel> find() {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.findAll();
	}

	/**
	 * 指定ユーザーIDのTODOアイテムを全件取得します。
	 * @param userId ユーザーID
	 * @return TODOアイテムモデルのArrayList
	 */
	public List<TodoItemModel> find(int userId) {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.findByUserId(userId);
	}

	/**
	 * 指定ユーザーIDのTODOアイテムを取得します。
	 * @param userId ユーザーID
	 * @param limit 取得するレコード数（リミット値）
	 * @param offset 取得開始する行数（オフセット値）
	 * @return TodoItemModelのArrayList
	 */
	public List<TodoItemModel> find(int userId, int limit, int offset) {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.findByUserId(userId, limit, offset);
	}

	/**
	 * 指定ID、指定ユーザーのTODOアイテムを1件取得します
	 * @param id TODOアイテムID
	 * @param userId ユーザーID
	 * @return TODOアイテムモデル
	 */
	public TodoItemModel find(int id, int userId) {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.findOne(id, userId);
	}

	/**
	 * 指定ユーザーIDのTODOアイテムをキーワードで検索します。
	 * @param userId ユーザーID
	 * @param keyWord 検索キーワード
	 * @return TODOアイテムモデルのArrayList
	 */
	public List<TodoItemModel> find(int userId, String keyWord) {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.findByKeyWord(userId, keyWord);
	}

	/**
	 * TODOアイテムを1件更新します。
	 * @param model TODOアイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean update(TodoItemModel model) {
		TodoItemDAO dao = new TodoItemDAO();
		return dao.update(model);
	}
}
