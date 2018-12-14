package cn.nevsao.system.domain.user.service;

import cn.nevsao.system.domain.user.entity.UserOnline;

import java.util.List;

public interface SessionService {

	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
