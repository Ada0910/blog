function showOriginalLike() {
    /**初始化进入页面点赞*/
    isLike = 1;
    var blogId = $("#blogId").val();
    var blogLikeTotal = $("#blogLikeTotal").val();
    var data = {"isLike": isLike, "blogId": blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addOrCancelLike',
        data: data,
        success: function (result) {
            $("#originalButton").hide();
            $("#cancelLikeButton").show();
            $("#addLikeButton").hide();
            $("#cancellikeTotal").html(Number(Number(result)+Number (blogLikeTotal)));
        },
        error: function () {
        }
    });
}
function cancelLike() {
    isLike = 0;
    var blogId = $("#blogId").val();
    var blogLikeTotal = $("#blogLikeTotal").val();
    var data = {"isLike": isLike, "blogId": blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addOrCancelLike',
        data: data,
        success: function (result) {
            $("#originalButton").hide();
            $("#cancelLikeButton").hide();
            $("#addLikeButton").show();
            $("#addLikeTotal").html(Number(Number(result)+Number (blogLikeTotal)));
        },
        error: function () {
        }
    });
}
function addLike() {
    isLike = 1;
    var blogId = $("#blogId").val();
    var blogLikeTotal = $("#blogLikeTotal").val();
    var data = {"isLike": isLike, "blogId": blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addOrCancelLike',
        data: data,
        success: function (result) {
            $("#originalButton").hide();
            $("#cancelLikeButton").show();
            $("#addLikeButton").hide();
            $("#addLikeTotal").html(Number(Number(result)+Number (blogLikeTotal)));
        },
        error: function () {
        }
    });
}


