
# blog个人博客
# 1. Ada个人博客网站
感谢网上的资料（高仿13的博客的框架）
# 2. 技术选型

开发技术：

- 后端
Springboot + mybatis + thymeleaf + mysql + Bootstrap4 + jqgrid
- 前端
bootstrap4 + html + CSS+ jQuery +thymeleaf 

# 3. 展示页面
首页

![](_v_images/_1564300152_19606.png)

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
- 先运行一下sql代码，或者保存为sql文件
```
/*
Navicat MySQL Data Transfer

Source Server         : Ada
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : web_db

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-27 15:00:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user` (
  `admin_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint(4) DEFAULT '0' COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`admin_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES ('1', 'ada', '202cb962ac59075b964b07152d234b70', 'Ada', '0', '2019-07-23');

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog` (
  `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) NOT NULL COMMENT '博客自定义路径url',
  `blog_cover_image` varchar(200) NOT NULL COMMENT '博客封面图',
  `blog_content` text NOT NULL COMMENT '博客内容',
  `blog_category_id` int(11) NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) NOT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) NOT NULL COMMENT '博客标签',
  `blog_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-草稿 1-发布',
  `blog_views` bigint(20) NOT NULL DEFAULT '0' COMMENT '阅读量',
  `enable_comment` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blog
-- ----------------------------
INSERT INTO `tb_blog` VALUES ('9', '阿斯顿发d', '', 'http://localhost:8080/upload/2019071621554850.gif', '![](http://localhost:8080/upload/2019071621553463.jpg)', '0', '默认分类', '士大夫', '1', '130', '0', '0', '2019-07-16 21:55:57', '2019-07-16 21:55:57');
INSERT INTO `tb_blog` VALUES ('11', 'SpringBoot系列', 'ddd', 'http://localhost:8080/upload/2019072322362143.png', '萨法asf', '22', 'SSM整合进阶篇', '爱上的地方', '1', '3', '0', '0', '2019-07-23 22:35:35', '2019-07-23 22:35:35');

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) NOT NULL COMMENT '分类的图标',
  `category_rank` int(11) NOT NULL DEFAULT '1' COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES ('22', 'SSM整合进阶篇', '/common/dist/img/category/02.png', '25', '0', '2019-07-23 23:07:36');

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comment`;
CREATE TABLE `tb_blog_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联的blog主键',
  `commentator` varchar(50) NOT NULL DEFAULT '' COMMENT '评论者名称',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  `website_url` varchar(50) NOT NULL DEFAULT '' COMMENT '网址',
  `comment_body` varchar(200) NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '评论时的ip地址',
  `reply_body` varchar(200) NOT NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `comment_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否审核通过 0-未审核 1-审核通过',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES ('27', '4', '十三', '224683568@qq.com', '', '第一条评论', '2019-05-13 10:12:19', '', '打的费', '2019-07-10 15:07:11', '1', '1');
INSERT INTO `tb_blog_comment` VALUES ('28', '10', 'ada xie', '1355948107@qq.com', '', '好的呢', '2019-07-20 23:19:22', '', '的的', '2019-07-20 23:19:57', '1', '0');

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES ('57', '世界上有一个很可爱的人', '0', '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES ('58', '现在这个人就在看这句话', '0', '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES ('66', 'Spring', '0', '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES ('67', 'SpringMVC', '0', '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES ('68', 'MyBatis', '0', '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES ('69', 'easyUI', '0', '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES ('127', '目录', '0', '2019-04-24 15:41:39');
INSERT INTO `tb_blog_tag` VALUES ('128', 'AdminLte3', '0', '2019-04-24 15:46:16');
INSERT INTO `tb_blog_tag` VALUES ('130', 'SpringBoot', '0', '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES ('131', '入门教程', '0', '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES ('132', '实战教程', '0', '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES ('133', 'spring-boot企业级开发', '0', '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES ('135', '啊啊啊', '1', '2019-07-08 23:19:04');
INSERT INTO `tb_blog_tag` VALUES ('136', '大', '0', '2019-07-14 21:25:26');
INSERT INTO `tb_blog_tag` VALUES ('137', '大大', '0', '2019-07-15 22:55:17');
INSERT INTO `tb_blog_tag` VALUES ('138', '爱上的地方阿斯蒂芬', '0', '2019-07-15 23:04:22');
INSERT INTO `tb_blog_tag` VALUES ('139', '发送到啊', '0', '2019-07-16 21:06:59');
INSERT INTO `tb_blog_tag` VALUES ('140', '大幅度', '0', '2019-07-16 21:49:50');
INSERT INTO `tb_blog_tag` VALUES ('141', '士大夫', '0', '2019-07-16 21:55:57');
INSERT INTO `tb_blog_tag` VALUES ('142', 'df dsf', '0', '2019-07-19 22:10:09');
INSERT INTO `tb_blog_tag` VALUES ('143', '爱上的地方', '0', '2019-07-23 22:35:35');

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag_relation`;
CREATE TABLE `tb_blog_tag_relation` (
  `relation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blog_tag_relation
-- ----------------------------
INSERT INTO `tb_blog_tag_relation` VALUES ('269', '2', '127', '2019-05-13 09:56:49');
INSERT INTO `tb_blog_tag_relation` VALUES ('278', '3', '128', '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES ('319', '1', '130', '2019-07-13 17:16:37');
INSERT INTO `tb_blog_tag_relation` VALUES ('320', '1', '131', '2019-07-13 17:16:37');
INSERT INTO `tb_blog_tag_relation` VALUES ('321', '1', '132', '2019-07-13 17:16:37');
INSERT INTO `tb_blog_tag_relation` VALUES ('322', '1', '133', '2019-07-13 17:16:37');
INSERT INTO `tb_blog_tag_relation` VALUES ('363', '4', '130', '2019-07-14 20:16:43');
INSERT INTO `tb_blog_tag_relation` VALUES ('364', '4', '131', '2019-07-14 20:16:43');
INSERT INTO `tb_blog_tag_relation` VALUES ('365', '4', '132', '2019-07-14 20:16:43');
INSERT INTO `tb_blog_tag_relation` VALUES ('366', '4', '133', '2019-07-14 20:16:43');
INSERT INTO `tb_blog_tag_relation` VALUES ('367', '5', '136', '2019-07-14 21:25:26');
INSERT INTO `tb_blog_tag_relation` VALUES ('368', '6', '137', '2019-07-15 22:55:17');
INSERT INTO `tb_blog_tag_relation` VALUES ('372', '7', '138', '2019-07-16 21:06:12');
INSERT INTO `tb_blog_tag_relation` VALUES ('373', '8', '140', '2019-07-16 21:49:50');
INSERT INTO `tb_blog_tag_relation` VALUES ('374', '9', '141', '2019-07-16 21:55:57');
INSERT INTO `tb_blog_tag_relation` VALUES ('378', '10', '142', '2019-07-21 16:15:19');
INSERT INTO `tb_blog_tag_relation` VALUES ('381', '11', '143', '2019-07-23 23:07:36');

-- ----------------------------
-- Table structure for tb_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_config`;
CREATE TABLE `tb_config` (
  `config_name` varchar(100) NOT NULL DEFAULT '' COMMENT '配置项的名称',
  `config_value` varchar(200) NOT NULL DEFAULT '' COMMENT '配置项的值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`config_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_config
-- ----------------------------
INSERT INTO `tb_config` VALUES ('footerAbout', 'Ada个人网站 1.0版本', '2018-11-11 20:33:23', '2019-07-23 23:07:16');
INSERT INTO `tb_config` VALUES ('footerCopyRight', '2019 Ada个人网站', '2018-11-11 20:33:31', '2019-07-23 23:07:16');
INSERT INTO `tb_config` VALUES ('footerICP', 'Copyright © 2019 Ada个人网站. 保留所有权利', '2018-11-11 20:33:27', '2019-07-23 23:07:16');
INSERT INTO `tb_config` VALUES ('footerPoweredBy', 'https://github.com/Ada0910', '2018-11-11 20:33:36', '2019-07-23 23:07:16');
INSERT INTO `tb_config` VALUES ('footerPoweredByURL', 'https://github.com/Ada0910', '2018-11-11 20:33:39', '2019-07-23 23:07:16');
INSERT INTO `tb_config` VALUES ('websiteDescription', '分享知识，记录点滴', '2018-11-11 20:33:04', '2019-07-23 23:07:11');
INSERT INTO `tb_config` VALUES ('websiteIcon', '/admin/img/user.png', '2018-11-11 20:33:11', '2019-07-23 23:07:11');
INSERT INTO `tb_config` VALUES ('websiteLogo', '/admin/img/logo.png', '2018-11-11 20:33:08', '2019-07-23 23:07:11');
INSERT INTO `tb_config` VALUES ('websiteName', 'Ada个人网站', '2018-11-11 20:33:01', '2019-07-23 23:07:11');
INSERT INTO `tb_config` VALUES ('yourAvatar', '/admin/dist/img/user.png', '2018-11-11 20:33:14', '2019-07-23 23:07:13');
INSERT INTO `tb_config` VALUES ('yourEmail', '1355948107@qq.com', '2018-11-11 20:33:17', '2019-07-23 23:07:13');
INSERT INTO `tb_config` VALUES ('yourName', 'Ada', '2018-11-11 20:33:20', '2019-07-23 23:07:13');

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_link`;
CREATE TABLE `tb_link` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '友链类别 0-友链 1-社区 2-网站',
  `link_name` varchar(50) NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) NOT NULL COMMENT '网站描述',
  `link_rank` int(11) NOT NULL DEFAULT '0' COMMENT '用于列表排序',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`link_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_link
-- ----------------------------
INSERT INTO `tb_link` VALUES ('4', '1', 'CSDN 图文课', 'https://gitchat.csdn.net', 'IT优质内容平台', '6', '0', '2018-10-22 19:55:55');
INSERT INTO `tb_link` VALUES ('5', '2', '十三的博客园', 'https://www.cnblogs.com/han-1034683568', '最开始写博客的地方', '17', '0', '2018-10-22 19:56:14');
INSERT INTO `tb_link` VALUES ('6', '1', 'CSDN', 'https://www.csdn.net/', 'CSDN-专业IT技术社区官网', '4', '0', '2018-10-22 19:56:47');
INSERT INTO `tb_link` VALUES ('7', '0', '梁桂钊的博客', 'http://blog.720ui.com', '后端攻城狮', '1', '0', '2018-10-22 20:01:38');
INSERT INTO `tb_link` VALUES ('14', '0', 'dd', 'https://translate.google.cn/d', 'dd', '0', '0', '2019-07-23 23:04:45');

```

- 在idea上导入github项目，修改数据库端口就可以运行了（端口号修改成80就可以直接用项目的地址访问，部署到云服务器的前提）

![](_v_images/_1564303517_11860.png)

-  部署到云服务器上，在maven中打包成jar包，然后运行 java -jar xxx(文件名) ，即用ip端口号+项目号的时候就可以访问（想去除端口号参考上一点）

![](_v_images/_1564302859_107.png)

但是  java -jar xxx(文件名)  在关闭终端或者会话的时候就会停止运行，如需后台运行项目的话，可以改用
nohup java -jar >xxx.txt &
可以指定文件名输出日志到xxx.txt文件中

# 5. 感谢
感谢13的博客
>>>>>>> cfdfa74cbc132dccbbf64683f64d167db3487077
