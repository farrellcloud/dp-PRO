package net.chenlin.dp.shiro.dao;

import org.mybatis.spring.annotation.MapperScan;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.shiro.entity.SysOrgEntity;

import java.util.List;

/**
 * 组织架构
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月17日 上午11:26:05
 */
@MapperScan
public interface SysOrgMapper extends BaseMapper<SysOrgEntity> {

	int countOrgChildren(Long parentId);

	List<Long> listOrgChildren(Long parentId);
	
}
