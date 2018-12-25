package cn.nevsao.system.dict.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_dict")
@Data
public class Dict extends BaseEntityExtra {

    private static final long serialVersionUID = 7780820231535870010L;

    @Column(name = "dict_key")
    @ExportConfig(value = "字典Key")
    private String dictKey;

    @Column(name = "dict_value")
    @ExportConfig(value = "字典Value")
    private String dictValue;

    @Column(name = "table_name")
    @ExportConfig(value = "列名")
    private String tableName;

    @Column(name = "column_name")
    @ExportConfig(value = "表名")
    private String columnName;


}