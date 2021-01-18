$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/role/getRoleList',
        datatype: "json",
        colModel: [
            {label: 'role_id', name: 'roleId', index: 'roleId', width: 10, key: true, hidden: true},
            {label: '角色名字', name: 'roleName', index: 'roleName', width: 80},
            {label: '角色描述', name: 'roleDesc', index: 'roleDesc', width: 60},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 60},
            {label: '更新时间', name: 'updateTime', index: 'updateTime', width: 60},
            {label: '授权', name: '', index: '', width: 20,formatter: bindPerms}
        ],
        rowNum: 10,
        height: 560,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 5,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currentPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}


/**绑定权限*/
function  bindPerms() {
    return "<button type=\"button\" class=\"btn btn-block btn-info btn-sm\" style=\"background-color:#9094dc;\">绑定权限</button>";
}

/*重置*/
function reset() {
    $("#roleName").val('');
    $("#roleDesc").val('');
}

/**添加用户*/
function toAddRolePage() {
    reset();
    $('.modal-title').html('添加角色');
    $('#roleModal').modal('show');
}

//绑定modal上的保存按钮
function saveRole() {
    $('.modal-title').html('添加角色');
    var roleId = $("#roleId").val();
    var roleName = $("#roleName").val();
    var roleDesc = $("#roleDesc").val();
    if (isNull(roleName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("角色名称不能为空！");
        return;
    }
    params = {
        "roleId": roleId,
        "roleName": roleName,
        "roleDesc": roleDesc,
    };
    var url = '/admin/role/add';

    if (roleId != null && roleId > 0) {
        url = '/admin/role/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        dataType:"json",
        success: function (result) {
            if (result.resultCode == 200) {
                $('#roleModal').modal('hide');
                swal("保存成功", {  icon: "success",});
                reload();
            } else {
                $('#roleModal').modal('hide');
                swal("保存失败", { icon: "error",  });
            };
            $("#roleId").val("");
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
            $("#roleId").val("");
        }
    });

}


/**修改*/
function toEditRolePage() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    reset();
    //请求数据
    $.get("/admin/role/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#roleName").val(r.data.roleName);
            $("#roleDesc").val(r.data.roleDesc);
        }
    });
    $('.modal-title').html('修改角色');
    $('#roleModal').modal('show');
    $("#roleId").val(id);
}

/**删除*/
function deleteRole() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if(flag) {
            $.ajax({
                type: "POST",
                url: "/admin/role/delete",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                }
            });
        }
    }
)

}




