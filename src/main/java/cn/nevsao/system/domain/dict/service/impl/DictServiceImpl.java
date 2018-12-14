package cn.nevsao.system.domain.dict.service.impl;

import cn.nevsao.common.domain.QueryRequest;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.system.domain.dict.entity.Dict;
import cn.nevsao.system.domain.dict.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.List;

@Service("dictService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class DictServiceImpl extends ExtraService<Dict> implements DictService {


    @Override
    public List<Dict> all(Dict dict, QueryRequest request) {
        try {
            Example example = new Example(Dict.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(dict.getDictKey())) {
                criteria.andCondition("dict_key=", dict.getDictKey());
            }
            if (StringUtils.isNotBlank(dict.getDictValue())) {
                criteria.andCondition("dict_value=", dict.getDictValue());
            }
            if (StringUtils.isNotBlank(dict.getTableName())) {
                criteria.andCondition("table_name=", dict.getTableName());
            }
            if (StringUtils.isNotBlank(dict.getColumnName())) {
                criteria.andCondition("column_name=", dict.getColumnName());
            }
            example.setOrderByClause("id");
            return this.findByExample(example);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return new ArrayList<>();
        }
    }


}
