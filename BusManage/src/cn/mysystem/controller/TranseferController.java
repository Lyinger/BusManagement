/**
 * 
 */
package cn.mysystem.controller;

import java.io.UnsupportedEncodingException;
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
public class TranseferController extends Controller {
	private Connection conn;
	private String startStation;
	private String finishStation;
	private String routeStationsAll = "";
	private String rnumAll = "";
	private String startStationId;
	private String finishStationId;

	public TranseferController() {
		conn = new sqlConnect().ConnectSQL();
	}

	public void index() {

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

	public boolean getInfo() {
		ResultSet result;
		PreparedStatement pstmt;
		String cityId = getSessionAttr("cityId");
		String CityIdBegin = "s" + cityId;
		try {
//			startStation = new String(getPara("startStation").getBytes(
//					"ISO-8859-1"), "UTF-8");
//			finishStation = new String(getPara("finishStation").getBytes(
//					"ISO-8859-1"), "UTF-8");
			startStation=getPara("startStation");
			finishStation=getPara("finishStation");
			pstmt = conn
					.prepareStatement("select * from station where substr(sid,1,3)=? and sname=?");
			pstmt.setString(1, CityIdBegin);
			pstmt.setString(2, startStation);
			result = pstmt.executeQuery();
			if (!result.next()) {
				// 没有该站点
				return false;
			} else {
				startStationId = result.getString("sid");
				pstmt = conn
						.prepareStatement("select * from station where substr(sid,1,3)=? and sname=?");
				pstmt.setString(1, CityIdBegin);
				pstmt.setString(2, finishStation);
				result = pstmt.executeQuery();
				if (!result.next()) {
					// 没有该站点
					return false;
				} else {
					finishStationId = result.getString("sid");
					return true;
				}
			}
		}
//		catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// 线路查询
	public void transefer() {
		getCityId();

		getInfo();
		TranseferZERO();
		TranseferONE();
		TranseferTwo();
		setSessionAttr("routeStations", routeStationsAll);
		setSessionAttr("routes", rnumAll);
		this.render("/TranseferResult.jsp");
	}

	public boolean TranseferZERO(){ 
		ResultSet result;
		PreparedStatement pstmt;
		try {
				pstmt = conn
						.prepareStatement("select A.RouteId from routeorder as A,routeorder as B where A.RouteId=B.RouteId and A.StationId=? and B.StationId=? ");
				pstmt.setString(1, startStationId);
				pstmt.setString(2, finishStationId);
				result = pstmt.executeQuery();
				String routeId = "";
				if(!result.next()) return false;
				else{
					routeId = routeId + result.getString("A.RouteId") + ";";
					while (result.next()) {
						routeId = routeId + result.getString("A.RouteId") + ";";
					}
					String[] routeIds = routeId.split(";");
					String routeStations = "";
					int startStationOrder = 0;
					int finishStationOrder = 0;
					String rnum = "";
					for (int i = 0; i < routeIds.length; i++) {
						routeStations = startStation + ";";
						pstmt = conn
								.prepareStatement("select rnum,type from route where rid=?");
						pstmt.setString(1, routeIds[i]);
						result = pstmt.executeQuery();
						if (result.next()) {
							rnum = getRnum(result.getString("rnum"),
									result.getString("type"));
						}
						pstmt = conn
								.prepareStatement("select routeorder.`order` as startOrder from routeorder where RouteId=? and StationId=?");
						pstmt.setString(1, routeIds[i]);
						pstmt.setString(2, startStationId);
						result = pstmt.executeQuery();
						if (result.next())
							startStationOrder = Integer.parseInt(result
									.getString("startOrder"));
						pstmt = conn
								.prepareStatement("select routeorder.`order` as finishOrder from routeorder where RouteId=? and StationId=?");
						pstmt.setString(1, routeIds[i]);
						pstmt.setString(2, finishStationId);
						result = pstmt.executeQuery();
						if (result.next())
							finishStationOrder = Integer.parseInt(result
									.getString("finishOrder"));
						if (startStationOrder < finishStationOrder) {
							pstmt = conn
									.prepareStatement("select sname from station where sid in(select StationId from routeorder where routeorder.`order`<? and routeorder.`order`>? and RouteId=? order by routeorder.`order` asc)");
							pstmt.setInt(1, finishStationOrder);
							pstmt.setInt(2, startStationOrder);
							pstmt.setString(3, routeIds[i]);
						} else {
							pstmt = conn
									.prepareStatement("select sname from station where sid in(select StationId from routeorder where routeorder.`order`<? and routeorder.`order`>? and RouteId=? order by routeorder.`order` desc)");
							pstmt.setInt(1, startStationOrder);
							pstmt.setInt(2, finishStationOrder);
							pstmt.setString(3, routeIds[i]);
						}
						result = pstmt.executeQuery();
						while (result.next()) {
							routeStations = routeStations
									+ result.getString("sname") + ";";
						}
						routeStations = routeStations + finishStation;
						System.out.println(routeIds[i] + routeStations + rnum);
						routeStationsAll = routeStationsAll + routeStations + "*";
						rnumAll = rnumAll + rnum + "*";
					}
				}
				
				routeStationsAll = routeStationsAll + "%";
				rnumAll = rnumAll + "%";
				System.out.println(routeStationsAll);
				pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean TranseferONE() {
		ResultSet result;
		PreparedStatement pstmt;
		String transferId = "";

		try {
			pstmt = conn
					.prepareStatement("select A.RouteId,C.RouteId,B.StationId from routeorder as A,routeorder as B,routeorder C,routeorder D where A.RouteId=B.RouteId and C.RouteId=D.RouteId and A.RouteId!=C.RouteId and B.StationId=C.StationId and A.StationId != B.StationId and C.StationId !=D.StationId and A.StationId=? and D.stationId=?");
			pstmt.setString(1, startStationId);
			pstmt.setString(2, finishStationId);
			result = pstmt.executeQuery();
			String routeId = "";
			if (!result.next()) {
				return false;
			} else {
				transferId = transferId + result.getString("B.StationId") + ";";
				routeId = routeId + result.getString("A.RouteId") + "@"
						+ result.getString("C.RouteId") + ";";
				while (result.next()) {
					transferId = transferId + result.getString("B.StationId")
							+ ";";
					routeId = routeId + result.getString("A.RouteId") + "@"
							+ result.getString("C.RouteId") + ";";
				}
				System.out.println("@"+transferId);
				System.out.println("%"+routeId);
				String[] routeStras = routeId.split(";");// 得到一种出行方案
				String[] transferIds = transferId.split(";");
				String routeStations;
				for (int j = 0; j < routeStras.length; j++) {
					String[] routeIds = routeStras[j].split("@");
					routeStations = "";// 所需经过的站点
					int startStationOrder = 0;
					int finishStationOrder = 0;
					String rnum = "";// 线路号
					routeStations = startStation + ";";

					// 第一条线
					pstmt = conn
							.prepareStatement("select rnum,type from route where rid=?");
					pstmt.setString(1, routeIds[0]);
					result = pstmt.executeQuery();
					if (result.next()) {
						rnum = rnum+getRnum(result.getString("rnum"),
								result.getString("type"))+"@";
					}
					pstmt = conn
							.prepareStatement("select routeorder.`order` as startOrder from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[0]);
					pstmt.setString(2, startStationId);
					result = pstmt.executeQuery();
					if (result.next())
						startStationOrder = Integer.parseInt(result
								.getString("startOrder"));
					pstmt = conn
							.prepareStatement("select routeorder.`order` as transferOrderOne from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[0]);
					pstmt.setString(2, transferIds[j]);
					result = pstmt.executeQuery();
					if (result.next())
						finishStationOrder = Integer.parseInt(result
								.getString("transferOrderOne"));
					if (startStationOrder < finishStationOrder) {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>? and RouteId=?)order by routeorder.`order` asc");
						pstmt.setString(1, routeIds[0]);
						pstmt.setInt(2, finishStationOrder);
						pstmt.setInt(3, startStationOrder);
						pstmt.setString(4, routeIds[0]);
					} else {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` desc");
						pstmt.setString(1, routeIds[0]);
						pstmt.setInt(2, startStationOrder);
						pstmt.setInt(3, finishStationOrder);
						pstmt.setString(4, routeIds[0]);
					}
					result = pstmt.executeQuery();
					while (result.next()) {
						routeStations = routeStations
								+ result.getString("sname") + ";";
					}
					System.out.println(routeStations);
					routeStations = routeStations + "@";

					// 第二条线
					pstmt = conn
							.prepareStatement("select rnum,type from route where rid=?");
					pstmt.setString(1, routeIds[1]);
					result = pstmt.executeQuery();
					if (result.next()) {
						rnum = rnum+getRnum(result.getString("rnum"),
								result.getString("type"));
					}
					pstmt = conn
							.prepareStatement("select routeorder.`order` as transferOrderTwo from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[1]);
					pstmt.setString(2, transferIds[j]);
					result = pstmt.executeQuery();
					if (result.next())
						startStationOrder = Integer.parseInt(result
								.getString("transferOrderTwo"));

					pstmt = conn
							.prepareStatement("select routeorder.`order` as finishOrder from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[1]);
					pstmt.setString(2, finishStationId);
					result = pstmt.executeQuery();
					if (result.next())
						finishStationOrder = Integer.parseInt(result
								.getString("finishOrder"));
					if (startStationOrder < finishStationOrder) {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` asc");
						pstmt.setString(1, routeIds[1]);
						pstmt.setInt(2, finishStationOrder);
						pstmt.setInt(3, startStationOrder);
						pstmt.setString(4, routeIds[1]);
					} else {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` desc");
						pstmt.setString(1, routeIds[1]);
						pstmt.setInt(2, startStationOrder);
						pstmt.setInt(3, finishStationOrder);
						pstmt.setString(4, routeIds[1]);
					}
					result = pstmt.executeQuery();
					while (result.next()) {
						routeStations = routeStations
								+ result.getString("sname") + ";";
					}
					routeStationsAll = routeStationsAll + routeStations + "*";
					rnumAll = rnumAll + rnum + "*";
				}
			}
			routeStationsAll = routeStationsAll + "%";
			rnumAll = rnumAll + "%";
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean TranseferTwo(){
		ResultSet result;
		PreparedStatement pstmt;
		String transferId = "";

		try {
			pstmt = conn
					.prepareStatement("select A.RouteId,C.RouteId,E.RouteId,B.StationId,D.StationId from routeorder as A,routeorder as B,routeorder C,routeorder D,routeorder E,routeorder F where A.RouteId=B.RouteId and A.RouteId!=C.RouteId and C.RouteId !=E.RouteId and A.RouteId !=E.RouteId and C.RouteId=D.RouteId and E.RouteId=F.RouteId and B.StationId=C.StationId and D.StationId =E.StationId and A.StationId != B.StationId and C.StationId !=D.StationId and E.StationId != F.StationId and A.StationId=? and F.stationId=?");
			pstmt.setString(1, startStationId);
			pstmt.setString(2, finishStationId);
			result = pstmt.executeQuery();
			String routeId = "";
			if (!result.next()) {
               return false;
			} else {
				transferId = transferId + result.getString("B.StationId") +"@"+result.getString("D.StationId")+ ";";
				routeId = routeId + result.getString("A.RouteId") + "@"
						+ result.getString("C.RouteId") + "@"
								+ result.getString("E.RouteId")+ ";";
				while (result.next()) {
					transferId = transferId + result.getString("B.StationId") +"@"+result.getString("D.StationId")+ ";";
					routeId = routeId + result.getString("A.RouteId") + "@"
							+ result.getString("C.RouteId") + "@"
									+ result.getString("E.RouteId")+ ";";
				}
				System.out.println("@"+transferId);
				System.out.println("%"+routeId);
				String[] routeStras = routeId.split(";");// 得到一种出行方案
				String[] transferIds = transferId.split(";");
				String routeStations;
				String[] transferIncludedIds;
				for (int j = 0; j < routeStras.length; j++) {
					transferIncludedIds=transferIds[j].split("@");
					String[] routeIds = routeStras[j].split("@");
					routeStations = "";// 所需经过的站点
					int startStationOrder = 0;
					int finishStationOrder = 0;
					String rnum = "";// 线路号
					routeStations = startStation + ";";

					// 第一条线
					pstmt = conn
							.prepareStatement("select rnum,type from route where rid=?");
					pstmt.setString(1, routeIds[0]);
					result = pstmt.executeQuery();
					if (result.next()) {
						rnum = rnum+getRnum(result.getString("rnum"),
								result.getString("type"))+"@";
					}
					pstmt = conn
							.prepareStatement("select routeorder.`order` as startOrder from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[0]);
					pstmt.setString(2, startStationId);
					result = pstmt.executeQuery();
					if (result.next())
						startStationOrder = Integer.parseInt(result
								.getString("startOrder"));
					pstmt = conn
							.prepareStatement("select routeorder.`order` as transferOrderOne from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[0]);
					pstmt.setString(2, transferIncludedIds[0]);
					result = pstmt.executeQuery();
					if (result.next())
						finishStationOrder = Integer.parseInt(result
								.getString("transferOrderOne"));
					if (startStationOrder < finishStationOrder) {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>? and RouteId=?)order by routeorder.`order` asc");
						pstmt.setString(1, routeIds[0]);
						pstmt.setInt(2, finishStationOrder);
						pstmt.setInt(3, startStationOrder);
						pstmt.setString(4, routeIds[0]);
					} else {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` desc");
						pstmt.setString(1, routeIds[0]);
						pstmt.setInt(2, startStationOrder);
						pstmt.setInt(3, finishStationOrder);
						pstmt.setString(4, routeIds[0]);
					}
					result = pstmt.executeQuery();
					while (result.next()) {
						routeStations = routeStations
								+ result.getString("sname") + ";";
					}
					System.out.println(routeStations);
					routeStations = routeStations + "@";

					// 第二条线
					pstmt = conn
							.prepareStatement("select rnum,type from route where rid=?");
					pstmt.setString(1, routeIds[1]);
					result = pstmt.executeQuery();
					if (result.next()) {
						rnum = rnum+getRnum(result.getString("rnum"),
								result.getString("type"))+"@";
					}
					pstmt = conn
							.prepareStatement("select routeorder.`order` as transferOrderTwo from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[1]);
					pstmt.setString(2, transferIncludedIds[0]);
					result = pstmt.executeQuery();
					if (result.next())
						startStationOrder = Integer.parseInt(result
								.getString("transferOrderTwo"));

					pstmt = conn
							.prepareStatement("select routeorder.`order` as transferOrderThree from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[1]);
					pstmt.setString(2, transferIncludedIds[1]);
					result = pstmt.executeQuery();
					if (result.next())
						finishStationOrder = Integer.parseInt(result
								.getString("transferOrderThree"));
					if (startStationOrder < finishStationOrder) {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` asc");
						pstmt.setString(1, routeIds[1]);
						pstmt.setInt(2, finishStationOrder);
						pstmt.setInt(3, startStationOrder);
						pstmt.setString(4, routeIds[1]);
					} else {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` desc");
						pstmt.setString(1, routeIds[1]);
						pstmt.setInt(2, startStationOrder);
						pstmt.setInt(3, finishStationOrder);
						pstmt.setString(4, routeIds[1]);
					}
					result = pstmt.executeQuery();
					while (result.next()) {
						routeStations = routeStations
								+ result.getString("sname") + ";";
					}
					routeStations = routeStations + "@";
					
					// 第三条线
					pstmt = conn
							.prepareStatement("select rnum,type from route where rid=?");
					pstmt.setString(1, routeIds[2]);
					result = pstmt.executeQuery();
					if (result.next()) {
						rnum = rnum+getRnum(result.getString("rnum"),
								result.getString("type"));
					}
					pstmt = conn
							.prepareStatement("select routeorder.`order` as transferOrderFour from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[2]);
					pstmt.setString(2, transferIncludedIds[1]);
					result = pstmt.executeQuery();
					if (result.next())
						startStationOrder = Integer.parseInt(result
								.getString("transferOrderFour"));

					pstmt = conn
							.prepareStatement("select routeorder.`order` as finishOrder from routeorder where RouteId=? and StationId=?");
					pstmt.setString(1, routeIds[2]);
					pstmt.setString(2, finishStationId);
					result = pstmt.executeQuery();
					if (result.next())
						finishStationOrder = Integer.parseInt(result
								.getString("finishOrder"));
					System.out.println(finishStationOrder);
					if (startStationOrder < finishStationOrder) {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` asc");
						pstmt.setString(1, routeIds[2]);
						pstmt.setInt(2, finishStationOrder);
						pstmt.setInt(3, startStationOrder);
						pstmt.setString(4, routeIds[2]);
					} else {
						pstmt = conn
								.prepareStatement("select sname from station,routeorder where station.sid=routeorder.StationId and routeorder.RouteId=? and sid in(select StationId from routeorder where routeorder.`order`<=? and routeorder.`order`>=? and RouteId=?)order by routeorder.`order` desc");
						pstmt.setString(1, routeIds[2]);
						pstmt.setInt(2, startStationOrder);
						pstmt.setInt(3, finishStationOrder);
						pstmt.setString(4, routeIds[2]);
					}
					result = pstmt.executeQuery();
					while (result.next()) {
						routeStations = routeStations
								+ result.getString("sname") + ";";
					}
					
					routeStationsAll = routeStationsAll + routeStations + "*";
					rnumAll = rnumAll + rnum + "*";
				}
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean getCityId() {
		String city = getSessionAttr("city");
		if (city == null || city.equals("0")) {
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
}
