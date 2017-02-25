
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS  user (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `pass_word` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `gmt_create` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `gmt_update` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;