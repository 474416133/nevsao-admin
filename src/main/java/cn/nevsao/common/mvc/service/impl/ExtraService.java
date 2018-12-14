/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018年12月13日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.common.mvc.service.impl;

import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import cn.nevsao.system.domain.user.entity.User;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.mvc.service.impl
 * @ClassName ExtraService
 * @Author sven
 * @Date 2018年12月13日
 * @Version 1.0
 */
public  class ExtraService<T extends BaseEntityExtra> extends BaseService<T> {

    @Override
    @Transactional
    public int insert(T entity) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        entity.setCreaterId(user.getId());
        return super.insert(entity);
    }

    @Override
    @Transactional
    public int update(T entity) {
        setEntityModifyInfo(entity);
        return super.update(entity);
    }

    @Override
    @Transactional
    public int updateExcludeNull(T entity) {
        setEntityModifyInfo(entity);
        return super.updateExcludeNull(entity);
    }

    private void setEntityModifyInfo(T entity){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        entity.setModifierId(user.getId());
        entity.setModifyTime(new Date());
    }
}
