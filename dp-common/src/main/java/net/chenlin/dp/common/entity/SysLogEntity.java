package net.chenlin.dp.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 系统日志
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月14日 下午8:05:17
 */
public class SysLogEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志id
	 */
	private Long id;

	/**
	 * 日志类型 1-登录 2-访问 3-操作 4-异常 5-授权
	 */
	private Integer type;

	/**
	 * 操作用户id
	 */
	private Long userId;
	
	/**
	 * 操作用户
	 */
	private String username;
	
	/**
	 * 操作
	 */
	private String operation;
	
	/**
	 * 方法
	 */
	private String method;
	
	/**
	 * 参数
	 */
	private String params;

	/**
	 * 操作ip地址
	 */
	private String ip;

	/**
	 * 操作结果 1-成功 2-失败
	 */
	private Integer result;

	/**
	 * 操作描述
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;

	public SysLogEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}
