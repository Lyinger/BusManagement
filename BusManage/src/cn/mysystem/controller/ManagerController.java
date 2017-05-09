/**
 * 
 */
package cn.mysystem.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import sql.mysystem.connect.sqlConnect;

import com.jfinal.core.Controller;
import com.model.company;
import com.model.route;
import com.model.station;

/**
 * @author admin
 * 
 */
public class ManagerController extends Controller {
	private Connection conn;

	public ManagerController() {
		conn = new sqlConnect().ConnectSQL();
	}

	public void index() {

	}

	public void CompleteCompanyInfo() {
		String cityName;
		String cityId;
		String address;
		String carNum=getPara("carNum");
		String routeNum=getPara("routeNum");
		String telephone="";

		PreparedStatement pstmt;
		ResultSet rs;
		try {
			cityName = new String(getPara("cityName").getBytes("ISO-8859-1"),
					"UTF-8");
			cityId = new String(getPara("cityId").getBytes("ISO-8859-1"),
					"UTF-8");
			address = new String(getPara("address").getBytes("ISO-8859-1"),
					"UTF-8");
			
			
			pstmt = conn
					.prepareStatement("insert into city values (?,?)");
			pstmt.setString(1, cityId);
			pstmt.setString(2, cityName);
			pstmt.executeUpdate();
			
			pstmt = conn
					.prepareStatement("select tel from apply where companyName=?");
			pstmt.setString(1,(String)getSessionAttr("companyName"));
			rs=pstmt.executeQuery();
			if(rs.next()){
               telephone=rs.getString("tel");
			}				
			
			pstmt = conn
					.prepareStatement("insert into company(cname,tel,address,carNum,routeNum,uid,cityId) values (?,?,?,?,?,?,?)");
			pstmt.setString(1, (String)getSessionAttr("companyName"));
			pstmt.setString(2, telephone);
			pstmt.setString(3, address);
			pstmt.setInt(4, Integer.parseInt(carNum));
			pstmt.setInt(5, Integer.parseInt(routeNum));
			pstmt.setString(6, (String)getSessionAttr("userId"));
			pstmt.setString(7, cityId);
			pstmt.executeUpdate();
			
			setSessionAttr("judged", "true");
			manageRoute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void judge() {
		PreparedStatement pstmt;
		String username = getSessionAttr("username");
		try {
			pstmt = conn
					.prepareStatement("select status,companyName,uid from apply where uid in (select uid from `user` where uname=?)");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String status = rs.getString("status");
				String companyName=rs.getString("companyName");
				String userId=rs.getString("uid");
				setSessionAttr("status", status);
				setSessionAttr("companyName",companyName);
				setSessionAttr("userId",userId);
				if (status.equals("审核不通过"))
					this.render("/JudgeFailed.jsp");
				else if (status.equals("待审核"))
					this.render("/JudgeUndo.jsp");
				else {
					pstmt = conn
							.prepareStatement("select * from company where uid in (select uid from `user` where uname=?)");
					pstmt.setString(1, username);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						setSessionAttr("judged", "true");
						manageRoute();
					} else{
						this.render("/ManageCompleteInfo.jsp");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getRnum(String rnum, String type) {
		if (type.equals("普通车"))
			rnum = rnum + "路";
		else if (type.equals("空调车"))
			rnum = "K" + rnum + "路";
		else if (type.equals("空调/普通车"))
			rnum = rnum + "/K" + rnum + "路";
		return rnum;
	}

	public void manageRoute() {
		if (getSessionAttr("status").equals("审核通过")) {
			if (getSessionAttr("judged") != null) {
				PreparedStatement pstmt;
				String username = getSessionAttr("username");

				try {
					pstmt = conn
							.prepareStatement("select cityId from user,company where user.uid=company.uid and uname = ?");
					pstmt.setString(1, username);
					ResultSet result = pstmt.executeQuery();
					if (!result.next()) {
						this.render("/MainPage.jsp");
					} else {
						setSessionAttr("CityId", result.getString("cityId"));
						String CityIdBegin = "r" + result.getString("cityId");
						pstmt = conn
								.prepareStatement("select rid,rnum,type from route where substr(rid,1,3)=?");
						pstmt.setString(1, CityIdBegin);
						result = pstmt.executeQuery();
						if (!result.next()) {//没有数据
                            this.render("/ManageNoneRoute.jsp");
						} else {
							List<route> routeList = new ArrayList<route>();
							route rt = new route();
							rt.setRid(result.getString("rid"));
							rt.setRnum(getRnum(result.getString("rnum"),
									result.getString("type")));
							routeList.add(rt);
							while (result.next()) {
								rt = new route();
								rt.setRid(result.getString("rid"));
								rt.setRnum(getRnum(result.getString("rnum"),
										result.getString("type")));
								routeList.add(rt);
							}
							pstmt.close();
							setAttr("route", routeList);

							/**** 供测试使用 ****/
							for (int i = 0; i < routeList.size(); i++) {
								System.out.println(routeList.get(i).getRnum());
							}
							/*****************/

							this.render("/ManageRoute.jsp");
						}
					}
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else
				this.render("/ManageCompleteInfo.jsp");
		} else if (getSessionAttr("status").equals("审核不通过"))
			this.render("/JudgeFailed.jsp");
		else
			this.render("/JudgeUndo.jsp");
	}

	public void deleteRoute() {
		PreparedStatement pstmt;
		String deleteRouteNum = getPara("routeName").substring(0, 1);
		try {
			pstmt = conn
					.prepareStatement("delete from routeorder where RouteId=(select rid from route where rnum=?)");
			pstmt.setString(1, deleteRouteNum);
			pstmt.executeUpdate();

			pstmt = conn.prepareStatement("delete from route where rnum=?");
			pstmt.setString(1, deleteRouteNum);
			pstmt.executeUpdate();
			pstmt.close();
			manageRoute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addRoute() {
		this.render("/ManageAddRoute.jsp");
	}

	public void addRouteSQL() {
		PreparedStatement pstmt;
		String routeNum, carType, routeType;
		int price, cid, interval;
		String starttime, finishtime;
		String CityId = getSessionAttr("CityId");
		String CityIdBegin = "r" + CityId;
		String stations;
		try {
//			routeNum = new String(getPara("routeNum").getBytes("ISO-8859-1"),
//					"UTF-8");
//			carType = new String(getPara("carType").getBytes("ISO-8859-1"),
//					"UTF-8");
//			routeType = new String(getPara("routeType").getBytes("ISO-8859-1"),
//					"UTF-8");
//			price = Integer.parseInt(new String(getPara("price").getBytes(
//					"ISO-8859-1"), "UTF-8"));
//			interval = Integer.parseInt(new String(getPara("interval")
//					.getBytes("ISO-8859-1"), "UTF-8"));
//			starttime = new String(getPara("startTime").getBytes("ISO-8859-1"),
//					"UTF-8");
//			finishtime = new String(getPara("finishTime")
//					.getBytes("ISO-8859-1"), "UTF-8");
//			stations = new String(getPara("stations").getBytes("ISO-8859-1"),
//					"UTF-8");
			
			routeNum=getPara("routeNum");
			carType=getPara("carType");
			routeType=getPara("routeType");
			price=Integer.parseInt(getPara("price"));
			interval=Integer.parseInt(getPara("interval"));			
			starttime=getPara("startTime");
			finishtime=getPara("finishTime");
			stations =getPara("stations");
			pstmt = conn.prepareStatement("select rid from route where rnum=?");
			
			pstmt = conn
					.prepareStatement("select substr(rid,4) as tempRid from route");
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) {
			} else {
				int currentId = Integer.parseInt(result.getString("tempRid"));
				int current;
				while (result.next()) {
					current = Integer.parseInt(result.getString("tempRid"));
					if (currentId < current)
						currentId = current;
				}
				currentId++;
				pstmt = conn
						.prepareStatement("select cid from company where cityId=?");
				pstmt.setString(1, CityId);
				result = pstmt.executeQuery();
				if (result.next()) {
					cid = Integer.parseInt(result.getString("cid"));
					pstmt = conn
							.prepareStatement("insert into route values (?,?,?,?,?,?,?,?,?)");
					setSessionAttr("routeId", CityIdBegin + currentId);
					pstmt.setString(1, CityIdBegin + currentId);
					pstmt.setString(2, routeNum);
					pstmt.setInt(3, price);
					pstmt.setInt(4, cid);
					pstmt.setInt(5, interval);
					pstmt.setString(6, starttime);
					pstmt.setString(7, finishtime);
					pstmt.setString(8, carType);
					pstmt.setString(9, routeType);
					pstmt.executeUpdate();
					pstmt.close();
					addRouteStation(stations);
					manageRoute();
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}

	public void addRouteStation(String stationString) {
		System.out.println(stationString);
		String[] stations = stationString.split("；");
		System.out.println(stations.length);
		boolean exists = true;
		PreparedStatement pstmt = null;
		ResultSet result;
		String CityIdBegin;
		try {
			for (int i = 0; i < stations.length; i++) {
				CityIdBegin = "s" + getSessionAttr("CityId");
				System.out.println(stations[i]);
				pstmt = conn
						.prepareStatement("select sid from station where substr(sid,1,3)=? and sname=?");
				pstmt.setString(1, CityIdBegin);
				pstmt.setString(2, stations[i]);
				result = pstmt.executeQuery();
				if (!result.next()) {// 弹窗提示
					exists = false;
					break;
				} else {
					stations[i] = result.getString("sid");
				}
			}
			if (exists) {
				String routeId = getSessionAttr("routeId");
				for (int i = 0; i < stations.length; i++) {
					pstmt = conn
							.prepareStatement("insert into routeorder values (?,?,?) ");
					pstmt.setString(1, routeId);
					pstmt.setString(2, stations[i]);
					pstmt.setInt(3, i + 1);
					pstmt.executeUpdate();
				}
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modifyRoute() {
		String modifyRouteName;
		PreparedStatement pstmt;
		String CityIdBegin;
		try {
			modifyRouteName = getPara("routeName").substring(0, 1);
			setSessionAttr("routeName", modifyRouteName);
			CityIdBegin = "r" + getSessionAttr("CityId");
			pstmt = conn
					.prepareStatement("select * from route where rnum = ? and substr(rid,1,3)=?");
			pstmt.setString(1, modifyRouteName);
			pstmt.setString(2, CityIdBegin);
			ResultSet result = pstmt.executeQuery();
			route rt = new route();
			if (result.next()) {
				rt.setRnum(result.getString("rnum"));
				rt.setPrice(result.getString("price"));
				rt.setStartTime(result.getString("starttime"));
				rt.setFinishTime(result.getString("finishtime"));
				rt.setInterval(result.getString("interval"));
				rt.setType(result.getString("type"));
				rt.setRouteType(result.getString("routeType"));
				rt.setStations(getStations(result.getString("rid")));
				setAttr("routeInfo", rt);
			}
			pstmt.close();
			manageRoute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.render("/ManageModifyRoute.jsp");
	}

	public void modifyRouteSQL() {
		PreparedStatement pstmt;
		ResultSet result;
		String routeName, rid;
		String routeNum, carType, routeType;
		int price, cid, interval;
		String starttime, finishtime;
		String CityId = getSessionAttr("CityId");
		String stations;
		try {
			routeName = getSessionAttr("routeName");
			routeNum = new String(getPara("routeNum").getBytes("ISO-8859-1"),
					"UTF-8");
			carType = new String(getPara("carType").getBytes("ISO-8859-1"),
					"UTF-8");
			routeType = new String(getPara("routeType").getBytes("ISO-8859-1"),
					"UTF-8");
			price = Integer.parseInt(new String(getPara("price").getBytes(
					"ISO-8859-1"), "UTF-8"));
			interval = Integer.parseInt(new String(getPara("interval")
					.getBytes("ISO-8859-1"), "UTF-8"));
			starttime = new String(getPara("startTime").getBytes("ISO-8859-1"),
					"UTF-8");
			finishtime = new String(getPara("finishTime")
					.getBytes("ISO-8859-1"), "UTF-8");
			stations = new String(getPara("stations").getBytes("ISO-8859-1"),
					"UTF-8");
//			routeNum=getPara("routeNum");
//			carType=getPara("carType");
//			routeType=getPara("routeType");
//			price=Integer.parseInt(getPara("price"));
//			interval=Integer.parseInt(getPara("interval"));			
//			starttime=getPara("startTime");
//			finishtime=getPara("finishTime");
//			stations =getPara("stations");
			pstmt = conn.prepareStatement("select rid from route where rnum=? and substr(rid,1,3)=?");
			pstmt.setString(1, routeName);
			pstmt.setString(2,"r"+CityId);
			result = pstmt.executeQuery();
			if (result.next()) {
				rid = result.getString("rid");
				setSessionAttr("routeId", rid);
				pstmt = conn
						.prepareStatement("select cid from company where cityId=?");
				pstmt.setString(1, CityId);
				result = pstmt.executeQuery();
				if (result.next()) {
					cid = Integer.parseInt(result.getString("cid"));
					pstmt = conn
							.prepareStatement("update route set rnum=?,price=?,cid=?,route.`interval`=?,starttime=?,finishtime=?,type=?,routeType=? where rid=?");
					pstmt.setString(9, rid);
					pstmt.setString(1, routeNum);
					pstmt.setInt(2, price);
					pstmt.setInt(3, cid);
					pstmt.setInt(4, interval);
					pstmt.setString(5, starttime);
					pstmt.setString(6, finishtime);
					pstmt.setString(7, carType);
					pstmt.setString(8, routeType);
					pstmt.executeUpdate();

					pstmt = conn
							.prepareStatement("delete from routeorder where RouteId=?");
					pstmt.setString(1, rid);
					pstmt.executeUpdate();

					pstmt.close();
					addRouteStation(stations);
					manageRoute();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getStations(String rid) {
		PreparedStatement pstmt;
		String stations = "";
		ResultSet result;
		try {
			pstmt = conn
					.prepareStatement("select StationId from routeorder where RouteId=? order by routeorder.`order`");
			pstmt.setString(1, rid);
			result = pstmt.executeQuery();
			while (result.next()) {
				stations = stations + result.getString("StationId") + "；";
			}
			if (stations != null) {
				String[] stationName = stations.split("；");
				stations = "";
				for (int i = 0; i < stationName.length; i++) {
					pstmt = conn
							.prepareStatement("select sname from station where sid=?");
					pstmt.setString(1, stationName[i]);
					result = pstmt.executeQuery();
					if (result.next())
						stationName[i] = result.getString("sname");
					stations = stations + stationName[i] + "；";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stations;
	}

	public void manageStation() {
		if (getSessionAttr("status").equals("审核通过")) {
			if (getSessionAttr("judged") != null) {
				PreparedStatement pstmt;
				try {
					String CityIdBegin = "s" + getSessionAttr("CityId");
					pstmt = conn
							.prepareStatement("select sid,sname from station where substr(sid,1,3)=?");
					pstmt.setString(1, CityIdBegin);
					ResultSet result = pstmt.executeQuery();
					if (!result.next()) {
						this.render("/ManageNoneStation.jsp");
					} else {
						List<station> stationList = new ArrayList<station>();
						station st = new station();
						st.setSid(result.getString("sid"));
						st.setSname(result.getString("sname"));
						stationList.add(st);
						while (result.next()) {
							st = new station();
							st.setSid(result.getString("sid"));
							st.setSname(result.getString("sname"));
							stationList.add(st);
						}
						pstmt.close();
						setAttr("station", stationList);

						/**** 供测试使用 ****/
						for (int i = 0; i < stationList.size(); i++) {
							System.out.println(stationList.get(i).getSname());
						}
						/*****************/

						this.render("/ManageStation.jsp");
					}
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else
				this.render("/ManageCompleteInfo.jsp");
		} else if (getSessionAttr("status").equals("审核不通过"))
			this.render("/JudgeFailed.jsp");
		else
			this.render("/JudgeUndo.jsp");
	}

	/************** 添加站点 ***************/
	public void addStation() {
		this.render("/ManageAddStation.jsp");
	}

	public void addStationSQL() {
		PreparedStatement pstmt;
		String stationName;
		String CityIdBegin = "s" + getSessionAttr("CityId");
		ResultSet result;
		int current;
		int currentId;
		try {
//			stationName = new String(getPara("stationName").getBytes(
//					"ISO-8859-1"), "UTF-8");
			stationName=getPara("stationName");
			pstmt = conn
					.prepareStatement("select * from station where sname=? and substr(sid,1,3)=?");
			pstmt.setString(1, stationName);
			pstmt.setString(2, CityIdBegin);
			result = pstmt.executeQuery();
			if (!result.next()) {

				pstmt = conn
						.prepareStatement("select substr(sid,4) as snum from station");
				result = pstmt.executeQuery();
				if (!result.next()) {
                    currentId=1;
				} else {
					currentId = Integer.parseInt(result.getString("snum"));
					while (result.next()) {
						current = Integer.parseInt(result.getString("snum"));
						if (currentId < current)
							currentId = current;
					}
					currentId++;
				}
					pstmt = conn
							.prepareStatement("insert into station(sid,sname) values (?,?)");
					pstmt.setString(1, CityIdBegin + currentId);
					pstmt.setString(2, stationName);
					pstmt.executeUpdate();
					pstmt.close();

					manageStation();
				conn.close();
			} else {
				manageStation();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}

	/************** 删除站点 **************/
	public void deleteStation() {
		/** 确认删除？ **/
		PreparedStatement pstmt;
		String deleteStationName = getPara("stationName");
		try {
			pstmt = conn
					.prepareStatement("select sid from station where sname=?");
			pstmt.setString(1, deleteStationName);
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) {

			} else {
				String sid = result.getString("sid");
				deleteRouteOrderStation(sid);
				pstmt = conn
						.prepareStatement("delete from station where sid=?");
				pstmt.setString(1, sid);
				pstmt.executeUpdate();
				pstmt.close();

				manageStation();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRouteOrderStation(String sid) {
		PreparedStatement pstmt;
		try {
			pstmt = conn
					.prepareStatement("create view Rstation as select RouteId as tempRid,routeorder.`order` as tempOrder from routeorder where StationId=?");
			pstmt.setString(1, sid);
			pstmt.executeUpdate();
			pstmt = conn
					.prepareStatement("update routeorder,Rstation set routeorder.`order`=routeorder.`order`-1 where routeorder.RouteId=tempRid and routeorder.`order` > tempOrder");
			pstmt.executeUpdate();
			pstmt = conn
					.prepareStatement("delete from routeorder where StationId=?");
			pstmt.setString(1, sid);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement("drop view Rstation");
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/************** 修改站点 **************/
	public void modifyStation() {
		String modifyStationName;
		PreparedStatement pstmt;
		try {
			modifyStationName = getPara("stationName");
			setAttr("station", modifyStationName);
			pstmt = conn
					.prepareStatement("select sid from station where sname = ?");
			pstmt.setString(1, modifyStationName);
			ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				System.out.println(result.getString("sid"));
				setSessionAttr("sid", result.getString("sid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.render("/ManageModifyStation.jsp");
	}

	public void modifyStationSQL() {
		PreparedStatement pstmt;
		String stationName;
		String sid = getSessionAttr("sid");
		System.out.println(sid + "!!");
		try {
//			stationName = new String(getPara("stationName").getBytes(
//					"ISO-8859-1"), "UTF-8");
			stationName=getPara("stationName");
			System.out.println(stationName + "!!");
			pstmt = conn
					.prepareStatement("update station set sname=? where sid=?");
			pstmt.setString(1, stationName);
			pstmt.setString(2, sid);
			pstmt.executeUpdate();
			pstmt.close();
			manageStation();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

	}

	/**************** 管理公司 ***************/
	public void manageInfo() {
		if (getSessionAttr("status").equals("审核通过")) {
			if (getSessionAttr("judged") != null) {
				PreparedStatement pstmt;
				try {
					pstmt = conn
							.prepareStatement("select * from company where cityId = ?");
					pstmt.setString(1, (String) getSessionAttr("CityId"));
					ResultSet result = pstmt.executeQuery();
					if (!result.next()) {

					} else {
						company com = new company();
						com.setCid(result.getString("cid"));
						com.setCname(result.getString("cname"));
						com.setTel(result.getString("tel"));
						com.setAddress(result.getString("address"));
						com.setCarNum(result.getInt("carNum"));
						com.setRouteNum(result.getInt("routeNum"));
						pstmt.close();
						setAttr("company", com);

						/**** 供测试使用 ****/
						System.out.println(com.getCname());
						/*****************/

						this.render("/ManageStation.jsp");
					}
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.render("/ManageInfo.jsp");
			} else
				this.render("/ManageCompleteInfo.jsp");

		} else if (getSessionAttr("status").equals("审核不通过"))
			this.render("/JudgeFailed.jsp");
		else
			this.render("/JudgeUndo.jsp");
	}

	public void modifyInfo() {
		String companyName;
		String telephone;
		String address;
		int carNum = Integer.parseInt(getPara("carNum"));
		int routeNum = Integer.parseInt(getPara("routeNum"));

		PreparedStatement pstmt;
		try {
//			companyName = new String(getPara("companyName").getBytes(
//					"ISO-8859-1"), "UTF-8");
//			telephone = new String(getPara("telephone").getBytes("ISO-8859-1"),
//					"UTF-8");
//			address = new String(getPara("address").getBytes("ISO-8859-1"),
//					"UTF-8");
			companyName=getPara("companyName");
			telephone=getPara("telephone");
			address=getPara("address");
			pstmt = conn
					.prepareStatement("update company set cname=?,tel=?,address=?,carNum=?,routeNum=? where cityId = ?");
			pstmt.setString(1, companyName);
			pstmt.setString(2, telephone);
			pstmt.setString(3, address);
			pstmt.setInt(4, carNum);
			pstmt.setInt(5, routeNum);
			pstmt.setString(6, (String) getSessionAttr("CityId"));
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("存储成功");
			manageInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//			catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		this.render("/ManageInfo.jsp");
	}
}
