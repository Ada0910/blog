$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/version/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'versionId', index: 'versionId', width: 10, key: true, hidden: true},
            {label: '版本号', name: 'versionSerialNum', index: 'versionSerialNum', width: 80},
            {label: '类型', name: 'versionType', index: 'versionType', width: 60, formatter: versionTypeFormatter},
            {label: '描述', name: 'versionDescription', index: 'versionDescription', width: 120},
            {label: '发布时间', name: 'versionCreateTime', index: 'createTime', width: 60},
            {label: '修改时间', name: 'versionUpdateTime', index: 'updateTime', width: 60}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
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
function versionTypeFormatter(cellvalue) {
    if (cellvalue == 0) {
        return "<button type=\"button\" class=\"btn btn-block btn-info btn-sm\" style=\"width: 80%;background-color:#c82333;\">BUG修改</button>";
    } else if (cellvalue == 1) {
        return "<button type=\"button\" class=\"btn btn-block  btn-info btn-sm\" style=\"width: 80%;background-color:green;\">添加新功能</button>";
    } else {
        return "<button type=\"button\" class=\"btn btn-block  btn-info btn-sm\" style=\"width: 80%;background-color:blue;\">其他</button>";
    }
}

function addVersion() {
    reset();
    $('.modal-title').html('版本添加');
    $('#versionModal').modal('show');
}

//绑定modal上的保存按钮
function saveButton() {
    $('.modal-title').html('添加');
    var versionId = $("#versionId").val();
    var versionSerialNum = $("#versionSerialNum").val();
    var versionDescription = $("#versionDescription").val();
    var versionType = $("#versionType").val();
    if (isNull(versionSerialNum)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("版本号不规范(不能为空)！");
        return;
    }
    params= {
        "versionId":versionId,
        "versionSerialNum":versionSerialNum,
        "versionType":versionType,
        "versionDescription":versionDescription,
    };
    var url = '/admin/version/add';
    if (versionId != null && versionId > 0) {
        url = '/admin/version/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200 && result.data) {
                $('#versionModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#versionModal').modal('hide');
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

function editVersion() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    reset();
    //请求数据
    $.get("/admin/version/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#versionSerialNum").val(r.data.versionSerialNum);
            $("#versionDescription").val(r.data.versionDescription);
            //根据原versionType值设置select选择器为选中状态
            if (r.data.versionType == 0) {
                $("#versionType option:eq(0)").prop("selected", 'selected');
            }
            if (r.data.versionType == 1) {
                $("#versionType option:eq(1)").prop("selected", 'selected');
            }
            if (r.data.versionType == 2) {
                $("#versionType option:eq(2)").prop("selected", 'selected');
            }
        }
    });
    $('.modal-title').html('修改');
    $('#versionModal').modal('show');
    $("#versionId").val(id);
}

function deleteVersion() {
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
                url: "/admin/version/delete",
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

/*重置*/
function reset() {
    $("#versionSerialNum").val('');
    $("#versionDescription").val('');
    $("#versionType option:first").prop("selected", 'selected');
}


