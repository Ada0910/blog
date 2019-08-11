$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/about/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'aboutId', index: 'aboutId', width: 10, key: true, hidden: true},
            {label: '名称', name: 'aboutName', index: 'aboutName', width: 80},
            {label: '链接', name: 'aboutUrl', index: 'aboutUrl', width: 100},
            {label: '描述', name: 'aboutDescription', index: 'aboutDescription', width: 120},
            {label: '类型', name: 'aboutType', index: 'aboutType', width: 60},
            {label: '图片', name: 'aboutImage', index: 'aboutImage', width: 100,formatter: coverImageFormatter},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 60},
            {label: '更新时间', name: 'updateTime', index: 'updateTime', width: 60}
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

function addAbout() {
    reset();
    $('.modal-title').html('关于添加');
    $('#aboutModal').modal('show');
}

//绑定modal上的保存按钮
function saveButton() {
    $('.modal-title').html('添加');
    var aboutId = $("#aboutId").val();
    var aboutName = $("#aboutName").val();
    var aboutUrl = $("#aboutUrl").val();
    var aboutDescription = $("#aboutDescription").val();
    var aboutImage = $("#aboutImage").val();
    if (isNull(aboutName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("名称不规范(不能为空)！");
        return;
    }
    if (!isURL(aboutUrl)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("链接不规范！");
        return;
    }
    if (isNull(aboutDescription)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("描述不规范(不能为空)！");
        return;
    }
   /* if (isNull(aboutImage)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("图片URL不规范(不能为空)！");
        return;
    }*/
    var params = $("#aboutForm").serialize();
    var url = '/admin/about/add';
    if (aboutId != null && aboutId > 0) {
        url = '/admin/about/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200 && result.data) {
                $('#aboutModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#aboutModal').modal('hide');
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

function editAbout() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    reset();
    //请求数据
    $.get("/admin/about/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#aboutName").val(r.data.aboutName);
            $("#aboutUrl").val(r.data.aboutUrl);
            $("#aboutDescription").val(r.data.aboutDescription);
            $("#aboutImage").val(r.data.aboutImage);
            //根据原aboutType值设置select选择器为选中状态
            if (r.data.aboutType == 1) {
                $("#aboutType option:eq(1)").prop("selected", 'selected');
            }
            if (r.data.aboutType == 2) {
                $("#aboutType option:eq(2)").prop("selected", 'selected');
            }
            if (r.data.aboutType == 3) {
                $("#aboutType option:eq(3)").prop("selected", 'selected');
            }
        }
    });
    $('.modal-title').html('修改');
    $('#aboutModal').modal('show');
    $("#aboutId").val(id);
}

function deleteAbout() {
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
                url: "/admin/about/delete",
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


function reset() {
    $("#aboutName").val('');
    $("#aboutUrl").val('');
    $("#aboutDescription").val('');
    $("#aboutRank").val(0);
    $('#edit-error-msg').css("display", "none");
    $("#aboutType option:first").prop("selected", 'selected');
}

/*图片格式*/
function coverImageFormatter(cellvalue) {
    return "<img src='" + cellvalue + "' height=\"120\" width=\"160\" alt='coverImage'/>";
}

/**上传图片*/
$(function () {
    new AjaxUpload('#uploadAboutImage', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#aboutImage").attr("src", r.data);
                $("#aboutImage").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});