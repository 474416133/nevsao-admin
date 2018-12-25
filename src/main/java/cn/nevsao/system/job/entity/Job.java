package cn.nevsao.system.job.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntity;
import com.google.common.base.MoreObjects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "task_job")
@Data
public class Job extends BaseEntity {

    private static final long serialVersionUID = 400066840871805700L;

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        private ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Column(name = "bean_name")
    @ExportConfig(value = "Bean名称")
    private String beanName;

    @Column(name = "method_name")
    @ExportConfig(value = "方法名称")
    private String methodName;

    @Column(name = "method_params")
    @ExportConfig(value = "参数")
    private String methodParams;

    @Column(name = "cron_expression")
    @ExportConfig(value = "cron表达式")
    private String cronExpression;

    @Column(name = "status")
    @ExportConfig(value = "状态", convert = "s:0=正常,1=暂停")
    private Integer status;

    @Column(name = "remark")
    @ExportConfig(value = "备注")
    private String remark;


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("beanName", beanName)
                .add("methodName", methodName)
                .add("params", methodParams)
                .add("cronExpression", cronExpression)
                .add("status", status)
                .add("remark", remark)
                .add("createTime", getCreateTime())
                .toString();
    }
}