/**
 * 
 */
package cn.mysystem.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sql.mysystem.connect.sqlConnect;

import com.jfinal.core.Controller;
import com.model.apply;

/**
 * @author admin
 *
 */
public class AdminController extends Controller{
	private Connection conn;
	public AdminController(){
		conn=new sqlConnect().ConnectSQL();
	}
	public void index(){
		
	}
	
	public void searchApply(){
		PreparedStatement pstmt;

		try {
			pstmt = conn
					.prepareStatement("select * from apply");
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) {
				this.render("/MainPage.jsp");
			} else {
				List<apply> list=new ArrayList<apply>();
				apply ap=new apply();
				ap.setCompanyName(result.getString("companyName"));
				ap.setTelephone(result.getString("tel"));
				ap.setStatus(result.getString("status"));
				list.add(ap);
				while(result.next()){
					ap=new apply();
					ap.setCompanyName(result.getString("companyName"));
					ap.setTelephone(result.getString("tel"));
					ap.setStatus(result.getString("status"));
					list.add(ap);
				}
				setAttr("apply",list);
				this.render("/AdminAllInfo.jsp");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void undoApply(){
		PreparedStatement pstmt;

		try {
			pstmt = conn
					.prepareStatement("select * from apply where status=?");
			pstmt.setString(1, "´ýÉóºË");
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) {
				this.render("/MainPage.jsp");
			} else {
				List<apply> list=new ArrayList<apply>();
				apply ap=new apply();
				ap.setCompanyName(result.getString("companyName"));
				ap.setTelephone(result.getString("tel"));
				ap.setStatus(result.getString("status"));
				list.add(ap);
				while(result.next()){
					ap=new apply();
					ap.setCompanyName(result.getString("companyName"));
					ap.setTelephone(result.getString("tel"));
					ap.setStatus(result.getString("status"));
					list.add(ap);
				}
				setAttr("apply",list);
			}
			this.render("/AdminUndo.jsp");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doneApply(){
		PreparedStatement pstmt;

		try {
			pstmt = conn
					.prepareStatement("select * from apply where status!=?");
			pstmt.setString(1, "´ýÉóºË");
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) {
				this.render("/MainPage.jsp");
			} else {
				List<apply> list=new ArrayList<apply>();
				apply ap=new apply();
				ap.setCompanyName(result.getString("companyName"));
				ap.setTelephone(result.getString("tel"));
				ap.setStatus(result.getString("status"));
				list.add(ap);
				while(result.next()){
					ap=new apply();
					ap.setCompanyName(result.getString("companyName"));
					ap.setTelephone(result.getString("tel"));
					ap.setStatus(result.getString("status"));
					list.add(ap);
				}
				setAttr("apply",list);
				this.render("/AdminDone.jsp");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public void process(){
    	String ResultStatus;
    	PreparedStatement pstmt;
    	String companyName;
    	try {
			ResultStatus = new String(getPara("result").getBytes("ISO-8859-1"),
					"UTF-8");
			companyName = new String(getPara("companyName").getBytes("ISO-8859-1"),
					"UTF-8");
			pstmt = conn
					.prepareStatement("update apply set status=? where companyName=?");
			pstmt.setString(1, ResultStatus);
			pstmt.setString(2, companyName);
			pstmt.executeUpdate();
			
			if(ResultStatus.equals("ÉóºËÍ¨¹ý")){
				pstmt = conn
						.prepareStatement("update `user` set manager=? where `user`.uid in (select apply.uid from apply where companyName=?)");
				pstmt.setString(1, "company");
				pstmt.setString(2, companyName);
				pstmt.executeUpdate();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	undoApply();
    }
}
