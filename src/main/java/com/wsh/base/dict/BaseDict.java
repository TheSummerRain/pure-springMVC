package com.wsh.base.dict;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础数据字典
 * @author Wally
 */
public class  BaseDict {

	 // 维--标签字典
	 public static final Map<Integer,String> WEI_LABLE=getWei_Lable();
	 public static final Map<Integer, String> ART_TYPE = getArtType();
	 public static final Map<String,String> BANKMAP = getBankMap();
	 
	 public static final String MSG_API_KEY = "f504ef1d5a17091064e33e3a9a369037"; //短信签名。
	 
	 public static final String EXP_BASEPATH_METHOD ="/extCall"; //外部访问的主controller。
	 public static final String BASECALLPATH_METHOD ="/toMyConPage/";  //自定义 访问路径 方法。
	 
	 public static final String SHAREPATH_METHOD = "/toSharePage/" ; //分享的路径。
	 public static final String SHAREPATH_METHOD_QRCODE = "/toShareQrCode/"; //二维码路径。
	 public static final String SHAREPATH_SHOWMEBOOKLIST = "/showMeBookList/" ; //秀一秀书单
	 
	 
	 public static final String WSH_BOOK_COUNT_READ_FLAG = "WSHBREAD_";  //WSH_B_READ_  //book阅读量 redis  key值。
	 public static final String WSH_PL_ZAN_COUNT_FLAG = "WSH_PL_ZAN_"; //评论 点赞标记
	 
	 //基础描述。
	 public static final String OPENVIP ="使用维币开通VIP会员";
	 public static final String WEIXIN ="微信";
	 public static final String SP_WEIBI ="维币余额";
	 public static final String NO_DATA ="没有数据";
	 public static final String USER_ERROR ="错误";
	 public static final String SYS_ERROR = "系统错误";
	 //阅读量平均数
	 public static final int WORDSAVERAGE = 8;   // 每本书的字数大概 2万
	 
	 //推广大使。
	 public static final int POP_SC_DEPTH = 6;   //推广大使查询的 深度。
	 public static final int POP_A_ENOUGH_PERSON = 50; //a级满 多少人，开始计算 def.
	 public static final int POP_ABC_INCOME=50;  //abc层每人50.
	 public static final int POP_DEF_INCOME=5;  //def层每人5块。
	 public static final String POP_WSH_WB_PRE = "WSHPOP_";
	 
	 //提现
	 public static final int TO_CASH_MONEY_MIN=500;  //最低提现金额。
	 
	 //jsp页面
	 public static final String PAGE_ERROR_JSP = "/xpage/pageError";

	 //分页
	 public static final int BOOK_COUNT_NUM = 8; //书籍列表10条
	 public static final int WEI_COUNT_NUM = 10; //维列表
 	 
	 //有时间弄 配置文件。
	 public static final String PICURL = "http://bookclubvip.oss-cn-beijing.aliyuncs.com/11111111.png";
	 public static final String ARTICURL = "http://mp.weixin.qq.com/s?__biz=MzA4OTg1ODc1OA==&mid=570728636&idx=1&sn=a8efc4b01f7f1d25e005f5f6b81160dd#rd";
	 
	 //PID特别处理。外面套一层壳。
	 public static final Integer DISGUISERNUM = 888888; //6个8
	 public static final Integer DISGUISERWHO = 5308; //伪装哪个userId
	 
	 //代理商相关。
	 public static final int AGENT_COMMISSION_RATE = 10;    //代理商佣金比例。10%；别弄混了 
	 public static final int AGENT_NEEDPAY = 42300;    //成为代理商需要缴纳多少钱。
	 public static final int AGENT_MANAGEMENT_TIER = 3;    //管理费上交几个代理商？
	 public static final int AGENT_MANAGEMENT_COST = 20;    //管理费 20元。
	 public static final int AGENT_RELATION_TIER = 100;    //向上查询的深度，默认100.深度100已经很恐怖了。
	 
	 //管理员手机号。用来在系统出问题时，通知管理员。
	 public static final String MANAGERMOBILE =  "18611080811";  //换成公号。
	 
	 
	 
	 public static void main(String[] args) {
	 }
	 
	 
	 public static Map<Integer, String> getArtType(){
		 Map<Integer, String> at = new HashMap<Integer, String>();
		 at.put(1, "书");
		 at.put(0,"维");
		 return at;
	 }
	 
	 public static Map<Integer, String> getWei_Lable(){
			Map<Integer,String> wl = new HashMap<Integer,String>();
			wl.put(1, "简介");
			wl.put(2, "作品");
			wl.put(3, "推荐");
			wl.put(4, "会员心得");
			wl.put(5, "公益");
			wl.put(6, "荐书");
			return wl;
	 }

	 
	 
	 public static Map<String, String> getBankMap(){
		 	Map<String,String> bankMap = new HashMap<String,String>();
		 	bankMap.put("ICBC_DEBIT","工商银行(借记卡)");
		 	bankMap.put("ICBC_CREDIT","工商银行(信用卡)");
		 	bankMap.put("ABC_DEBIT","农业银行(借记卡)");
		 	bankMap.put("ABC_CREDIT","农业银行(信用卡)");
		 	bankMap.put("PSBC_DEBIT","邮政储蓄(借记卡)");
		 	bankMap.put("PSBC_CREDIT","邮政储蓄(信用卡)");
		 	bankMap.put("CCB_DEBIT","建设银行(借记卡)");
		 	bankMap.put("CCB_CREDIT","建设银行(信用卡)");
		 	bankMap.put("CMB_DEBIT","招商银行(借记卡)");
		 	bankMap.put("CMB_CREDIT","招商银行(信用卡)");
		 	bankMap.put("COMM_DEBIT","交通银行(借记卡)");
		 	bankMap.put("BOC_CREDIT","中国银行(信用卡)");
		 	bankMap.put("SPDB_DEBIT","浦发银行(借记卡)");
		 	bankMap.put("SPDB_CREDIT","浦发银行(信用卡)");
		 	bankMap.put("GDB_DEBIT","广发银行(借记卡)");
		 	bankMap.put("GDB_CREDIT","广发银行(信用卡)");
		 	bankMap.put("CMBC_DEBIT","民生银行(借记卡)");
		 	bankMap.put("CMBC_CREDIT","民生银行(信用卡)");
		 	bankMap.put("PAB_DEBIT","平安银行(借记卡)");
		 	bankMap.put("PAB_CREDIT","平安银行(信用卡)");
		 	bankMap.put("CEB_DEBIT","光大银行(借记卡)");
		 	bankMap.put("CEB_CREDIT","光大银行(信用卡)");
		 	bankMap.put("CIB_DEBIT","兴业银行(借记卡)");
		 	bankMap.put("CIB_CREDIT","兴业银行(信用卡)");
		 	bankMap.put("CITIC_DEBIT","中信银行(借记卡)");
		 	bankMap.put("CITIC_CREDIT","中信银行(信用卡)");
		 	bankMap.put("SDB_CREDIT","深发银行(信用卡)");
		 	bankMap.put("BOSH_DEBIT","上海银行(借记卡)");
		 	bankMap.put("BOSH_CREDIT","上海银行(信用卡)");
		 	bankMap.put("CRB_DEBIT","华润银行(借记卡)");
		 	bankMap.put("HZB_DEBIT","杭州银行(借记卡)");
		 	bankMap.put("HZB_CREDIT","杭州银行(信用卡)");
		 	bankMap.put("BSB_DEBIT","包商银行(借记卡)");
		 	bankMap.put("BSB_CREDIT","包商银行(信用卡)");
		 	bankMap.put("CQB_DEBIT","重庆银行(借记卡)");
		 	bankMap.put("SDEB_DEBIT","顺德农商行(借记卡)");
		 	bankMap.put("SZRCB_DEBIT","深圳农商银行(借记卡)");
		 	bankMap.put("HRBB_DEBIT","哈尔滨银行(借记卡)");
		 	bankMap.put("BOCD_DEBIT","成都银行(借记卡)");
		 	bankMap.put("GDNYB_DEBIT","南粤银行(借记卡)");
		 	bankMap.put("GDNYB_CREDIT","南粤银行(信用卡)");
		 	bankMap.put("GZCB_CREDIT","广州银行(信用卡)");
		 	bankMap.put("JSB_DEBIT","江苏银行(借记卡)");
		 	bankMap.put("JSB_CREDIT","江苏银行(信用卡)");
		 	bankMap.put("NBCB_DEBIT","宁波银行(借记卡)");
		 	bankMap.put("NBCB_CREDIT","宁波银行(信用卡)");
		 	bankMap.put("NJCB_DEBIT","南京银行(借记卡)");
		 	bankMap.put("QDCCB_DEBIT","青岛银行(借记卡)");
		 	bankMap.put("ZJTLCB_DEBIT","浙江泰隆银行(借记卡)");
		 	bankMap.put("XAB_DEBIT","西安银行(借记卡)");
		 	bankMap.put("CSRCB_DEBIT","常熟农商银行(借记卡)");
		 	bankMap.put("QLB_DEBIT","齐鲁银行(借记卡)");
		 	bankMap.put("LJB_DEBIT","龙江银行(借记卡)");
		 	bankMap.put("HXB_DEBIT","华夏银行(借记卡)");
		 	bankMap.put("CS_DEBIT","测试银行借记卡快捷支付(借记卡)");
		 	bankMap.put("AE_CREDIT","AE(信用卡)");
		 	bankMap.put("JCB_CREDIT","JCB(信用卡)");
		 	bankMap.put("VISA_CREDIT","VISA(信用卡)");
		 	bankMap.put("MASTERCARD_CREDIT","MASTERCARD(信用卡)");
		 	bankMap.put("","不存在");
		 return bankMap;
	 }
	 
	 
}
