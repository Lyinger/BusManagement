/**
 * 
 */
package sql.mysystem.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author admin
 *
 */
public class sqlConnect {

	public Connection ConnectSQL() {
		Connection connection;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/busmanagement?useUnicode=true&characterEncoding=UTF-8";
		String name = "root";
		String password = "123456";

		try {
			Class.forName(driver);// 加载驱动
			connection = DriverManager.getConnection(url, name, password);// 建立连接
			log();
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void log(){
		System.out.println("------------------------------------------------------------------");
		System.out.println("连接成功！");
		System.out.println("------------------------------------------------------------------");
	}
}
