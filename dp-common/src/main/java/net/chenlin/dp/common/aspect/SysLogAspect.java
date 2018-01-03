package net.chenlin.dp.common.aspect;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.entity.SysLogEntity;
import net.chenlin.dp.common.manager.SysLogManager;
import net.chenlin.dp.common.utils.IpUtils;
import net.chenlin.dp.common.utils.JSONUtils;
import net.chenlin.dp.common.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 * 
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月14日 下午8:00:45
 */
@Aspect
@Component
public class SysLogAspect {
	
	@Autowired
	private SysLogManager sysLogManager;
	
	@Pointcut("@annotation(net.chenlin.dp.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	/**
	 * 操作异常日志
	 * @param joinPoint
	 * @param ex
	 */
	@AfterThrowing(pointcut = "logPointCut()", throwing = "ex")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLogEntity sysLog = new SysLogEntity();
		sysLog.setType(SystemConstant.LogType.ERROR.getValue());

		SysLog sysLogAnnotation = method.getAnnotation(SysLog.class);
		if(sysLogAnnotation != null){
			//注解上的描述
			sysLog.setOperation(sysLogAnnotation.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONUtils.beanToJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//设置IP地址
		sysLog.setIp(IpUtils.getIpAddr());

		//用户名
		sysLog.setUserId(ShiroUtils.getUserId());
		sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());

		//操作状态和结果
		sysLog.setResult(SystemConstant.StatusType.DISABLE.getValue());
		sysLog.setRemark("异常信息：" + ex.getMessage());

		sysLogManager.saveLog(sysLog);
	}

	/**
	 * 操作日志
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = joinPoint.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLogEntity sysLog = new SysLogEntity();

		SysLog sysLogAnnotation = method.getAnnotation(SysLog.class);
		if(sysLogAnnotation != null){
			//注解上的描述
			sysLog.setOperation(sysLogAnnotation.value());
			String type = sysLogAnnotation.type();
			if (StringUtils.isNotEmpty(type)) {
				sysLog.setType(SystemConstant.LogType.valueOf(type).getValue());
			} else {
				sysLog.setType(SystemConstant.LogType.OPERATION.getValue());
			}
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONUtils.beanToJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//设置IP地址
		sysLog.setIp(IpUtils.getIpAddr());

		//用户名
		sysLog.setUserId(ShiroUtils.getUserId());
		sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());

		//操作执行状态
		if (result instanceof R) {
			R r = (R) result;
			int code = (int) r.get("code");
			if(code == 0) {
				//操作成功
				sysLog.setResult(SystemConstant.StatusType.ENABLE.getValue());

				//响应时间：ms
				sysLog.setRemark("响应时间：" + time + "ms");
			} else {
				sysLog.setResult(SystemConstant.StatusType.DISABLE.getValue());
				sysLog.setRemark(String.valueOf(r.get("msg")));
			}
		}

		//保存系统日志
		sysLogManager.saveLog(sysLog);
		return result;
	}

}
