/**
 * 
 */
package cn.mysystem.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sql.mysystem.connect.sqlConnect;

import com.jfinal.core.Controller;
import com.model.company;
import com.model.route;
import com.model.station;

/**
 * @author admin
 * 
 */
public class QueryController extends Controller {
	private Connection conn;

	public QueryController() {
		conn = new sqlConnect().ConnectSQL();
	}

	public void index() {

	}

	/***********************公司查询**********************/
	public void queryCompany() {
		if (getCityId()) {
			PreparedStatement pstmt;
			String cityId = getSessionAttr("cityId");
			try {
				pstmt = conn
						.prepareStatement("select * from company where cityId = ?");
				pstmt.setString(1, cityId);
				ResultSet result = pstmt.executeQuery();
				if (!result.next()) {
					this.render("/MainPage.jsp");
				} else {
					List<company> companyList=new ArrayList<company>();
					company com=new company();
					com.setCid(result.getString("cid"));
					com.setCname(result.getString("cname"));
					com.setTel(result.getString("tel"));
					com.setAddress(result.getString("address"));
					com.setCarNum(result.getInt("carNum"));
					com.setRouteNum(result.getInt("routeNum"));
					companyList.add(com);
					while(result.next()){
						com=new company();
						com.setCid(result.getString("cid"));
						com.setCname(result.getString("cname"));
						com.setTel(result.getString("tel"));
						com.setAddress(result.getString("address"));
						com.setCarNum(result.getInt("carNum"));
						com.setRouteNum(result.getInt("routeNum"));
					}
					
					/****供测试使用****/
					for(int i=0;i<companyList.size();i++){
						System.out.println(companyList.get(i).getCid()+":"+companyList.get(i).getCname()+";"+companyList.get(i).getTel()+";"+companyList.get(i).getAddress()+";"+companyList.get(i).getRouteNum()+";"+companyList.get(i).getCarNum());
					}
					/*****************/
					
					setAttr("company",companyList);
					this.render("/company.jsp");
				}
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/***********************路线查询**********************/
	public void queryRoute() {
		if (getCityId()) {
			setSessionAttr("type","route");
			PreparedStatement pstmt;
			String cityId = getSessionAttr("cityId");
			String cityIdBegin = "r" + cityId;
			try {
				pstmt = conn
						.prepareStatement("select * from route where substr(rid,1,3) = ?");
				pstmt.setString(1, cityIdBegin);
				ResultSet result = pstmt.executeQuery();
				if (!result.next()) {
					this.render("/MainPage.jsp");
				} else {
					List<route> routeList=new ArrayList<route>();
					route rt=new route();
					rt.setRid(result.getString("rid"));
					rt=setRNumandPrice(rt,result.getString("type"),result.getString("rnum"),result.getString("price"));
					rt.setInterval(Integer.toString(result.getInt("interval")));
					rt.setStartTime(result.getTime("starttime").toString());
					rt.setFinishTime(result.getTime("finishtime").toString());
					rt.setCompany(getCompany(result.getInt("cid")));
					rt.setStations(getStations(rt.getRid()));
					rt.setType(result.getString("routeType"));
					routeList.add(rt);
					while(result.next()){
						rt=new route();
						rt.setRid(result.getString("rid"));
						rt=setRNumandPrice(rt,result.getString("type"),result.getString("rnum"),result.getString("price"));
						rt.setInterval(Integer.toString(result.getInt("interval")));
						rt.setStartTime(result.getTime("starttime").toString());
						rt.setFinishTime(result.getTime("finishtime").toString());
						rt.setCompany(getCompany(result.getInt("cid")));
						rt.setStations(getStations(rt.getRid()));
						rt.setType(result.getString("routeType"));
						routeList.add(rt);
					}
					
					/****供测试使用****/
					for(int i=0;i<routeList.size();i++){
						System.out.println(routeList.get(i).getRid()+":"+routeList.get(i).getRnum()+";"+routeList.get(i).getPrice()+";"+routeList.get(i).getInterval()+";"+routeList.get(i).getStartTime()+"~"+routeList.get(i).getFinishTime()+";"+routeList.get(i).getCompany());
						System.out.println(routeList.get(i).getStations());
						System.out.println(routeList.get(i).getType());
					}
					/*****************/
					
					pstmt.close();
					setAttr("route",routeList);
					this.render("/route.jsp");
				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public route setRNumandPrice(route rt,String type,String rnum,String price){
		if(type.equals("normal"))
			rnum=rnum+"路";
		else if(type.equals("aircondition")) 
		   rnum="K"+rnum+"路";
		else if(type.equals("air-nor")){
			rnum=rnum+"/K"+rnum+"路";
			price=price+"/2";
		}
		rt.setPrice(price);
		rt.setRnum(rnum);
		return rt;
	}
	public String getCompany(int cid){
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("select * from company where cid = ?");
			pstmt.setInt(1, cid);
			ResultSet result = pstmt.executeQuery();
			String cname=null;
			if(result.next()) cname=result.getString("cname");
			pstmt.close();
			return cname;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getStations(String rid){
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("select * from routeorder,station where routeorder.StationId=station.sid and RouteId = ?");
			pstmt.setString(1, rid);
			ResultSet result = pstmt.executeQuery();
			int i=0;
			while(result.next()){
				i++;
			}
			String[] station=new String[i];
			pstmt = conn.prepareStatement("select * from routeorder,station where routeorder.StationId=station.sid and RouteId = ?");
			pstmt.setString(1, rid);
			result = pstmt.executeQuery();
			while(result.next()){
				station[result.getInt("order")-1]=result.getString("sname");
			}
			String stations="";
			for(i=0;i<station.length;i++){
				stations=stations+station[i]+";";
			}
			pstmt.close();
			return stations;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/****************************************************/
	
	/***********************站点查询**********************/
	public void queryStation() {
		if (getCityId()) {
			String cityId = getSessionAttr("cityId");
			String cityIdBegin = "s" + cityId;
			PreparedStatement pstmt;
			try {
				pstmt = conn
						.prepareStatement("select * from station where substr(sid,1,3) = ?");
				pstmt.setString(1, cityIdBegin);
				ResultSet result = pstmt.executeQuery();
				if(!result.next()) {
					this.render("/MainPage.jsp");
				}else{
					List<station> stationList = new ArrayList<station>();
					station st=new station();
					st.setSid(result.getString("sid"));
					st.setSname(result.getString("sname"));
					st.setRoutes(getRoutes(result.getString("sid")));
			        stationList.add(st);
					while (result.next()) {
						st=new station();
						st.setSid(result.getString("sid"));
						st.setSname(result.getString("sname"));
						st.setRoutes(getRoutes(result.getString("sid")));
				        stationList.add(st);
					}
					
					/****供测试使用****/
					for(int i=0;i<stationList.size();i++){
						System.out.println(stationList.get(i).getSid()+":"+stationList.get(i).getSname());
						System.out.println(stationList.get(i).getRoutes());
					}
					/*****************/
				    
					setAttr("station",stationList);
				    this.render("/station.jsp");
				}
				pstmt.close();
				conn.close();
				/*
				 * 不存在查不到，这里先留着，用来测试 else { pstmt.close(); }
				 */
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public String getRoutes(String sid){
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("select * from routeorder,route where routeorder.RouteId=route.rid and StationId = ?");
			pstmt.setString(1, sid);
			ResultSet result = pstmt.executeQuery();
			String routes="";
			pstmt = conn.prepareStatement("select * from routeorder,route where routeorder.RouteId=route.rid and StationId = ?");
			pstmt.setString(1, sid);
			result = pstmt.executeQuery();
			while(result.next()){
				routes=routes+getRnum(result.getString("rnum"),result.getString("type"))+";";
			}
			pstmt.close();
			return routes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getRnum(String rnum,String type){
		if(type.equals("normal"))
			rnum=rnum+"路";
		else if(type.equals("aircondition")) 
		   rnum="K"+rnum+"路";
		else if(type.equals("air-nor"))
			rnum=rnum+"/K"+rnum+"路";
		return rnum;
	}
	/****************************************************/

	/*********************获取城市相关信息**********************/
	public boolean getCityId() {
		String city = getSessionAttr("city");
		if (city == null||city.equals("0") ) {
			System.out.println("请选择城市");/* 弹窗显示 */
			this.render("/MainPage.jsp");
		} else {
			try {
				PreparedStatement pstmt;
				String sql = "select * from city where name =?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, city);
				ResultSet result = pstmt.executeQuery();
				if (result.next()) {
					System.out.println("查到了");
					System.out.println(result.getString("cid"));
					setSessionAttr("cityId", result.getString("cid"));
					pstmt.close();
					return true;
				}
				/*
				 * 不存在查不到，这里先留着，用来测试 else { pstmt.close(); }
				 */
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public void city() {
		setSessionAttr("city", getPara("city"));
		this.keepPara("city");
		this.render("/MainPage.jsp");
		try {
			conn.close();
			System.out.println("连接关闭");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**********************************************************/
}
