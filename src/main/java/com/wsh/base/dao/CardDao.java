package com.wsh.base.dao;

import com.wsh.base.model.Card;
import com.wsh.base.util.dateUtil.DateUtil;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy_Liu on 2016/11/26.
 */

@Repository
public class CardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public  boolean validate(Card card){
        List<Map<String, Object>> list=jdbcTemplate.queryForList("select * from card where num=? and keyt =? and valid=1",new Object[]{card.getNum(),card.getKeyt()});
        return  list!=null && !list.isEmpty();
    }


    public  boolean joinMembership(final  Card card){
        final JdbcTemplate temp=jdbcTemplate;
        PlatformTransactionManager platformTransactionManager=new DataSourceTransactionManager(temp.getDataSource());
        TransactionTemplate transactionTemplate=new TransactionTemplate(platformTransactionManager);
        return (Boolean)transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    int rows0=0,rows1=0,rows2=0;
                    rows0=temp.update("update card set valid=0,`owner`=?  where num=? and keyt =? ", new Object[]{card.getOwner(), card.getNum(), card.getKeyt()});
                    Date now = new Date();
                    Date vipEndDate = DateUtil.addYear(now, 1);

                    String sql="update userinfo set vipLv=1,mobile=?,vipStartTime=?,vipEndTime=? where id=?";
                    rows2=temp.update(sql,new Object[]{card.getOwnerMobile(),now,vipEndDate,card.getOwner()});

                    if(card.getpId()!=null && card.getpId()>0 ){
                        rows1=temp.update("update generalizeinfo set pId=?,isElchee=1 where userid=? ", new Object[]{card.getpId(),card.getOwner()});
                    }
                    if(rows1!=1 || rows0!=1 || rows2!=1){
                        transactionStatus.setRollbackOnly();
                        return false;
                    }
                }catch (Exception e){
                    transactionStatus.setRollbackOnly();
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
    }


    public  boolean renewal(final  Card card){
        final JdbcTemplate temp=jdbcTemplate;
        PlatformTransactionManager platformTransactionManager=new DataSourceTransactionManager(temp.getDataSource());
        TransactionTemplate transactionTemplate=new TransactionTemplate(platformTransactionManager);
        return (Boolean)transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    int rows0=0,rows1=0;
                    rows0=temp.update("update card set valid=0,`owner`=?  where num=? and keyt =? ", new Object[]{card.getOwner(), card.getNum(), card.getKeyt()});
                    Date now = new Date();
                    Date vipEndDate = DateUtil.addYear(now, 1);
                    rows1=temp.update("update userinfo set vipEndTime=timestampadd(YEAR,1,vipEndTime) where id= ?", new Object[]{card.getOwner()});
                    if(rows1!=1 || rows0!=1){
                        transactionStatus.setRollbackOnly();
                        return false;
                    }
                }catch (Exception e){
                    transactionStatus.setRollbackOnly();
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
    }

}
