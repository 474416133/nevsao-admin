package cn.nevsao.job.domain;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntity;
import com.google.common.base.MoreObjects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_job")
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

    @Column(name = "BEAN_NAME")
    @ExportConfig(value = "Bean名称")
    private String beanName;

    @Column(name = "METHOD_NAME")
    @ExportConfig(value = "方法名称")
    private String methodName;

    @Column(name = "PARAMS")
    @ExportConfig(value = "参数")
    private String params;

    @Column(name = "CRON_EXPRESSION")
    @ExportConfig(value = "cron表达式")
    private String cronExpression;

    @Column(name = "STATUS")
    @ExportConfig(value = "状态", convert = "s:0=正常,1=暂停")
    private String status;

    @Column(name = "REMARK")
    @ExportConfig(value = "备注")
    private String remark;


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("beanName", beanName)
                .add("methodName", methodName)
                .add("params", params)
                .add("cronExpression", cronExpression)
                .add("status", status)
                .add("remark", remark)
                .add("createTime", getCreateTime())
                .toString();
    }
}