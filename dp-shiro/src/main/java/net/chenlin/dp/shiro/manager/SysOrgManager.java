package net.chenlin.dp.shiro.manager;

import java.util.List;

import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.shiro.entity.SysOrgEntity;

/**
 * 组织机构
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月17日 上午11:31:59
 */
public interface SysOrgManager {

	List<SysOrgEntity> listOrg();
	
	int saveOrg(SysOrgEntity org);
	
	SysOrgEntity getOrg(Long orgId);
	
	int updateOrg(SysOrgEntity org);
	
	int bactchRemoveOrg(Long[] id);
	
	boolean hasChildren(Long[] id);

	List<Long> listOrgChildren(Long parentId);

	List<Long> getAllOrgChildren(Long parentId);
	
}
