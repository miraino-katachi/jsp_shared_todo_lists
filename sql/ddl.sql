create table users (
	`id` int not null primary key auto_increment comment 'ID',
	`email` varchar(256) not null comment 'E-mailアドレス',
	`password` varchar(256) not null comment 'パスワード',
	`name` varchar(50) not null comment 'ユーザー名',
	`is_deleted` tinyint not null default 0 comment '削除フラグ',
	`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
	`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザー';

ALTER TABLE `users`
	ADD UNIQUE KEY `idx_users_email` (`email`);


create table todo_items (
	`id` int primary key auto_increment comment 'ID',
	`user_id` int not null comment 'ユーザーID',
	`registration_date` date not null comment '登録日',
	`expiration_date` date not null comment '期限日',
	`finished_date` date default null comment '完了日',
	`todo_item` varchar(50) not null comment 'TODO項目',
	`is_deleted` tinyint not null default 0 comment '削除フラグ',
	`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
	`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
)  ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'TODOリスト';

