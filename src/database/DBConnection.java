package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.katachi.miraino.settings.DatabaseSettings;

/**
 * データベース接続設定クラス
 */
public class DBConnection {

	private Connection connection;

	/**
	 * データベース接続クラス
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public  DBConnection() throws ClassNotFoundException, SQLException {
		// JDBCドライバを読み込み
		Class.forName(DatabaseSettings.DRIVER_NAME);
		// データベースへ接続
		this.connection= DriverManager.getConnection(DatabaseSettings.JDBC_URL, DatabaseSettings.DB_USER, DatabaseSettings.DB_PASS);
	}

	/**
	 * データベース接続（セッション）を取得します。
	 * @return データベース接続（セッション）
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		// データベースへ接続
		return this.connection;
	}
}
