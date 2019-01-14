/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019年01月14日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.common.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.util
 * @ClassName ClassUtils
 * @Author sven
 * @Date 2019年01月14日
 * @Version 1.0
 */
public final class ClassUtils {

    public static Set<Class<?>> getClasses(String packageName){
        Set<Class<?>> classSet = new LinkedHashSet<>();
        String packageDir = packageName.replace(".", "/");
        return classSet;
    }
}
