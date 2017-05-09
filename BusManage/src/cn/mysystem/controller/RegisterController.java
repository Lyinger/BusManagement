/**
 * 
 */
package cn.mysystem.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sql.mysystem.connect.sqlConnect;

import com.jfinal.core.Controller;

/**
 * @author admin
 *
 */
public class RegisterController extends Controller{
	private Connection conn;
	public RegisterController(){
		conn=new sqlConnect().ConnectSQL();
	}
	
    public void index(){
    	this.render("/register.jsp");
    }
    
    public void UserRegister(){
    	String name = getPara("usernameRegister");
    	String mail=getPara("mail");
        String password = getPara("passwordRegister");
        String passwordEnsured=getPara("passwordEnsured");
       
        PreparedStatement pstmt;
		try {
			if(password.equals(passwordEnsured)){
				pstmt = conn
						.prepareStatement("insert into user(uname,mail,password,manager) values (?,?,?,?)");
				pstmt.setString(1, name);
				pstmt.setString(2,mail);
				pstmt.setString(3,password);
				pstmt.setString(4,"user");
				pstmt.executeUpdate();  	
				pstmt.close();
				/**返回主页面**/
			}
			else{
				System.out.println("两次密码不一致，请重新输入");
                /**弹窗显示**/
				/**返回注册页面**/
			}
		    conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void CompanyRegister(){
    	String companyName = getPara("companyName");
        String telephone = getPara("telephone");
    	String username = getPara("username");
        String password = getPara("password");
        String passwordConfirmed=getPara("passwordConfirmed");
        setSessionAttr("username",username);
       
        PreparedStatement pstmt;
		try {
			if(password.equals(passwordConfirmed)){
				pstmt = conn
						.prepareStatement("insert into user(uname,password,manager) values (?,?,?)");
				pstmt.setString(1,username);
				pstmt.setString(2,password);
				pstmt.setString(3,"temp");
				pstmt.executeUpdate();  
				pstmt = conn
						.prepareStatement("select uid from user where uname=?");
				pstmt.setString(1,username);
			    ResultSet rs=pstmt.executeQuery();
			    String uid="";
			    if(rs.next()) uid=rs.getString("uid");
				pstmt = conn
						.prepareStatement("insert into apply(companyName,tel,uid,status) values (?,?,?,?)");
				pstmt.setString(1,companyName);
				pstmt.setString(2,telephone);
				pstmt.setString(3,uid);
				pstmt.setString(4,"待审核");
				pstmt.executeUpdate();  
				pstmt.close();
				this.render("/ManagerCenter.jsp");
				/**返回主页面**/
			}
			else{
				System.out.println("两次密码不一致，请重新输入");
                /**弹窗显示**/
				/**返回注册页面**/
			}
		    conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
