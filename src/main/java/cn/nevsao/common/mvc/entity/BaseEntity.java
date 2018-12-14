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

import cn.nevsao.common.annotation.ExportConfig;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.mvc.entity
 * @ClassName BaseEntity
 * @Author sven
 * @Date 2018年12月10日
 * @Version 1.0
 */

@Data
public class BaseEntity implements Serializable{
    private static final long serialVersionUID = 2296108808835931671L;

    @Id
    @Column(name = "id")
    @ExportConfig(value = "编号")
    private String id;

    @Column(name = "create_time")
    @ExportConfig(value = "操作时间", convert = "c:cn.nevsao.common.util.poi.convert.TimeConvert")
    private Date createTime;


}
