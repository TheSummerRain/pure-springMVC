package com.wsh.base.mapperdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wsh.base.model.GeneralizeInfo;
import com.wsh.base.model.UserInfo;

/**
 * 
 * @package : com.wsh.base.mapperdao
 * @ClassName: IGeneralizeInfoMapper
 * @Description: 用户关系表 的mapper.三级分销。
 * @author Wally
 * @date 2016年12月12日 下午3:17:46
 * @modify Wally
 * @modifyDate 2016年12月12日 下午3:17:46
 */

public interface IGeneralizeInfoMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(GeneralizeInfo record);

    int insertSelective(GeneralizeInfo record);

	int addRecordBatch(List<GeneralizeInfo> records);

    GeneralizeInfo selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(GeneralizeInfo record);

    int updateByPrimaryKey(GeneralizeInfo record);
    
    
    
    //判断某个ID，是否有下线。
    @Select(" select count(1) from generalizeinfo where pId = #{pId}")
    int checkHasChild(@Param("pId")Integer pId);
    
    /**
	 * 
	 * @Title 佣金返钱，管理费返钱，推广费用返钱会用这里。
	 * @author Wally
	 * @time 2016年12月17日上午10:36:55
	 * @Description 查询节点。upOrDown=1；向下查询子节点。=2向上查询父节点。
	 * @param level = 查询层数。
	 * @return String
	 */
	String selectFatherNew(@Param("userid") int userid,@Param("level")int level,@Param("upOrDown")int upOrDown);
	
	

	/**
	 * 
	 * @Title selectPreUser
	 * @author Wally
	 * @time 2016年12月17日上午10:51:38
	 * @Description 查询上级的所有关系数据。因为涉及到推广费，管理费，所以这里，必须保证关系表的上下级中，都是会员。也就是userinfo的vipLv>0
	 * @param lvc-查询高度（上层）
	 * @return List<GeneralizeInfo>
	 */
	List<GeneralizeInfo> selectPreUser(@Param("list") List<Integer> list,@Param("lvc")int lvc);
	
	
	
	
	
	
	
	
	
}