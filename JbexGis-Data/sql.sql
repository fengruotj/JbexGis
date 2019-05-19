/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.4-m14 : Database - jbex
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jbex` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jbex`;

/*Table structure for table `t_friendrequest` */

DROP TABLE IF EXISTS `t_friendrequest`;

CREATE TABLE `t_friendrequest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `friend_id` bigint(20) NOT NULL,
  `requestgroup` char(20) DEFAULT NULL,
  `requestime` datetime DEFAULT NULL,
  `validationmessage` char(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `t_friendrequest_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_friendrequest_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_friendrequest` */

/*Table structure for table `t_groupfamily` */

DROP TABLE IF EXISTS `t_groupfamily`;

CREATE TABLE `t_groupfamily` (
  `user_id` bigint(20) NOT NULL,
  `friends_id` bigint(20) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `friends_id` (`friends_id`),
  CONSTRAINT `t_groupfamily_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_groupfamily_ibfk_2` FOREIGN KEY (`friends_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_groupfamily` */

insert  into `t_groupfamily`(`user_id`,`friends_id`) values (6,4),(10,4),(17,4),(4,18),(5,4),(5,4),(13,29),(29,13),(13,5),(4,42),(42,4),(4,54),(54,4),(4,55),(55,4),(4,53),(53,4),(4,56),(56,4),(4,57),(57,4),(58,4),(4,58),(13,37),(37,13),(13,42),(42,13),(13,30),(30,13),(9,13),(13,9);

/*Table structure for table `t_groupfriends` */

DROP TABLE IF EXISTS `t_groupfriends`;

CREATE TABLE `t_groupfriends` (
  `user_id` bigint(20) NOT NULL,
  `friends_id` bigint(20) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `friends_id` (`friends_id`),
  CONSTRAINT `t_groupfriends_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_groupfriends_ibfk_2` FOREIGN KEY (`friends_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_groupfriends` */

insert  into `t_groupfriends`(`user_id`,`friends_id`) values (4,6),(4,13),(13,4),(4,14),(12,13),(13,12),(11,4),(4,10),(4,15),(15,4),(16,4),(4,17),(16,17),(18,4),(23,24),(24,23),(24,17),(17,24),(5,4),(15,16),(4,27),(28,4),(4,28),(4,59);

/*Table structure for table `t_groupstudent` */

DROP TABLE IF EXISTS `t_groupstudent`;

CREATE TABLE `t_groupstudent` (
  `user_id` bigint(20) NOT NULL,
  `friends_id` bigint(20) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `friends_id` (`friends_id`),
  CONSTRAINT `t_groupstudent_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_groupstudent_ibfk_2` FOREIGN KEY (`friends_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_groupstudent` */

insert  into `t_groupstudent`(`user_id`,`friends_id`) values (8,4),(4,8),(14,4),(4,11),(5,13),(4,16),(17,16),(16,15),(27,4),(59,4);

/*Table structure for table `t_jbrxfriend` */

DROP TABLE IF EXISTS `t_jbrxfriend`;

CREATE TABLE `t_jbrxfriend` (
  `user_id` bigint(20) NOT NULL,
  `jbrxfriend_id` bigint(20) NOT NULL,
  `jbrxinfo_id` bigint(20) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `jbrxfriend_id` (`jbrxfriend_id`),
  KEY `jbrxinfo_id` (`jbrxinfo_id`),
  CONSTRAINT `t_jbrxfriend_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_jbrxfriend_ibfk_2` FOREIGN KEY (`jbrxfriend_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_jbrxfriend_ibfk_3` FOREIGN KEY (`jbrxinfo_id`) REFERENCES `t_jbrxinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_jbrxfriend` */

insert  into `t_jbrxfriend`(`user_id`,`jbrxfriend_id`,`jbrxinfo_id`) values (9,4,71);

/*Table structure for table `t_jbrxinfo` */

DROP TABLE IF EXISTS `t_jbrxinfo`;

CREATE TABLE `t_jbrxinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `dot_x` double NOT NULL,
  `dot_y` double NOT NULL,
  `title` varchar(50) NOT NULL COMMENT '主题',
  `detail` varchar(200) NOT NULL COMMENT '细节',
  `time` datetime NOT NULL COMMENT '发布时间',
  `label` varchar(50) NOT NULL COMMENT '标签',
  `picture1` varchar(100) DEFAULT NULL COMMENT '图片1',
  `picture2` varchar(100) DEFAULT NULL COMMENT '图片2',
  `size` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `t_jbrxinfo_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

/*Data for the table `t_jbrxinfo` */

insert  into `t_jbrxinfo`(`id`,`user_id`,`dot_x`,`dot_y`,`title`,`detail`,`time`,`label`,`picture1`,`picture2`,`size`) values (66,9,114.400167450715,30.505608676218305,'组队买衣服！！！！','换季了 想去买衣服了  有人一起吗','2015-10-12 12:30:00','运动','null','null',5),(67,10,114.40259838166416,30.52083189361987,'谁去网吧一起去玩啊','有没有人和我一起去玩啊，一起玩啊','2015-11-09 22:42:00','KTV','null','null',20),(68,4,114.3764585528664,30.507998969713235,'大家一起出去吃饭\n','','2015-10-10 00:21:00','足球','jbex_68_1.png','null',0),(69,4,114.36077746418779,30.517628959887674,'大家一起去吃饭\n','','2015-10-14 00:23:00','吃饭','jbex_69_1.png','null',100),(70,4,114.4105982385221,30.47683659742281,'呵呵呵','','2016-10-10 00:27:00','篮球','null','null',50),(71,4,114.38743836170178,30.509254443709775,'奶茶','','2016-10-10 00:28:00','奶茶','null','null',71),(72,4,114.4437445160723,30.514170113567484,'健身','','2016-10-10 00:29:00','健身','null','null',40),(73,13,114.41119305215369,30.650495532802417,'删除','','2016-10-10 04:27:00','运动','null','null',0);

/*Table structure for table `t_messagebean` */

DROP TABLE IF EXISTS `t_messagebean`;

CREATE TABLE `t_messagebean` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `send_id` bigint(20) NOT NULL,
  `receiver_id` bigint(20) NOT NULL,
  `time` datetime NOT NULL,
  `msg` varchar(100) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0一般消息 1好友请求消息 2结伴好友消息',
  PRIMARY KEY (`id`),
  KEY `send_id` (`send_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `t_messagebean_ibfk_1` FOREIGN KEY (`send_id`) REFERENCES `t_user` (`user_id`),
  CONSTRAINT `t_messagebean_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=utf8;

/*Data for the table `t_messagebean` */

insert  into `t_messagebean`(`id`,`send_id`,`receiver_id`,`time`,`msg`,`type`) values (121,4,27,'2015-09-02 17:27:17','。。。',0),(128,13,12,'2015-09-14 21:48:32','[鬼脸]',0),(144,4,59,'2015-09-18 18:49:10','更高的哈哈很高端',0),(145,4,59,'2015-09-18 18:49:11','规范公共',0),(146,4,59,'2015-09-18 18:49:13','gFF GG的',0),(247,4,59,'2015-09-19 12:29:16','[囧][囧]',0),(248,4,59,'2015-09-19 12:29:17','[生气][生气]',0),(264,4,6,'2015-09-20 01:25:22','到宿舍',0),(276,4,8,'2015-09-25 16:47:37','骨灰盒',0),(277,4,8,'2015-09-25 16:47:45','[瞌睡][囧][生气][生气]',0),(279,4,6,'2015-09-26 22:16:06','大多数',0);

/*Table structure for table `t_messageflag` */

DROP TABLE IF EXISTS `t_messageflag`;

CREATE TABLE `t_messageflag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0没有最新消息 1有最新消息',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `t_messageflag_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_messageflag` */

/*Table structure for table `t_myattention` */

DROP TABLE IF EXISTS `t_myattention`;

CREATE TABLE `t_myattention` (
  `user_id` bigint(20) NOT NULL,
  `attention_id` bigint(20) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `attention_id` (`attention_id`),
  CONSTRAINT `t_myattention_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_myattention_ibfk_2` FOREIGN KEY (`attention_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_myattention` */

insert  into `t_myattention`(`user_id`,`attention_id`) values (5,8),(5,8),(4,8),(4,13),(17,4),(16,4),(4,9),(4,6),(13,4),(8,4),(59,4),(4,20),(13,5);

/*Table structure for table `t_mycollections` */

DROP TABLE IF EXISTS `t_mycollections`;

CREATE TABLE `t_mycollections` (
  `user_id` bigint(20) NOT NULL,
  `collection_id` bigint(20) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `collection_id` (`collection_id`),
  CONSTRAINT `t_mycollections_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_mycollections_ibfk_2` FOREIGN KEY (`collection_id`) REFERENCES `t_jbrxinfo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_mycollections` */

insert  into `t_mycollections`(`user_id`,`collection_id`) values (10,67),(9,71),(13,66),(4,71);

/*Table structure for table `t_personaldynamic` */

DROP TABLE IF EXISTS `t_personaldynamic`;

CREATE TABLE `t_personaldynamic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `dot_x` double NOT NULL,
  `dot_y` double NOT NULL,
  `detail` varchar(200) NOT NULL COMMENT '细节',
  `picture1` varchar(100) DEFAULT NULL COMMENT '图片1',
  `picture2` varchar(100) DEFAULT NULL COMMENT '图片2',
  `time` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `t_personaldynamic_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `t_personaldynamic` */

insert  into `t_personaldynamic`(`id`,`user_id`,`dot_x`,`dot_y`,`detail`,`picture1`,`picture2`,`time`) values (15,14,114.3964890384924,30.51962687972575,'   今天大家在干嘛','null','null','2015-10-09 20:58:08'),(17,24,114.40280546860706,30.51805804891933,'哈哈','null','null','2015-10-09 21:03:15'),(18,34,114.3988665869067,30.50476966192929,'你在哪呢','null','null','2015-10-09 21:05:22'),(19,46,114.3993377323365,30.52115886283253,'相遇','null','null','2015-10-09 21:06:26'),(21,66,114.39740764291194,30.52889819305404,'没事做','null','null','2015-10-09 21:08:35'),(24,9,114.39852003417653,30.513407165451348,'我想打球','dynamic_24_1.png','null','2015-10-10 03:56:16'),(25,9,114.39852003417653,30.513407165451348,'我想打球','null','null','2015-10-10 03:56:20');

/*Table structure for table `t_publicinfo` */

DROP TABLE IF EXISTS `t_publicinfo`;

CREATE TABLE `t_publicinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `dot_x` double NOT NULL,
  `dot_y` double NOT NULL,
  `title` varchar(50) NOT NULL COMMENT '主题',
  `detail` varchar(200) NOT NULL COMMENT '细节',
  `time` datetime NOT NULL COMMENT '发布时间',
  `label` varchar(50) DEFAULT NULL COMMENT '标签',
  `picture1` varchar(100) DEFAULT NULL COMMENT '图片1',
  `picture2` varchar(100) DEFAULT NULL COMMENT '图片2',
  PRIMARY KEY (`id`),
  KEY `FK2EEAFF42C11C771D` (`user_id`),
  CONSTRAINT `FK2EEAFF42C11C771D` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

/*Data for the table `t_publicinfo` */

insert  into `t_publicinfo`(`id`,`user_id`,`dot_x`,`dot_y`,`title`,`detail`,`time`,`label`,`picture1`,`picture2`) values (79,65,114.40005276036841,30.528331642790654,'中国地质大学欢迎全体新生','中国地质学大学欢迎全体新生，祝新一代的地大人更具开拓精神!为地大争光','2015-09-01 12:01:00','运动','null','null'),(80,67,114.41769887794332,30.514768520409635,'华中科技大学讲座信息','今晚7.30分在文华楼由有阿里巴巴总裁马云的讲座，请有兴趣的同学们积极参与！','2015-09-24 09:00:00','运动','null','null'),(81,71,114.35590404828771,30.556225853232757,'武汉七大高校篮球联赛开赛啦','从今天开始武汉七大高校篮球联赛正式开赛!第一场将在武汉大学举行，同学们快来为自己的学校加油吧','2015-10-09 08:00:00','运动','null','null'),(82,71,114.36289593315578,30.522826702951622,'华师一附中名师孟昭奎来我校指导初三学生备考工作','2015年10月5日上午，华师一附中数学名师十堰地区招生负责人孟昭奎老师莅临十堰英才培训学校，为众多有志于报考华师一附中的优秀学子及家长做了关于2016年华师一附中高一招生相关信息及备考说明座谈会。','2015-10-07 20:06:00','运动','null','null'),(83,74,114.3553758981867,30.47365484831994,'【企业新闻】热烈祝贺华中农业大学“金新农班”正式成立','9月25日下午，第一届校企联办的金新农班在华中农业大学人文楼M507正式成立。经济管理学院党委副书记镇志勇、经济管理学院副院长申沛、金新农股份人资行政总监刘永生、招聘培训经理李钢、校企双方班主任陈蔚、何晋斌以及首届金新农班的30个学员出席了这次开班仪式。','2015-10-09 20:12:00','运动','null','null'),(84,76,114.36423338748385,30.52034441592929,'武汉理工大学马克思主义学院二〇一六年攻读硕士学位研究生招生简章','武汉理工大学是教育部直属的全国重点大学，是首批列入国家“211工程”重点建设的高校。在英国《泰晤士高等教育》2015年亚洲大学前100名排行榜上，我校名列第60位。学校现有三个校区，占地面积4000余亩，校舍总建筑面积逾156万平方米，四座现代化图书馆藏书400余万册。学校现有教职工5282人，其中专任教师2979人，专任教师中教授774人，副教授1346人；','2015-10-09 20:23:00','运动','null','null'),(85,78,114.38272731745309,30.474123851818977,'中南财经政法大学海南校友会众筹咖啡厅试业啦','邂逅咖啡厅，由中南财经政法大学海南校友会创办于2015年8月，是海南首家由文科高校校友会众筹创立的咖啡厅，经营餐饮、棋牌、茶艺、甜品。邂逅咖啡厅拥有1600平方米的经营面积','2015-10-09 20:27:00','运动','null','null'),(86,78,114.39827564623769,30.48314779566077,'中南民族大学2015年10月14日“校领导接待日”预告','根据《中南民族大学校领导接待日工作方案》及有关工作安排，现将2015年10月14日“校领导接待日”的有关事项预告如下：','2015-10-14 20:33:00','运动','null','null'),(87,81,114.39761310661665,30.520611961705846,'超市大减价','买口香糖送水杯','2015-10-11 20:39:00','运动','null','null'),(88,70,114.40340820589347,30.513213122781686,'珠宝设计大赛','请礼仪队的穿带珠宝走秀\n','2015-10-11 20:44:00','运动','null','null'),(89,79,114.40112070409532,30.51967951583099,'修表修车免费','','2015-10-12 20:48:00','运动','null','null'),(90,75,114.39337853307698,30.521825430895255,'四六级报名即将截至','四六级还没报名的同学请尽快报名 ，系统在9月15日会关闭','2015-09-09 12:20:00','运动','null','null');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `user_name` varchar(128) NOT NULL COMMENT '用户名，和邮箱一样',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `user_nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `person_signature` varchar(200) DEFAULT '未绑定' COMMENT '个性签名',
  `sex` int(11) DEFAULT '0' COMMENT '0、1、2分别为未知、男、女',
  `school` varchar(100) DEFAULT '中国地质大学' COMMENT '学校',
  `academy` varchar(100) DEFAULT '未绑定' COMMENT '院系',
  `state` int(11) DEFAULT '1' COMMENT '0、1分别代表离线和在线',
  `birthday` date DEFAULT '2014-05-06',
  `telephone` varchar(25) DEFAULT '未绑定',
  `picture` varchar(128) DEFAULT 'default.png',
  `SecurityControl` int(11) DEFAULT '0' COMMENT '权限   0完全不具备发布公众信息的权限 1可以发布社团的公众信息 2可以发布校园官方的信息',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`user_id`,`email`,`user_name`,`password`,`user_nickname`,`person_signature`,`sex`,`school`,`academy`,`state`,`birthday`,`telephone`,`picture`,`SecurityControl`) values (4,'798750508@qq.com','798750508@qq.com','woainixxx1314','小小梦想','我就是我是颜色不一样的烟火',1,'中国地质大学','信息工程学院',1,'1995-10-02','13072783289','4.png',2),(5,'798750507@qq.com','798750507@qq.com','woainixxx1314','小小的梦想','未绑定',1,'中国地质大学','未绑定',1,'2015-07-19','未绑定','5.png',0),(6,'798750506@qq.com','798750506@qq.com','woainixxx1314','李治韬','未绑定',1,'中国地质大学','未绑定',1,'2015-07-19','未绑定','6.png',0),(8,'798750504@qq.com','798750504@qq.com','woainixxx1314','李治','未绑定',1,'中国地质大学','未绑定',1,'2015-07-21','未绑定','8.png',1),(9,'798750503@qq.com','798750503@qq.com','woainixxx1314','薛大喧','未绑定',0,'中国地质大学','未绑定',1,'2014-05-06','未绑定','9.png',0),(10,'798750502@qq.com','798750502@qq.com','woainixxx1314','袁奇','未绑定',0,'中国地质大学','未绑定',1,'2014-05-06','未绑定','10.png',0),(11,'798750501@qq.com','798750501@qq.com','woainixxx1314','谭杰','未绑定',1,'中国地质大学','未绑定',1,'2015-07-28','未绑定','11.png',2),(12,'798750511@qq.com','798750511@qq.com','woainixxx1314','呵呵','未绑定',1,'中国地质大学','未绑定',1,'2015-07-29','未绑定','12.png',0),(13,'798750500@qq.com','798750500@qq.com','woainixxx1314','doctor','我就是我，是颜色不一样的焰火',1,'中国地质大学','计算机学院',1,'1994-08-29','13429803366','13.png',0),(14,'798750400@qq.com','798750400@qq.com','woainixxx1314','曹植端','未绑定',1,'中国地质大学','未绑定',1,'2015-07-29','未绑定','14.JPG',0),(15,'798750200@qq.com','798750200@qq.com','woainixxx1314','谭正华','未绑定',1,'中国地质大学','未绑定',1,'2015-08-04','未绑定','15.png',0),(16,'798750300@qq.com','798750300@qq.com','woainixxx1314','罗佳浩','呵呵大',1,'中国地质大学','未绑定',1,'2015-08-04','未绑定','16.png',0),(17,'405529829@qq.com','405529829@qq.com','woainixxx1314','大暄','未绑定',1,'中国地质大学','未绑定',1,'2015-08-09','未绑定','17.png',0),(18,'798750800@qq.com','798750800@qq.com','woainixxx1314','毛毛','未绑定',1,'中国地质大学','未绑定',1,'2015-08-09','未绑定','18.png',0),(19,'1748355708@qq.com','1748355708@qq.com','123456789','夏浩','未绑定',1,'中国地质大学','未绑定',1,'2015-08-12','未绑定','19.png',0),(20,'798750422@qq.com','798750422@qq.com','woainixxx1314','袁奇123','未绑定',1,'中国地质大学','未绑定',1,'2015-08-12','未绑定','20.png',0),(21,'798750111@qq.com','798750111@qq.com','woainixxx1314','王东帅','未绑定',1,'中国地质大学','未绑定',1,'2015-08-12','未绑定','21.png',0),(22,'798750222@qq.com','798750222@qq.com','woainixxx1314','梁霆南','未绑定',1,'中国地质大学','未绑定',1,'2015-08-12','未绑定','22.png',0),(23,'798750333@qq.com','798750333@qq.com','woainixxx1314','孙斌','未绑定',1,'中国地质大学','未绑定',1,'2015-08-12','未绑定','23.png',0),(24,'798750444@qq.com','798750444@qq.com','woainixxx1314','王天','未绑定',1,'中国地质大学','未绑定',1,'2015-08-12','未绑定','24.png',0),(27,'574141875@qq.com','574141875@qq.com','llll','Cugtt','未绑定',1,'中国地质大学','未绑定',1,'2015-08-27','未绑定','27.png',0),(28,'1234@qq.com','1234@qq.com','1234','1234','未绑定',1,'中国地质大学','未绑定',1,'2015-08-27','未绑定','default.png',0),(29,'798777@qq.com','798777@qq.com','woainixxx1314','学习','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(30,'798750544@qq.com','798750544@qq.com','woainixxx1314','注册您','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(31,'798750110@qq.com','798750110@qq.com','woainixxx1314','老大二号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(32,'798750112@qq.com','798750112@qq.com','woainixxx1314','老大三号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(33,'798750113@qq.com','798750113@qq.com','woainixxx1314','老大三号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(34,'798750522@qq.com','798750522@qq.com','woainixxx1314','老大四号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(35,'250@qq.com','250@qq.com','woainixxx1314','老大5号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(36,'798750789@qq.com','798750789@qq.com','woainixxx1314','好像','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(37,'798750116@qq.com','798750116@qq.com','woainixxx1314','到底','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(38,'798750117@qq.com','798750117@qq.com','woainixxx1314','老大流号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(39,'798750223@qq.com','798750223@qq.com','woainixxx1314','老大七号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(42,'798750@qq.com','798750@qq.com','woainixxx1314','DDr','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(46,'798750226@qq.com','798750226@qq.com','woainixxx1314','老大9号','未绑定',1,'中国地质大学','未绑定',1,'2015-09-14','未绑定','default.png',0),(47,'798750678@qq.com','798750678@qq.com','woainixxx1314','对对对','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(48,'123456789@qq.com','123456789@qq.com','woainixxx1314','大多数','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(49,'123456@qq.com','123456@qq.com','woainixxx1314','啊啊到底','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(50,'123@qq.com','123@qq.com','woainixxx1314','大大家看看','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(51,'12@qq.com','12@qq.com','woainixxx1314','啊（奋斗精神','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(52,'1@qq.com','1@qq.com','woainixxx1314','1@qq.','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(53,'2@qq.com','2@qq.com','woainixxx1314','sjajd','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(54,'5@qq.com','5@qq.com','woainixxx1314','ssfjsj','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(55,'8@qq.com','8@qq.com','woainixxx1314','#$^#3','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(56,'3@qq.com','3@qq.com','woainixxx1314','gfyjhhhhh','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(57,'6@qq.com','6@qq.com','woainixxx1314','6@qq.com','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(58,'99@qq.com','99@qq.com','woainixxx1314','99@qq.com','未绑定',1,'中国地质大学','未绑定',1,'2015-09-15','未绑定','default.png',0),(59,'2365291179@qq.com','2365291179@qq.com','2013199302122580','App','未绑定',1,'中国地质大学','未绑定',1,'1994-09-18','未绑定','59.png',0),(60,'413322133@qq.com','413322133@qq.com','102454930','rw','未绑定',1,'中国地质大学','信息工程学院',1,'1995-01-06','未绑定','60.png',0),(61,'413322133@qq.com','413322133@qq.com','102454930','mr','未绑定',1,'中国地质大学','未绑定',1,'2015-09-18','未绑定','default.png',0),(62,'100@qq.com','100@qq.com','woainixxx1314','中国地质大学广播电台','未绑定',1,'中国地质大学','未绑定',1,'2015-09-23','未绑定','default.png',2),(63,'798600002@qq.com','798600002@qq.com','woainixxx1314','地大山铭志','未绑定',1,'中国地质大学','未绑定',1,'2015-09-23','未绑定','default.png',1),(65,'798700000@qq.com','798700000@qq.com','woainixxx1314','中国地质大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','65.png',2),(66,'73674578@qq.com','73674578@qq.com','137645',' 中国地质大学信息工程学院','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','66.png',1),(67,'798700001@qq.com','798700001@qq.com','woainixxx1314','华中科技大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','67.png',2),(68,'74678523@qq.com','74678523@qq.com','137645','中国地质大学体课部','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','68.png',1),(69,'798700002@qq.com','798700002@qq.com','woainixxx1314','武汉大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',2),(70,'74156983@qq.com','74156983@qq.com','137645','中国地质大学珠宝学院','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','70.png',1),(71,'798700003@qq.com','798700003@qq.com','woainixxx1314','华中师范大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','71.png',2),(72,'15968231@qq.com','15968231@qq.com','137645','中国地质大学地质学院','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',1),(73,'14963752@qq.con','14963752@qq.con','137645','地大财务处','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',1),(74,'798700004@qq.com','798700004@qq.com','woainixxx1314','华中农业大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','74.png',2),(75,'58634696@qq.com','58634696@qq.com','137645','地大教务处','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','75.png',1),(76,'798700005@qq.com','798700005@qq.com','woainixxx1314','武汉理工大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','76.png',2),(77,'58697263@qq.com','58697263@qq.com','137645','地大后勤部','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',1),(78,'798700006@qq.com','798700006@qq.com','woainixxx1314','中南财经大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','78.png',2),(79,'259687146@qq.com','259687146@qq.com','137645','西三生活服务中心','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','79.png',1),(80,'798700007@qq.com','798700007@qq.com','woainixxx1314','中南民族大学校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','80.png',2),(81,'58364128@qq.com','58364128@qq.com','137645','西三超市','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','81.png',2),(82,'798700008@qq.com','798700008@qq.com','woainixxx1314','武汉体育学院校园官方','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',2),(83,'798600000@qq.com','798600000@qq.com','woainixxx1314','地大广播台','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','83.png',1),(84,'798600001@qq.com','798600001@qq.com','woainixxx1314','地大第五元素合唱团','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',1),(85,'798600003@qq.com','798600003@qq.com','woainixxx1314','地大子非鱼','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',1),(86,'798600004@qq.com','798600004@qq.com','woainixxx1314','地大军乐团','未绑定',1,'中国地质大学','未绑定',1,'2015-10-09','未绑定','default.png',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
