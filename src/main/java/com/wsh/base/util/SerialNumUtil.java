package com.wsh.base.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.core.annotation.Order;

import com.wsh.base.util.dateUtil.DateUtil;

/**
 * 
 * @package : com.wsh.base.util
 * @ClassName: SerialNumUtil
 * @Description: 序列号生成器。
 * @author Wally
 * @date 2016年1月11日 上午10:52:46
 * @modify Wally
 * @modifyDate 2016年1月11日 上午10:52:46
 */
public class SerialNumUtil {

/*头字符          用户ID              时间                                     随机数（30+ 2位随机）  
 cz       userid 10位                     18时分秒位 
 cz       0000000001       201601111049       (int)(Math.random()*99)
*/
	 
		
	 /**
	  * 
	  * @Title getMoveOrderNo
	  * @author Wally
	  * @time 2016年1月11日上午11:01:39
	  * @Description 产生订单号
	  * @param
	  * @return String
	  */
	 // CZ000001 2016 01 11 11 38 37 0436 72
	 // CZ000001 2016 01 11 11 38 50 0441 71
	 public synchronized static String getOrderNo(Integer userid,Date date,String businesshead) {
		 return businesshead+String.format("%06d", userid)+DateUtil.getOrderDate(date)+String.valueOf((int)(Math.random()*99));
	 }
	
	 
}
