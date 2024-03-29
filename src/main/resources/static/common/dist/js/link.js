$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/link/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'linkId', index: 'linkId', width: 50, key: true, hidden: true},
            {label: '网站名称', name: 'linkName', index: 'linkName', width: 100},
            {label: '网站链接', name: 'linkUrl', index: 'linkUrl', width: 120},
            {label: '网站描述', name: 'linkDescription', index: 'linkDescription', width: 120},
            {label: '友链类型', name: 'linkType', index: 'linkType', width: 50,formatter: linkTypeFormatter},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 100}
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

function linkTypeFormatter(cellvalue) {
    if (cellvalue == 1) {
        return "<button type=\"button\" class=\"btn btn-block btn-info btn-sm\" style=\"width: 80%;background-color:#28a745;\">推荐社区</button>";
    }
    else if (cellvalue == 2) {
        return "<button type=\"button\" class=\"btn btn-block  btn-info btn-sm\" style=\"width: 80%;background-color:#6c757d;\">推荐网站</button>";
    }else{
        return "<button type=\"button\" class=\"btn btn-block  btn-info btn-sm\" style=\"width: 80%;\">友情链接</button>";
    }
}
/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function linkAdd() {
    reset();
    $('.modal-title').html('友情链接添加');
    $('#linkModal').modal('show');
}

//绑定modal上的保存按钮
function saveButton(){
/*$('#saveButton').onclick(function () {*/
    $('.modal-title').html('斤斤计较');
    var linkId = $("#linkId").val();
    var linkName = $("#linkName").val();
    var linkUrl = $("#linkUrl").val();
    var linkDescription = $("#linkDescription").val();
    if (!validCN_ENString2_100(linkName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("名称不规范(2-100位的中英文字符串)！");
        return;
    }
    if (!isURL(linkUrl)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("网址不规范！");
        return;
    }
    if (!validCN_ENString2_100(linkDescription)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("描述不规范(2-100位的中英文字符串)！");
        return;
    }

    var params = $("#linkForm").serialize();
    var url = '/admin/link/add';
    if (linkId != null && linkId > 0) {
        url = '/admin/link/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200 && result.data) {
                $('#linkModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#linkModal').modal('hide');
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
function linkEdit() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    reset();
    //请求数据
    $.get("/admin/link/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#linkName").val(r.data.linkName);
            $("#linkUrl").val(r.data.linkUrl);
            $("#linkDescription").val(r.data.linkDescription);
            //根据原linkType值设置select选择器为选中状态
            if (r.data.linkType == 1) {
                $("#linkType option:eq(1)").prop("selected", 'selected');
            }
            if (r.data.linkType == 2) {
                $("#linkType option:eq(2)").prop("selected", 'selected');
            }
            if (r.data.linkType == 0) {
                $("#linkType option:eq(0)").prop("selected", 'selected');
            }
        }
    });
    $('.modal-title').html('友链修改');
    $('#linkModal').modal('show');
    $("#linkId").val(id);
}

function deleteLink() {
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
    }).then((flag)=>{
        if(flag) {
            $.ajax({
                type: "POST",
                url: "/admin/link/delete",
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
);

}

function reset() {
    $("#linkName").val('');
    $("#linkUrl").val('');
    $("#linkDescription").val('');
    $('#edit-error-msg').css("display", "none");
    $("#linkType option:first").prop("selected", 'selected');
}