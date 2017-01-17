package com.wsh.base.dao;

import com.wsh.base.model.NewUserInfo;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.collections.functors.ExceptionClosure;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.*;
import java.util.*;

/**
 * Created by William on 2016/8/22.
 */
@Repository
public class ExpansionRepository extends DaoSupport {


    //一级会员数量
    public Long getAVIPs(Integer userId){
        String sql="select COUNT(1) from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ?  and uf.vipLv > 0";
        return jdbcTemplate.queryForObject(sql,Long.class,userId);
    }

    public Long getAFans(Integer userId){
        String sql="select COUNT(1) from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ? and uf.nickName is not null";
        return jdbcTemplate.queryForObject(sql,Long.class,userId);
    }


    public List<Map<String,String>> getAFansDetails(Integer userId){
        String sql="select nickname,vipLv,DATE_FORMAT(uf.createdTime,'%Y-%m-%d %H:%i:%s') as createdTime from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ?  " +
                "  and nickName is not null ORDER BY createdTime desc";
        List<Map<String,String>> temp=jdbcTemplate.query(sql,new Object[]{userId}, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                Map<String, String> m = new HashMap<String, String>();
                DefaultLobHandler lobHandler=new DefaultLobHandler();
                //Blob blob=rs.getBlob(1);
                //String nickname = new String(blob.getBytes(1L,(int)blob.length()));
                String nickname = "";
                try {
                    nickname= new String(lobHandler.getBlobAsBytes(rs, 1), "UTF-8");
                }catch (Exception e){
                    e.printStackTrace();
                }
                m.put("nickname", nickname);
                m.put("vipLv", rs.getString(2));
                m.put("createdTime",rs.getString("createdTime"));
                return m;
            }
        });
        return  temp;
    }



    //二级会员数量
    public Long getBVIPs(Integer userId){
        String sql="select COUNT(1) from generalizeinfo gf1 LEFT JOIN userinfo uf1 on gf1.userid=uf1.id where uf1.vipLv > 0  " +
                "and gf1.pId in (select userid from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ?  and  " +
                "uf.vipLv > 0 )";
       return jdbcTemplate.queryForObject(sql,Long.class,userId);
    }


    public Long getBFans(Integer userId){
        String sql="select COUNT(1) from generalizeinfo gf1 LEFT JOIN userinfo uf1 on gf1.userid=uf1.id where uf1.nickName is not null " +
                "   and gf1.pId in (select userid from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ?  and  " +
                "   uf.nickName is not null )";
        return jdbcTemplate.queryForObject(sql,Long.class,userId);
    }

    //三级会员数量
    public Long getCVIPs(Integer userId){
        String sql="select COUNT(1) from generalizeinfo gf2 LEFT JOIN userinfo uf2 on gf2.userid=uf2.id where uf2.vipLv > 0 " +
                "and gf2.pId in ( " +
                "select userid from generalizeinfo gf1 LEFT JOIN userinfo uf1 on gf1.userid=uf1.id where uf1.vipLv > 0 " +
                "and gf1.pId in (select userid from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ? and " +
                "uf.vipLv > 0)) ";
        return jdbcTemplate.queryForObject(sql,Long.class,userId);
    }

    public Long getCFans(Integer userId){
        String sql="select COUNT(1) from generalizeinfo gf2 LEFT JOIN userinfo uf2 on gf2.userid=uf2.id where uf2.nickName is not null  " +
                "and gf2.pId in ( " +
                "select userid from generalizeinfo gf1 LEFT JOIN userinfo uf1 on gf1.userid=uf1.id where uf1.nickName is not null  " +
                "and gf1.pId in (select userid from generalizeinfo gf LEFT JOIN userinfo uf on gf.userid=uf.id where gf.pId = ? and " +
                "uf.nickName is not null )) ";
        return jdbcTemplate.queryForObject(sql,Long.class,userId);
    }


    //绑定银行卡
    public void  bindBank(Map<String,String> params){
        jdbcTemplate.update("insert into userbankinfo (userid,bkNo,bkCode,subBkCode,bkUserName,bkMobile) values (?,?,?,?,?,?)" ,
                new Object[]{params.get("userId"),params.get("bkNo"),params.get("bkCode"),params.get("subBkCode"),params.get("bkUserName"),params.get("bkMobile")});
    }

    public Map<String,Object>  getBankByUserId(Integer userId){
        List<Map<String,Object>> temp=jdbcTemplate.queryForList("select * from userbankinfo where userid=? ",new Object[]{userId});
        if(temp!=null && !temp.isEmpty()){
            return temp.get(0);
        }
        return null;
    }


    //绑定收货地址
    public void  bindAddr(Map<String,String> params){
        jdbcTemplate.update("insert into useraddrinfo (userid,addrDetail,consignee ,zip,mobile,preAdd) values (?,?,?,?,?,?)" ,
                new Object[]{params.get("userId"),params.get("addrDetail"),params.get("consignee"),params.get("zip"),params.get("mobile"),params.get("preAdd")});
    }

    public Map<String,Object>  getAddrByUserId(Integer userId){
        List<Map<String,Object>> temp=jdbcTemplate.queryForList("select * from useraddrinfo where userid=? ",new Object[]{userId});
        if(temp!=null && !temp.isEmpty()){
            return temp.get(0);
        }
        return null;
    }


    public int getPId(Integer userId){
       List<Map<String,Object>> tmp=jdbcTemplate.queryForList("select pId from generalizeinfo where userid=?",new Object[]{userId});
        if(tmp!=null && !tmp.isEmpty()){
            return tmp.get(0).get("pId") == null ? 0 : Integer.parseInt(tmp.get(0).get("pId").toString());
        }
        return 0;

    }


    // 核对数据
   public List<Map<String,Object>> getAllVIPs(){
        return jdbcTemplate.queryForList("SELECT *,DATE_FORMAT(vipStartTime,'%Y-%m-%d %H:%i:%s') as time_ from userinfo where vipLv>0");
    }


    public List<Map<String,Object>> getNext(Integer pId){
        return jdbcTemplate.queryForList("SELECT u.*,DATE_FORMAT(u.vipStartTime,'%Y-%m-%d %H:%i:%s') as time_ from userinfo u,generalizeinfo g where g.userid=u.id and g.pId="+pId+" and u.vipLv>0");
    }


    public Integer getWallet(Integer userId){
       return jdbcTemplate.queryForObject("select count(*) from mywallet where cmType=3 and toUserId="+userId,Integer.class);
    }

    public Integer joinMember(final Map<String,String> params){

           Integer result=(Integer) jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                    String storedProc = "{call joinMembership(?,?,?,?)}";
                    CallableStatement cs = connection.prepareCall(storedProc);
                    int i=1;
                    cs.setString(i++,params.get("mobile"));
                    cs.setString(i++,params.get("pMobile"));
                    cs.setString(i++,params.get("name"));
                    cs.registerOutParameter(i++,Types.INTEGER);
                    return cs;
                }
            }, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                    callableStatement.execute();
                    return callableStatement.getInt(4);
                }
            });

        return 0;
    }


    public void insertTicket(Integer userId){
        jdbcTemplate.update("INSERT into ticket (userId,qrcode) VALUES (?,?)",new Object[]{userId,UUID.randomUUID()});
    }

    public void shown(Integer userId){
        jdbcTemplate.update("update  ticket set shown=1 where userId=?",new Object[]{userId});
    }

    public Map<String,Object> getTicketById(Integer userId){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("SELECT qrcode,shown from ticket where userId=?",new Object[]{userId});
        if(list!=null && !list.isEmpty()){
                return list.get(0);
        }
        return  null;
    }
}
