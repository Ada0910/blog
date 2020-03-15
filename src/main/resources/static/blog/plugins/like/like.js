function showOriginalLike() {
    var isLike = $("#isLike").val();
    /**初始化进入页面点赞*/
    var blogId = $("#blogId").val();
    var blogLikeTotal = $("#blogLikeTotal").val();
    var data = {
        "isLike": isLike,
        "blogId": blogId,
    };
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addLike',
        data: data,
        success: function (result) {
            $("#originalButton").hide();
            $("#cancelLikeButton").show();
            $("#addLikeButton").hide();
            $("#cancellikeTotal").html(Number(Number(result) + Number(blogLikeTotal)));
        },
        error: function () {
        }
    });
}

function cancelLike() {
    var blogId = $("#blogId").val();
    var isLike = $("#isLike").val();
    var blogLikeTotal = $("#blogLikeTotal").val();
    var data = {"isLike": isLike, "blogId": blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/cancelLike',
        data: data,
        success: function (result) {
            $("#originalButton").hide();
            $("#cancelLikeButton").hide();
            $("#addLikeButton").show();
            $("#addLikeTotal").html(Number(Number(result) + Number(blogLikeTotal)));
        },
        error: function () {
        }
    });
}

function addLike() {
    var blogId = $("#blogId").val();
    var isLike = $("#isLike").val();
    var blogLikeTotal = $("#blogLikeTotal").val();
    var data = {"isLike": isLike, "blogId": blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addLike',
        data: data,
        success: function (result) {
            $("#originalButton").hide();
            $("#cancelLikeButton").show();
            $("#addLikeButton").hide();
            $("#addLikeTotal").html(Number(Number(result) + Number(blogLikeTotal)));
        },
        error: function () {
        }
    });
}


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
            if (id == result.length-1) {
                swal("这已经是最后一篇!!!");
            } else {
                window.location.href = "/blog/" + Number(result[id+ 1]);
            }

        },
    });
}

