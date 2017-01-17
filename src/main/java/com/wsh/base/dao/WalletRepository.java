package com.wsh.base.dao;

import com.wsh.base.dao.DaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by William on 2016/8/10.
 */

@Repository
public class WalletRepository extends DaoSupport {

    public Long getTotalExpenditure(Integer userId){
        return jdbcTemplate.queryForObject("select abs(IFNULL(sum(money),0)) from mywallet where toUserId=? and money<0",new Object[]{userId},Long.class);
    }


    public Long getTotalIncome(Integer userId){
        //java 端转换处理
        return jdbcTemplate.queryForObject("select IFNULL(sum(money),0) from mywallet where toUserId=? and money>0",new Object[]{userId},Long.class);
    }

    public Long getBalance(Integer userId){
        return jdbcTemplate.queryForObject("select IFNULL(sum(money),0)/100 as sum from mywallet where toUserId=?",new Object[]{userId},Long.class);
    }

    public List<Map<String,Object>> getList(Integer pageNo,Integer userId){
        return jdbcTemplate.queryForList("select money ,createTime as tradingTime,sourceDes as text,month(createTime) as month,day(createTime) as day from mywallet where toUserId=? order by createTime desc limit 20 offset ? ",new Object[]{userId,(pageNo-1)*20});
    }

    public boolean enchashment(Integer userId,Integer money,String paymentNo){
        return jdbcTemplate.update("insert into mywallet(toUserId,money,epType,epChannel,channelTransNo,sourceDes) values (?,?,?,?,?,?)",new Object[]{userId,money,1,2,paymentNo,"Transfer To WeChat"})==1;
    }


    public List<Map<String,Object>> getIncome(Integer userId){
        return jdbcTemplate.queryForList("select * from mywallet where toUserId=? and money>0 and cmType<=2",new Object[]{userId});
    }

}
