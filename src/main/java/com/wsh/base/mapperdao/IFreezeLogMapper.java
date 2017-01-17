package com.wsh.base.mapperdao;


import com.wsh.base.model.FreezeWeiBi;

/**
 * 冻结流水日志 表。
 * @author summer
 */
public interface IFreezeLogMapper {

	/**
	 * 
	 * @Title instFreezeLogForUser
	 * @author Wally
	 * @time 2016年1月11日下午9:01:52
	 * @Description 冻结流水插入。
	 * @param
	 * @return int
	 */
	int instFreezeLogForUser(FreezeWeiBi fwBi);

}
