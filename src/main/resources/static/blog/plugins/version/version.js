function showVersion() {
    /*网站首页查看版本信息*/
    $.get("/admin/version/getLatestVersion/", function (r) {
        if (r.resultCode == 200 && r.data != null) {
            swal(
                'Ada个人网站新版本功能介绍',
                '版本号:' + r.data.versionSerialNum + '\n\n' + "更新日期" + r.data.versionCreateTime + '\n\n' + '功能介绍:' + '\n' + r.data.versionDescription
            )
        }
    });
}