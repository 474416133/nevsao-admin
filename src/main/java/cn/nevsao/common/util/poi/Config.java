/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019年02月25日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.common.util.poi;

import java.util.Map;
import java.util.Set;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.util.poi
 * @ClassName Config
 * @Author sven
 * @Date 2019年02月25日
 * @Version 1.0
 */
public class Config {
    public static Set bitSet = null;
    static {
        bitSet.add("是");
        bitSet.add("Y");
        bitSet.add("y");
        bitSet.add("1");
    }
}
