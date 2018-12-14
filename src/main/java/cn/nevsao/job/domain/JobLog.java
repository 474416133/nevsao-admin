package cn.nevsao.job.domain;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_job_log")
@Data
public class JobLog extends BaseEntity {

	private static final long serialVersionUID = -7114915445674333148L;


	@Column(name = "job_id")
	@ExportConfig("任务ID")
	private String jobId;

	@Column(name = "bean_name")
	@ExportConfig(value = "Bean名称")
	private String beanName;

	@Column(name = "method_name")
	@ExportConfig(value = "方法名称")
	private String methodName;

	@Column(name = "method_params")
	@ExportConfig(value = "参数")
	private String params;

	@Column(name = "ret_status")
	@ExportConfig(value = "状态", convert = "s:0=成功,1=失败")
	private String status;

	@Column(name = "ret_remark")
	@ExportConfig(value = "异常信息")
	private String error;

	@Column(name = "period_time")
	@ExportConfig(value = "耗时（毫秒）")
	private Long periodTime;

}