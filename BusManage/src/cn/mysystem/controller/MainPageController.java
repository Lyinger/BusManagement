/**
 * 
 */
package cn.mysystem.controller;

import com.jfinal.core.Controller;

/**
 * @author admin
 *
 */
public class MainPageController extends Controller{
	public void index(){
		this.render("/MainPage.jsp");
	}
}
