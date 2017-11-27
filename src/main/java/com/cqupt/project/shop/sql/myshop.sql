DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id`     BIGINT(10)  NOT NULL AUTO_INCREMENT
  COMMENT '用户Id',
  `username`    VARCHAR(50) NOT NULL
  COMMENT '用户名',
  `password`    VARCHAR(50) NOT NULL
  COMMENT '用户密码',
  `email`       VARCHAR(50)          DEFAULT NULL,
  `phone`       VARCHAR(20)          DEFAULT NULL,
  `question`    VARCHAR(100)         DEFAULT NULL
  COMMENT '找回密码问题',
  `answer`      VARCHAR(100)         DEFAULT NULL
  COMMENT '找回密码答案',
  `role`        INT(4)      NOT NULL
  COMMENT '角色：0代表管理员；1代表普通用户',
  `create_time` DATETIME    NOT NULL
  COMMENT '创建用户时间',
  `update_time` DATETIME    NOT NULL
  COMMENT '最后一次修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_index`(`username`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id`  BIGINT(100)    NOT NULL AUTO_INCREMENT
  COMMENT '商品id',
  `category_id` INT(11)        NOT NULL
  COMMENT '商品对应的分类id',
  `name`        VARCHAR(50)    NOT NULL
  COMMENT '商品名称',
  `subtitle`    VARCHAR(100)   NOT NULL
  COMMENT '商品副标题',
  `main_image`  VARCHAR(100)   NOT NULL
  COMMENT '产品主图，url相对地址',
  `sub_images`  TEXT COMMENT '图片地址,json格式,扩展用',
  `detail`      TEXT COMMENT '商品详情',
  `price`       DECIMAL(20, 2) NOT NULL
  COMMENT '价格,单位-元保留两位小数',
  `stock`       INT(11)        NOT NULL
  COMMENT '库存数量',
  `status`      INT(6)                  DEFAULT '1'
  COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` DATETIME                DEFAULT NULL
  COMMENT '创建时间',
  `update_time` DATETIME                DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`product_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 30
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `shipping`;
CREATE TABLE `shipping` (
  `ship_id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id`           BIGINT(20)          DEFAULT NULL
  COMMENT '用户id',
  `receiver_name`     VARCHAR(20)         DEFAULT NULL
  COMMENT '收货姓名',
  `receiver_phone`    VARCHAR(20)         DEFAULT NULL
  COMMENT '收货固定电话',
  `receiver_mobile`   VARCHAR(20)         DEFAULT NULL
  COMMENT '收货移动电话',
  `receiver_province` VARCHAR(20)         DEFAULT NULL
  COMMENT '省份',
  `receiver_city`     VARCHAR(20)         DEFAULT NULL
  COMMENT '城市',
  `receiver_district` VARCHAR(20)         DEFAULT NULL
  COMMENT '区/县',
  `receiver_address`  VARCHAR(200)        DEFAULT NULL
  COMMENT '详细地址',
  `receiver_zip`      VARCHAR(6)          DEFAULT NULL
  COMMENT '邮编',
  `create_time`       DATETIME            DEFAULT NULL,
  `update_time`       DATETIME            DEFAULT NULL,
  PRIMARY KEY (`ship_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 33
  DEFAULT CHARSET = utf8;
DROP TABLE IF EXISTS `pay_info`;
CREATE TABLE `pay_info` (
  `id`              BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id`         BIGINT(20)          DEFAULT NULL
  COMMENT '用户id',
  `order_no`        BIGINT(20)          DEFAULT NULL
  COMMENT '订单号',
  `pay_platform`    INT(10)             DEFAULT NULL
  COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` VARCHAR(200)        DEFAULT NULL
  COMMENT '支付宝支付流水号',
  `platform_status` VARCHAR(20)         DEFAULT NULL
  COMMENT '支付宝支付状态',
  `create_time`     DATETIME            DEFAULT NULL
  COMMENT '创建时间',
  `update_time`     DATETIME            DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 61
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '订单子表id',
  `user_id`            BIGINT(20)          DEFAULT NULL,
  `order_no`           BIGINT(20)          DEFAULT NULL,
  `product_id`         BIGINT(20)          DEFAULT NULL
  COMMENT '商品id',
  `product_name`       VARCHAR(100)        DEFAULT NULL
  COMMENT '商品名称',
  `product_image`      VARCHAR(500)        DEFAULT NULL
  COMMENT '商品图片地址',
  `current_unit_price` DECIMAL(20, 2)      DEFAULT NULL
  COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity`           INT(10)             DEFAULT NULL
  COMMENT '商品数量',
  `total_price`        DECIMAL(20, 2)      DEFAULT NULL
  COMMENT '商品总价,单位是元,保留两位小数',
  `create_time`        DATETIME            DEFAULT NULL,
  `update_time`        DATETIME            DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`, `order_no`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 135
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id`           BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '订单id',
  `order_no`     BIGINT(20)          DEFAULT NULL
  COMMENT '订单号',
  `user_id`      BIGINT(20)          DEFAULT NULL
  COMMENT '用户id',
  `shipping_id`  BIGINT(20)          DEFAULT NULL,
  `payment`      DECIMAL(20, 2)      DEFAULT NULL
  COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` INT(4)              DEFAULT NULL
  COMMENT '支付类型,1-在线支付',
  `postage`      INT(10)             DEFAULT NULL
  COMMENT '运费,单位是元',
  `status`       INT(10)             DEFAULT NULL
  COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` DATETIME            DEFAULT NULL
  COMMENT '支付时间',
  `send_time`    DATETIME            DEFAULT NULL
  COMMENT '发货时间',
  `end_time`     DATETIME            DEFAULT NULL
  COMMENT '交易完成时间',
  `close_time`   DATETIME            DEFAULT NULL
  COMMENT '交易关闭时间',
  `create_time`  DATETIME            DEFAULT NULL
  COMMENT '创建时间',
  `update_time`  DATETIME            DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 118
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '类别Id',
  `parent_id`   INT(11)          DEFAULT NULL
  COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name`        VARCHAR(50)      DEFAULT NULL
  COMMENT '类别名称',
  `status`      TINYINT(1)       DEFAULT '1'
  COMMENT '类别状态1-正常,2-已废弃',
  `sort_order`  INT(4)           DEFAULT NULL
  COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
  `create_time` DATETIME         DEFAULT NULL
  COMMENT '创建时间',
  `update_time` DATETIME         DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100032
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id`          INT(11)    NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT(20) NOT NULL,
  `product_id`  BIGINT(20)          DEFAULT NULL
  COMMENT '商品id',
  `quantity`    INT(11)             DEFAULT NULL
  COMMENT '数量',
  `checked`     INT(11)             DEFAULT NULL
  COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` DATETIME            DEFAULT NULL
  COMMENT '创建时间',
  `update_time` DATETIME            DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 146
  DEFAULT CHARSET = utf8;