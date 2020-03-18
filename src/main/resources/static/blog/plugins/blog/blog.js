/**跳转到上一篇文章*/
function getPreArticle() {
    var blogId = Number($("#preArticle").val());
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
    var blogId = Number($("#nextArticle").val());
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
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/pdfDownload',
        success: function (result) {
        },
    });


}