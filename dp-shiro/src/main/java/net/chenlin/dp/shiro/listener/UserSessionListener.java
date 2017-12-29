package net.chenlin.dp.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * session监听器
 * @author ZhouChenglin
 * @date 2017/12/28
 */
public class UserSessionListener implements SessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(UserSessionListener.class);

    @Override
    public void onStart(Session session) {
        LOG.debug("会话创建：{}", session.getId());
    }

    @Override
    public void onStop(Session session) {
        LOG.debug("会话停止：{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        LOG.debug("会话过期：{}", session.getId());
    }
}
