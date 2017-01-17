package com.wsh.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Andy_Liu on 2016/12/20.
 */

@Repository
public class LocationDao {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public  void  addAddress(Map<String,String> params){
        //TODO 用于统计分析
        jdbcTemplate.update("insert into location(openId,province,city) values (?,?,?)",
                new Object[]{params.get("openId"),params.get("province"),params.get("city")});
    }
}
