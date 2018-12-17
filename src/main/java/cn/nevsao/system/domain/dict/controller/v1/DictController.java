package cn.nevsao.system.domain.dict.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.domain.dict.entity.Dict;
import cn.nevsao.system.domain.dict.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class DictController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DictService dictService;

    @Log("获取字典信息")
    @RequestMapping("dict")
    @RequiresPermissions("dict:list")
    public String index() {
        return "system/dict/dict";
    }

    @RequestMapping("dict/list")
    @RequiresPermissions("dict:list")
    @ResponseBody
    public Map<String, Object> dictList(QueryRequest request, Dict dict) {
        return super.selectByPageNumSize(request, () -> this.dictService.all(dict, request));
    }

    @RequestMapping("dict/excel")
    @ResponseBody
    public ResponseBo dictExcel(Dict dict) {


        List<Dict> list = this.dictService.all(dict, null);
        return FileUtil.createExcelByPOIKit("字典表", list, Dict.class);
    }

    @RequestMapping("dict/csv")
    @ResponseBody
    public ResponseBo dictCsv(Dict dict) {

        List<Dict> list = this.dictService.all(dict, null);
        return FileUtil.createCsv("字典表", list, Dict.class);
    }

    @RequestMapping("dict/getDict")
    @ResponseBody
    public ResponseBo getDict(String dictId) {

        Dict dict = this.dictService.get(dictId);
        return ResponseBo.ok(dict);
    }

    @Log("新增字典 ")
    @RequiresPermissions("dict:add")
    @RequestMapping("dict/add")
    @ResponseBody
    public ResponseBo addDict(Dict dict) {

        this.dictService.insert(dict);
        return ResponseBo.ok("新增字典成功！");
    }

    @Log("删除字典")
    @RequiresPermissions("dict:delete")
    @RequestMapping("dict/delete")
    @ResponseBody
    public ResponseBo deleteDicts(String ids) {

        this.dictService.delete(ids);
        return ResponseBo.ok("删除字典成功！");
    }

    @Log("修改字典 ")
    @RequiresPermissions("dict:update")
    @RequestMapping("dict/update")
    @ResponseBody
    public ResponseBo updateDict(Dict dict) {

        this.dictService.update(dict);
        return ResponseBo.ok("修改字典成功！");
    }
}
