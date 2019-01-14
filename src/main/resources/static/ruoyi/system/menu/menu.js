/*菜单管理-新增-选择菜单树*/
var domain = window.location.protocol+"//"+window.location.host;

function selectMenuTree() {
    var treeId = $("#treeId").val();
    var menuId = treeId > 0 ? treeId : 1;
    var url = domain + "/system/menu/tree/view";
    var options = {
        title: '菜单选择',
        width: "380",
        url: url,
        callBack: doSubmit,
        idKey: "id",
        pIdKey: "parentId"
    };
    $.modal.openOptions(options);
}

function doSubmit(index, layero){
    var body = layer.getChildFrame('body', index);
    $("#treeId").val(body.find('#treeId').val());
    $("#treeName").val(body.find('#treeName').val());
    layer.close(index);
}