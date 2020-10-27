package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.TodoItemModel;
import model.UserModel;

/**
 * TODOアイテムDAOクラス
 */
public class TodoItemDAO {
	/** 基本となるSELECT文 */
	private final String BASE_SQL = "select "
			+ "t.id,"
			+ "t.user_id,"
			+ "t.registration_date,"
			+ "t.expiration_date,"
			+ "t.finished_date,"
			+ "t.todo_item,"
			+ "t.is_deleted,"
			+ "t.created_at,"
			+ "t.updated_at,"
			+ "u.email,"
			+ "u.password,"
			+ "u.name,"
			+ "u.is_deleted as user_is_deleted,"
			+ "u.created_at as user_created_at,"
			+ "u.updated_at as user_updated_at "
			+ "from todo_items t "
			+ "inner join users u on t.user_id=u.id ";

	/**
	 * TODOアイテムを全件取得します。
	 * @return TODOアイテムモデルのArrayList
	 */
	public List<TodoItemModel> findAll() {
		List<TodoItemModel> list = new ArrayList<TodoItemModel>();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = BASE_SQL
						+ "where t.is_deleted=0 and u.is_deleted=0 "
						+ "order by t.expiration_date asc, t.id desc";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						// SQLの実行結果をArrayListに格納する
						while (rs.next()) {
							TodoItemModel model = new TodoItemModel();
							model.setId(rs.getInt("id"));
							model.setUserId(rs.getInt("user_id"));
							model.setRegistrationDate(Date.valueOf(rs.getString("registration_date")));
							model.setExpirationDate(Date.valueOf(rs.getString("expiration_date")));
							model.setFinishedDate(Date.valueOf(rs.getString("finished_date")));
							model.setTodoItem(rs.getString("todo_item"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
							model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));

							UserModel userModel = new UserModel();
							userModel.setEmail(rs.getString("email"));
							userModel.setPassword(rs.getString("password"));
							userModel.setName(rs.getString("name"));
							userModel.setIsDeleted(rs.getInt("user_is_deleted"));
							userModel.setCreatedAt(Timestamp.valueOf(rs.getString("user_created_at")));
							userModel.setUpdatedAt(Timestamp.valueOf(rs.getString("user_updated_at")));
							model.setUserModel(userModel);

							list.add(model);
						}
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * TODOアイテムを1件取得します。
	 * @param id TODOアイテムID
	 * @param userId ユーザーID
	 * @return TODOアイテムモデル
	 */
	public TodoItemModel findOne(int id, int userId) {
		TodoItemModel model = new TodoItemModel();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQL文を設定する
				String sql = BASE_SQL
						+ "where t.is_deleted=0 "
						+ "and u.is_deleted=0 "
						+ "and t.id=? "
						+ "and t.user_id=?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定
					stmt.setInt(1, id);
					stmt.setInt(2, userId);
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						// SQLの実行結果を取得する
						if (rs.next()) {
							model.setId(rs.getInt("id"));
							model.setUserId(rs.getInt("user_id"));
							model.setRegistrationDate(Date.valueOf(rs.getString("registration_date")));
							model.setExpirationDate(Date.valueOf(rs.getString("expiration_date")));
							if (rs.getString("finished_date") == null) {
								model.setFinishedDate(null);
							} else {
								model.setFinishedDate(Date.valueOf(rs.getString("finished_date")));
							}
							model.setTodoItem(rs.getString("todo_item"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
							model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));

							UserModel userModel = new UserModel();
							userModel.setEmail(rs.getString("email"));
							userModel.setPassword(rs.getString("password"));
							userModel.setName(rs.getString("name"));
							userModel.setIsDeleted(rs.getInt("user_is_deleted"));
							userModel.setCreatedAt(Timestamp.valueOf(rs.getString("user_created_at")));
							userModel.setUpdatedAt(Timestamp.valueOf(rs.getString("user_updated_at")));
							model.setUserModel(userModel);
						} else {
							model = null;
						}
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return model;
	}

	/**
	 * 指定ユーザーIDのTODOアイテムを取得します。
	 * @param userId ユーザーID
	 * @param limit 取得するレコード数（リミット値）
	 * @param offset 取得開始する行数（オフセット値）
	 * @return TodoItemModelのArrayList
	 */
	public List<TodoItemModel> findByUserId(int userId, int limit, int offset) {
		List<TodoItemModel> list = new ArrayList<TodoItemModel>();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = BASE_SQL
						+ "where t.is_deleted=0 "
						+ "and t.user_id=? "
						+ "order by t.expiration_date asc, t.id desc "
						+ "limit ? offset ?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setInt(1, userId);
					stmt.setInt(2, limit);
					stmt.setInt(3, offset);
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						// SQLの実行結果をArrayListに格納する
						while (rs.next()) {
							TodoItemModel model = new TodoItemModel();
							model.setId(rs.getInt("id"));
							model.setUserId(rs.getInt("user_id"));
							model.setRegistrationDate(Date.valueOf(rs.getString("registration_date")));
							model.setExpirationDate(Date.valueOf(rs.getString("expiration_date")));
							if (rs.getString("finished_date") == null) {
								model.setFinishedDate(null);
							} else {
								model.setFinishedDate(Date.valueOf(rs.getString("finished_date")));
							}
							model.setTodoItem(rs.getString("todo_item"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
							model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));
							list.add(model);
						}
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 指定ユーザーIDのTODOアイテムを全件取得します。
	 * @param userId ユーザーID
	 * @return TodoItemModelのArrayList
	 */
	public List<TodoItemModel> findByUserId(int userId) {
		return findByUserId(userId, Integer.MAX_VALUE, 0);
	}

	/**
	 * 指定ユーザーIDのTODOアイテムのレコードの件数を取得します。
	 * @param userId ユーザーID
	 * @return レコード数
	 */
	public int countByUserId(int userId) {
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "select count(t.id) as cnt "
						+ "from todo_items t "
						+ "inner join users u on t.user_id=u.id "
						+ "where t.is_deleted=0 "
						+ "and t.user_id=? "
						+ "order by t.expiration_date asc, t.id desc";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setInt(1, userId);
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {
							return rs.getInt("cnt");
						} else {
							return 0;
						}
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 指定ユーザーIDのTODOアイテムをキーワードで検索します。
	 * @return TODOアイテムモデルのArrayList
	 */
	public List<TodoItemModel> findByKeyWord(int userId, String keyWord) {
		List<TodoItemModel> list = new ArrayList<TodoItemModel>();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = BASE_SQL
						+ "where t.is_deleted=0 "
						+ "and t.user_id=? "
						+ "and t.todo_item like ? "
						+ "order by t.expiration_date asc, t.id desc";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setInt(1, userId);
					stmt.setString(2, "%" + keyWord + "%");
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						// SQLの実行結果をArrayListに格納する
						while (rs.next()) {
							TodoItemModel model = new TodoItemModel();
							model.setId(rs.getInt("id"));
							model.setUserId(rs.getInt("user_id"));
							model.setRegistrationDate(Date.valueOf(rs.getString("registration_date")));
							model.setExpirationDate(Date.valueOf(rs.getString("expiration_date")));
							if (rs.getString("finished_date") == null) {
								model.setFinishedDate(null);
							} else {
								model.setFinishedDate(Date.valueOf(rs.getString("finished_date")));
							}
							model.setTodoItem(rs.getString("todo_item"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
							model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));
							list.add(model);
						}
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * TODOアイテムを1件追加します。
	 * @param model TodoItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean create(TodoItemModel model) {
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "insert into todo_items ("
						+ "user_id ,"
						+ "registration_date,"
						+ "expiration_date,"
						+ "finished_date,"
						+ "todo_item,"
						+ "is_deleted"
						+ ") values ("
						+ "?," // user_id
						+ "?," // registration_date
						+ "?," // expiration_date
						+ "?," // finished_date
						+ "?," // todo_item
						+ "?" // is_deleted
						+ ")";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setInt(1, model.getUserId());
					stmt.setString(2, model.getRegistrationDate().toString());
					stmt.setString(3, model.getExpirationDate().toString());
					if (model.getFinishedDate() == null) {
						stmt.setString(4, null);
					} else {
						stmt.setString(4, model.getFinishedDate().toString());
					}
					stmt.setString(5, model.getTodoItem());
					stmt.setInt(6, model.getIsDeleted());

					// SQLを実行する
					stmt.executeUpdate();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * TODOアイテムを1件更新します。
	 * @param model TodoItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean update(TodoItemModel model) {
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "update todo_items set "
						+ "user_id=?,"
						+ "registration_date=?,"
						+ "expiration_date=?,"
						+ "finished_date=?,"
						+ "todo_item=?,"
						+ "is_deleted=? "
						+ "where id=?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setInt(1, model.getUserId());
					stmt.setString(2, model.getRegistrationDate().toString());
					stmt.setString(3, model.getExpirationDate().toString());
					if (model.getFinishedDate() == null) {
						stmt.setString(4, null);
					} else {
						stmt.setString(4, model.getFinishedDate().toString());
					}
					stmt.setString(5, model.getTodoItem());
					stmt.setInt(6, model.getIsDeleted());
					stmt.setInt(7, model.getId());

					// SQLを実行する
					stmt.executeUpdate();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
