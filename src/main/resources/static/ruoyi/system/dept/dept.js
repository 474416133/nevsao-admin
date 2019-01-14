 /*部门管理-新增-选择父部门树*/
var domain = window.location.protocol+"//"+window.location.host;
function selectDeptTree() {
    var options = {
        title: '部门选择',
        width: "380",
        url: domain + "/system/dept/tree/view",
        callBack: doSubmit
    };
    $.modal.openOptions(options);
}

function doSubmit(index, layero){
    var body = layer.getChildFrame('body', index);
        $("#treeId").val(body.find('#treeId').val());
        $("#treeName").val(body.find('#treeName').val());
        layer.close(index);
}
