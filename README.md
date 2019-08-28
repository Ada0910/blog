
# 1. 2.0更新
-首页调整
![](_v_images/_1566996219_18389.png)

- 评论功能调整（以弹窗的形式）
![](_v_images/_1566996304_27088.png)
- 添加“关于”模块
![](_v_images/_1566996425_16270.png)
# 2. 技术选型

开发技术：

- 后端
Springboot + mybatis + thymeleaf + mysql + Bootstrap4 + jqgrid
- 前端
bootstrap4 + html + CSS+ jQuery +thymeleaf +sweetalert2

# 3. 展示页面
首页

![](_v_images/_1566996242_3290.png)

内容详情页

![](_v_images/_1564300189_30622.png)

友情链接

![](_v_images/_1564300911_17471.png)


关于

![](_v_images/_1564300935_3748.png)


后台：
登陆首页

![](_v_images/_1564301236_4681.png)

后台首页

![](_v_images/_1564301283_17358.png)


![](_v_images/_1564302190_11810.png)

![](_v_images/_1564302391_29948.png)

# 4. 部署
- 在_v_images下的web_db.sql是数据库文件，导入到mysql中

- 在idea上导入github项目，修改数据库端口就可以运行了（端口号修改成80就可以直接用项目的地址访问，部署到云服务器的前提）

![](_v_images/_1564303517_11860.png)

-  部署到云服务器上，在maven中打包成jar包，然后运行 java -jar xxx(文件名) ，即用ip端口号+项目号的时候就可以访问（想去除端口号参考上一点）

![](_v_images/_1564302859_107.png)

但是  java -jar xxx(文件名)  在关闭终端或者会话的时候就会停止运行，如需后台运行项目的话，可以改用
`nohup java -jar  xxx(文件名)>xxx.txt &`
可以指定文件名输出日志到xxx.txt文件中

# 5. 感谢
感谢13的博客
