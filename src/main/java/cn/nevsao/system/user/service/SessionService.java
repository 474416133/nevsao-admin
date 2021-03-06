package cn.nevsao.system.user.service;

import cn.nevsao.system.user.entity.UserOnline;

import java.util.List;

public interface SessionService {

	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
