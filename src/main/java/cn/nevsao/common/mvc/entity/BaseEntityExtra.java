/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018年12月10日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.common.mvc.entity;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.mvc.entity
 * @ClassName BaseEntityExtra
 * @Author sven
 * @Date 2018年12月10日
 * @Version 1.0
 */

@Data
public class BaseEntityExtra extends BaseEntity {

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "modifier_id")
    private String modifierId;

    @Column(name = "creater_id")
    private String createrId;
}
