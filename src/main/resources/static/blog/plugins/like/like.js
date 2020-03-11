var isLike ;
function showOriginalLike() {
    alert(isLike);
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
            isLike = 1;
        },
        error: function () {
        }
    });
}

function cancelLike() {
    var blogId = $("#blogId").val();
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
            isLike = 0;
        },
        error: function () {
        }
    });
}

function addLike() {
    var blogId = $("#blogId").val();
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
            isLike = 1;
        },
        error: function () {
        }
    });
}


