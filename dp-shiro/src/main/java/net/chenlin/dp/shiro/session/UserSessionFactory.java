package net.chenlin.dp.shiro.session;

import net.chenlin.dp.common.utils.IpUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * session工厂
 * @author ZhouChenglin
 * @date 2017/12/28
 */
public class UserSessionFactory implements SessionFactory{

    @Override
    public Session createSession(SessionContext initData) {
        UserSession session = new UserSession();
        HttpServletRequest request = (HttpServletRequest)initData.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
        session.setHost(getIpAddress(request));
        return session;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        return ip;
    }

}
