package com.wsh.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsh.base.mapperdao.IPopConsumeMapper;
import com.wsh.base.mapperdao.IWBInfoMapper;
import com.wsh.base.model.WeibiLog;
import com.wsh.base.service.IUserWBService;

@Service
public class UserWBServiceImpl implements IUserWBService {

	@Autowired
	private IWBInfoMapper wbInfoMapper;  //充值维币流水表。包括增加和支出 
	
	@Autowired
	private IPopConsumeMapper popConsumeMapper; //推广收入类型的维币 消费记录。
	
	
	//查询 充值类型 的 剩余维币。日期 倒序 查找第一条。
	@Override
	public Integer toSearchMyWb(Integer userid) {
		Integer spwb = wbInfoMapper.toSearchMyWb(userid);
		return  (null == spwb ? 0:spwb);
	}
	
	//查询：推广类型的     消费维币；【剩余维币，还需要根据 ：总收入-总消费】
	@Override
	public Integer toSearchMy_PopWb(Integer userid){
		Integer popspwb = popConsumeMapper.toSearchMyPopWb(userid);
		return  (null == popspwb ? 0:popspwb);
	}
	
	/**
	 * 
	 * @Title newToSearchMySpWB
	 * @author Wally
	 * @time 2016年2月1日下午12:46:21
	 * @Description 新的计算维币剩余。等于各类型的维币收入-各类型的维币开销。 计算综合返回。
	 * @param
	 * @return Integer
	 */
	public Integer newToSearchMySpWB(Integer userid){
		//充值类型的 维币-不可提现，但是可以用于其他消费。所以还是需要计算出来。
		Integer czSpwb = toSearchMyWb(userid);  //充值渠道 的剩余维币。
		
		return null;
	}
	

	
	

	//查询用户的 维币流水 日志。
	@Override
	public List<WeibiLog> getWBLogInfoByUserId(Integer userid) {
		return wbInfoMapper.getWBLogInfoByUserId(userid);
	}


	
	@Override
	public Integer findAllWbIncomeByUid(Integer userid, Integer chanel, Integer dateNum) {
		return wbInfoMapper.findAllPopWbByidAndTime(userid,chanel,dateNum);
	}

}
