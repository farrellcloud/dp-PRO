package net.chenlin.dp.shiro.aspect;

import net.chenlin.dp.common.annotation.DataPermission;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.entity.SysUserEntity;
import net.chenlin.dp.common.exception.RRException;
import net.chenlin.dp.common.utils.ShiroUtils;
import net.chenlin.dp.shiro.manager.SysOrgManager;
import net.chenlin.dp.shiro.manager.SysUserManager;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据权限切面处理
 * @author ZhouChenglin
 * @date 2018/2/7
 */
@Aspect
@Component
public class DataPermissionAspect {

    @Autowired
    private SysUserManager sysUserManager;

    @Autowired
    private SysOrgManager sysOrgManager;

    @Pointcut("@annotation(net.chenlin.dp.common.annotation.DataPermission)")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        Map param = (Map)joinPoint.getArgs()[0];
        if (param != null && param instanceof Map) {
            SysUserEntity user = ShiroUtils.getUserEntity();
            Long userOrgId = user.getOrgId();
            // 系统管理员不进行数据权限处理
            if (user.getUserId() == SystemConstant.SUPER_ADMIN) {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                DataPermission dataPermission = signature.getMethod().getAnnotation(DataPermission.class);

                List<Long> orgIds = new ArrayList<>();
                // 用户本身机构
                orgIds.add(userOrgId);
                // 用户角色机构
                List<Long> roleOrgIds = sysUserManager.listAllOrgId(user.getUserId());
                if (roleOrgIds.size() > 0) {
                    orgIds.addAll(roleOrgIds);
                }
                // 用户机构所有子机构
                if (dataPermission.sub()) {
                    orgIds.addAll(sysOrgManager.getAllOrgChildren(userOrgId));
                }

                String tableAlias = dataPermission.alias();
                if (StringUtils.isNotBlank(tableAlias)) {
                    tableAlias += ".";
                }

                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append(" (");
                sqlBuilder.append(tableAlias).append("org_id in(").append(StringUtils.join(orgIds,",")).append(")");

                if (dataPermission.user()) {
                    sqlBuilder.append(" or ").append(tableAlias).append("user_id=").append(user.getUserId());
                }
                sqlBuilder.append(")");
                param.put(SystemConstant.DATA_PERMISSION, sqlBuilder.toString());
            }
        } else {
            throw new RRException("数据权限接口参数必须为Map类型");
        }
    }

}
