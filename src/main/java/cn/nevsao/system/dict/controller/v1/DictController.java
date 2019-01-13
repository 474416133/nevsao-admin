package cn.nevsao.system.dict.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.dict.entity.Dict;
import cn.nevsao.system.dict.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "system/")
public class DictController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String PATH_PREFIX = "system/dict/";
    @Autowired
    private DictService dictService;

    @Log("获取字典信息")
    @RequestMapping("dict")
    @RequiresPermissions("system:dict:list")
    public String index() {
        return PATH_PREFIX + "dict";
    }

    @RequestMapping("dict/list")
    @RequiresPermissions("system:dict:list")
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


    @RequiresPermissions("system:dict:add")
    @GetMapping("dict/add")
    public String addDictView(ModelMap mmap) {
        return PATH_PREFIX + "add";
    }

    @Log("新增字典 ")
    @RequiresPermissions("system:dict:add")
    @RequestMapping("dict/add")
    @ResponseBody
    public ResponseBo addDict(Dict dict) {

        this.dictService.insert(dict);
        return ResponseBo.ok("新增字典成功！");
    }

    @Log("删除字典")
    @RequiresPermissions("system:dict:delete")
    @RequestMapping("dict/delete")
    @ResponseBody
    public ResponseBo deleteDicts(String ids) {

        this.dictService.delete(ids);
        return ResponseBo.ok("删除字典成功！");
    }


    @RequiresPermissions("system:dict:update")
    @GetMapping("dict/update/{dictKey}")
    public String updateDictView(@PathVariable("dictKey") String dictKey, ModelMap mmap) {
        Dict dict = dictService.get(dictKey);
        mmap.put("dict", dict);
        return PATH_PREFIX + "update";
    }

    @Log("修改字典 ")
    @RequiresPermissions("system:dict:update")
    @RequestMapping("dict/update")
    @ResponseBody
    public ResponseBo updateDict(Dict dict) {

        this.dictService.updateExcludeNull(dict);
        return ResponseBo.ok("修改字典成功！");
    }
}
