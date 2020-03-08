/**未点赞/取消点赞=0 , 点赞=1*/
var isLike ;
/**点赞和未点赞标志位*/
var flagLike ;

var likeTotal= 0;
function addOrCancelLike() {
    /**未点赞*/
    if (flagLike != false) {
        flagLike = true;
        swal({title:'点赞完成！'});
        isLike = 1;
    } else  {
        /**已点赞*/
        flagLike = false;
        swal({title:'已取消!'});
        isLike = 0;
    }
    var blogId = $("#blogId").val();
    var data = {"isLike": isLike, "blogId": blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addOrCancelLike',
        data: data,
        success: function (result) {
            $("#likeTotal").html(result);
        },
        error: function () {
        }
    });
}
