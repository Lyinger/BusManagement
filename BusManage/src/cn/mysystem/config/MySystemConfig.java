/**
 * 
 */
package cn.mysystem.config;

import java.sql.Connection;

import sql.mysystem.connect.sqlConnect;
import cn.mysystem.controller.AdminController;
import cn.mysystem.controller.LoginController;
import cn.mysystem.controller.MainPageController;
import cn.mysystem.controller.ManagerController;
import cn.mysystem.controller.QueryController;
import cn.mysystem.controller.RegisterController;
import cn.mysystem.controller.TranseferController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.model.User;

/**
 * @author admin
 *
 */
public class MySystemConfig extends JFinalConfig {

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configConstant(com.jfinal.config.Constants)
	 */
	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configRoute(com.jfinal.config.Routes)
	 */
	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/",MainPageController.class);
		me.add("/login",LoginController.class);
		me.add("/register",RegisterController.class);
		me.add("/query",QueryController.class);
		me.add("/connect",AdminController.class);
		me.add("/manage",ManagerController.class);
		me.add("/transefer",TranseferController.class);
		me.add("/admin",AdminController.class);
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configPlugin(com.jfinal.config.Plugins)
	 */
	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configInterceptor(com.jfinal.config.Interceptors)
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configHandler(com.jfinal.config.Handlers)
	 */
	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		me.add(new ContextPathHandler("basePath"));
	}
}
