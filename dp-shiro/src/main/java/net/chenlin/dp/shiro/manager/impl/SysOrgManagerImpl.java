package net.chenlin.dp.shiro.manager.impl;

import java.util.ArrayList;
import java.util.List;

import net.chenlin.dp.common.annotation.DataPermission;
import net.chenlin.dp.common.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.shiro.dao.SysOrgMapper;
import net.chenlin.dp.shiro.dao.SysRoleOrgMapper;
import net.chenlin.dp.shiro.entity.SysOrgEntity;
import net.chenlin.dp.shiro.manager.SysOrgManager;

/**
 * 组织架构
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月17日 上午11:32:15
 */
@Component("sysOrgManager")
public class SysOrgManagerImpl implements SysOrgManager {

	@Autowired
	private SysOrgMapper sysOrgMapper;
	
	@Autowired
	private SysRoleOrgMapper sysRoleOrgMapper;

	@Override
	public List<SysOrgEntity> listOrg() {
		return sysOrgMapper.list();
	}

	@Override
	public int saveOrg(SysOrgEntity org) {
		return sysOrgMapper.save(org);
	}

	@Override
	public SysOrgEntity getOrg(Long orgId) {
		return sysOrgMapper.getObjectById(orgId);
	}

	@Override
	public int updateOrg(SysOrgEntity org) {
		return sysOrgMapper.update(org);
	}

	@Override
	public int bactchRemoveOrg(Long[] id) {
		int count = sysOrgMapper.batchRemove(id);
		sysRoleOrgMapper.batchRemoveByOrgId(id);
		return count;
	}

	@Override
	public boolean hasChildren(Long[] id) {
		for(Long parentId : id) {
			int count = sysOrgMapper.countOrgChildren(parentId);
			if(CommonUtils.isIntThanZero(count)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Long> listOrgChildren(Long parentId) {
		return sysOrgMapper.listOrgChildren(parentId);
	}

	@Override
	public List<Long> getAllOrgChildren(Long parentId) {
		List<Long> orgIds = new ArrayList<>();
		List<Long> parentIds = listOrgChildren(parentId);
		recursionOrgChildren(parentIds, orgIds);
		return orgIds;
	}

	/**
	 * 递归查询子机构
	 * @param parentIds
	 * @param result
	 */
	public void recursionOrgChildren(List<Long> parentIds, List<Long> result) {
		for (Long parentId : parentIds) {
			List<Long> ids = listOrgChildren(parentId);
			if (ids.size() > 0) {
				recursionOrgChildren(ids, result);
			}
			result.add(parentId);
		}
	}

}
