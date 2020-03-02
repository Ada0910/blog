/**未点赞/取消点赞=0 , 点赞=1*/
var isLike = 0;
/**点赞和未点赞标志位*/
var flagLike = false;

function addOrCancelLike() {
    /**未点赞*/
    if (flagLike == false) {
        flagLike = true;
        isLike = 1;
    } else if (flagLike == true) {
        /**已点赞*/
        flagLike = false;
        isLike = 0;
    }
    var blogId = $("#blogId").val();
    var data = {"isLike":isLike,"blogId":blogId};
    $.ajax({
        type: 'POST',//方法类型
        url: '/blog/addOrCancelLike',
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
            } else {
            }
            ;
        },
        error: function () {
        }
    });
}
