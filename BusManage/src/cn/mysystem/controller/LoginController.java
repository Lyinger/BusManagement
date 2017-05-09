/**
 * 
 */
package cn.mysystem.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sql.mysystem.connect.sqlConnect;

import com.jfinal.core.Controller;
import com.model.User;

/**
 * @author admin
 *
 */
public class LoginController extends Controller {
	private Connection conn;
	public LoginController(){
		conn=new sqlConnect().ConnectSQL();
	}
	
	public void index(){
		this.render("/login.jsp");
	}
	
	public void submit(){
        String name = getPara("usernameLogin");
        String password = getPara("passwordLogin");
       
        PreparedStatement pstmt;
		try {
			pstmt = conn
					.prepareStatement("select * from user where uname=? and password=?");
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			ResultSet result=pstmt.executeQuery();
			if(result.next()){
				String manager=result.getString("manager");
				setSessionAttr("username",name);
				setSessionAttr("password",password);
				if(manager.equals("admin"))	this.render("/admin.jsp");
			    else if(manager.equals("user")) this.render("/MainPage.jsp");
			    else if(manager.equals("company")||manager.equals("temp")) this.render("/ManagerCenter.jsp");
			}
			else{
				this.render("/login.jsp");
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
