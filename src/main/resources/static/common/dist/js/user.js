$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/user/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'adminUserId', index: 'adminUserId', width: 10, key: true, hidden: true},
            {label: '登录名字', name: 'loginUserName', index: 'loginUserName', width: 80},
            // {label: '登录密码', name: 'loginPassword', index: 'loginPassword', width: 80},
            {label: '绰号', name: 'nickName', index: 'nickName', width: 60},
            {label: '是否锁定', name: 'locked', index: 'locked', width: 20, formatter: isLock},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 60},
            {label: '更新时间', name: 'updateTime', index: 'updateTime', width: 60},
            {label: '授权', name: '', index: '', width: 20,formatter: bindUser}
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

/**类型*/
function isLock(cellvalue) {
    if (cellvalue == 1) {
        return "<button type=\"button\" class=\"btn btn-block btn-info btn-sm\" style=\"background-color:#dc3545;\">锁定</button>";
    } else {
        return "<button type=\"button\" class=\"btn btn-block  btn-info btn-sm\" style=\"background-color:#0cb842;\">可用</button>";
    }
}

/**绑定用户*/
function  bindUser() {
    return "<button type=\"button\" class=\"btn btn-block btn-info btn-sm\" style=\"background-color:#9094dc;\">绑定角色</button>";
}

/*重置*/
function reset() {
    $("#loginUserName").val('');
    $("#password").val('');
    $("#nickName").val('');
}

/**添加用户*/
function addUser() {
    reset();
    $('.modal-title').html('添加用户');
    $('#userModal').modal('show');
}

//绑定modal上的保存按钮
function saveButton() {
    $('.modal-title').html('添加');
    var userId = $("#userId").val();
    var loginUserName = $("#loginUserName").val();
    var loginPassword = $("#loginPassword").val();
    var nickName = $("#nickName").val();
    if (isNull(loginUserName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("用户名不能为空！");
        return;
    }
    if (isNull(loginPassword)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("密码不能为空！");
        return;
    }
    params = {
        "adminUserId": userId,
        "loginUserName": loginUserName,
        "loginPassword": loginPassword,
        "nickName": nickName,
    };
    var url = '/admin/user/add';

    if (userId != null && userId > 0) {
        url = '/admin/user/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        dataType:"json",
        success: function (result) {
            if (result.resultCode == 200) {
                $('#userModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#userModal').modal('hide');
                swal("保存失败", {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });

}


/**修改*/
function editUser() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    reset();
    //请求数据
    $.get("/admin/user/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#loginUserName").val(r.data.loginUserName);
            $("#loginPassword").val(r.data.loginPassword);
            $("#nickName").val(r.data.nickName);
        }
    });
    $('.modal-title').html('修改');
    $('#userModal').modal('show');
    $("#userId").val(id);
}

/**删除*/
function deleteUser() {
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
                url: "/admin/user/delete",
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




