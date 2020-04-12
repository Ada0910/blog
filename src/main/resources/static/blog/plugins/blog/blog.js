/**跳转到上一篇文章*/
function getPreArticle() {
    var blogId = Number($("#blogId").val());
    var id;
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/getBlogIdList',
        success: function (result) {
            for (var i = 0; i < result.length; i++) {
                if (blogId == result[i]) {
                    id = i;
                }
            }
            if (id == 0) {
                swal("已经是第一篇!!!");
            } else {
                window.location.href = "/blog/" + Number(result[id - 1]);
            }

        },
    });
}

/**跳转到下一篇文章*/
function getNextArticle() {
    var blogId = Number($("#blogId").val());
    var id;
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/getBlogIdList',
        success: function (result) {
            for (var i = 0; i < result.length; i++) {
                if (blogId == result[i]) {
                    id = i;
                }
            }
            if (id == result.length - 1) {
                swal("这已经是最后一篇!!!");
            } else {
                window.location.href = "/blog/" + Number(result[id + 1]);
            }

        },
    });
}

/**下载pdf到本地*/
function pdfDownload() {
    var blogId = $("#blogId").val();
    window.location.href = '/blog/pdfDownload/' + blogId;

}

/**分享到微信*/
function shareToWeChat() {
    var blogId = Number($("#blogId").val());
    var content = "http://www.isada.cn/blog/" + blogId;
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/shareToWeChat',
        data: {
            content: content,
            blogId: blogId
        },
        dataType: "json",
        success: function (result) {
            $("#blogQRContent").attr("src", result.message);
        },
        error: function (result) {
            swal("请求有误!!");
        }
    });
    $('#blogWechatContent').show();
    $('#popLayer').show();
}

function closeWeChatShare() {
    $("#popLayer").attr("style", "display:none");
    $('#blogWechatContent').attr("style", "display:none");
}
