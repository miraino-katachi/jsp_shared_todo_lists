package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.katachi.miraino.settings.DatabaseSettings;

import database.DBConnection;
import model.UserModel;

/**
 * ユーザーDAOクラス
 */
public class UserDAO {

	/**
	 * ユーザーを全件取得します
	 * @return 検索結果（ユーザーモデルのリスト）
	 */
	public List<UserModel> findAll() {
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する
				String sql = "select * from users order by id";
				try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する
					while (rs.next()) {
						UserModel model = new UserModel();
						model.setId(rs.getInt("id"));
						model.setEmail(rs.getString("email"));
						model.setPassword(rs.getString("todo_item"));
						model.setName(rs.getString("name"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
						model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));
						list.add(model);
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
	 * 指定ユーザーIDのユーザーを1件検索します
	 * @param id ユーザーID
	 * @return 検索結果（ユーザーモデル）
	 */
	public UserModel findOne(int id) {
		UserModel model = new UserModel();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "select * from users where id=?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setInt(1, id);
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {
							model.setId(rs.getInt("id"));
							model.setEmail(rs.getString("email"));
							model.setPassword(rs.getString("todo_item"));
							model.setName(rs.getString("name"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
							model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));
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
	 * 指定ユーザーIDのユーザーを1件検索します
	 * @param id ユーザーID
	 * @return 検索結果（ユーザーモデル）
	 */
	public UserModel findOne(String email, String password) {
		UserModel model = new UserModel();
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "select * from users "
						+ "where is_deleted=0 and email=? and password=?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setString(1, email);
					stmt.setString(2, password);
					// SQLを実行する
					try (ResultSet rs = stmt.executeQuery()) {
						// SQLの実行結果を取得する
						if (rs.next()) {
							model.setId(rs.getInt("id"));
							model.setEmail(rs.getString("email"));
							model.setPassword(rs.getString("password"));
							model.setName(rs.getString("name"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(Timestamp.valueOf(rs.getString("created_at")));
							model.setUpdatedAt(Timestamp.valueOf(rs.getString("updated_at")));
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
	 * ユーザーを1件追加します
	 * @param model UserModel
	 * @return 実行結果 1:成功、0:失敗、その他:エラーコード
	 */
	public int create(UserModel model) {
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "insert into users (email, password, name, is_deleted) "
						+ "values (?, ?, ?, ?)";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setString(1, model.getEmail());
					stmt.setString(2, model.getPassword());
					stmt.setString(3, model.getName());
					stmt.setInt(4, model.getIsDeleted());
					// SQLを実行する
					stmt.executeUpdate();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return DatabaseSettings.DB_EXECUTION_FAILURE;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return DatabaseSettings.DB_EXECUTION_SUCCESS;
	}

	/**
	 * 指定ユーザーIDのユーザーを1件更新します。
	 * @param model
	 * @return 実行結果 1:成功、0:失敗、その他:エラーコード
	 */
	public int update(UserModel model) {
		try {
			// データベースに接続する
			DBConnection db = new DBConnection();
			try (Connection connection = db.getConnection()) {
				// SQLを実行する準備をする
				String sql = "update users set "
						+ "email=?, password=?, name=?, is_deleted=? "
						+ "where id=?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setString(1, model.getEmail());
					stmt.setString(2, model.getPassword());
					stmt.setString(3, model.getName());
					stmt.setInt(4, model.getIsDeleted());
					stmt.setInt(5, model.getId());
					// SQLを実行する
					stmt.executeUpdate();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return DatabaseSettings.DB_EXECUTION_FAILURE;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return DatabaseSettings.DB_EXECUTION_SUCCESS;
	}
}
