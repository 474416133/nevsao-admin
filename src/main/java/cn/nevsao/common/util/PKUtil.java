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
package cn.nevsao.common.util;

import java.util.UUID;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.util
 * @ClassName PKUtil
 * @Author sven
 * @Date 2018年12月13日
 * @Version 1.0
 */
public final class PKUtil {
    private PKUtil(){}
    public static  String newId(){
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "");
    }

}
