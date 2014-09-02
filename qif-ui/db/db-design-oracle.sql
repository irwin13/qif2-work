CREATE TABLE app_user
(
  id char(32) NOT NULL,
  active char(1) default 'Y',
  create_by varchar2(255),
  create_date timestamp,
  last_update_by varchar2(255),
  last_update_date timestamp,
  username varchar2(255),
  password varchar2(255),
  last_login_from varchar2(100),
  last_login timestamp,
  display_lang varchar2(5),
  first_name varchar2(255),
  last_name varchar2(255),
  email varchar2(255),
  phone varchar2(255),
  CONSTRAINT app_user_pkey PRIMARY KEY (id)
);

CREATE TABLE app_role
(
  id char(32) NOT NULL,
  active char(1) default 'Y',
  create_by varchar2(255),
  create_date timestamp,
  last_update_by varchar2(255),
  last_update_date timestamp,
  name varchar2(100),
  description varchar2(255),
  CONSTRAINT app_role_pkey PRIMARY KEY (id)
);

CREATE TABLE app_user_role
(
  app_user_id char(32),
  app_role_id char(32)
 );

CREATE TABLE app_permission
(
  id char(32) NOT NULL,
  active char(1) default 'Y',
  create_by varchar2(255),
  create_date timestamp,
  last_update_by varchar2(255),
  last_update_date timestamp,
  name varchar2(255),
  description varchar2(255),
  http_path varchar2(500),
  http_method varchar2(25),
  parent_menu char(32),
  icon_file varchar2(255),
  as_menu varchar2(1) default 'N',
  menu_order integer default 0,
  CONSTRAINT app_permission_pkey PRIMARY KEY (id)
);

CREATE TABLE app_role_permission
(
  app_role_id char(32),
  app_permission_id char(32)
 );

CREATE TABLE app_option
(
  id char(32) NOT NULL,
  active char(1) default 'Y',
  create_by varchar2(255),
  create_date timestamp,
  last_update_by varchar2(255),
  last_update_date timestamp,
  name varchar2(255),
  option_category varchar2(255),
  description varchar2(255),
  CONSTRAINT app_option_pkey PRIMARY KEY (id)
);

CREATE TABLE app_setting
(
  id char(32) NOT NULL,
  active char(1) default 'Y',
  create_by varchar2(255),
  create_date timestamp,
  last_update_by varchar2(255),
  last_update_date timestamp,
  code varchar2(255),
  string_value varchar2(500),
  setting_category varchar2(500),
  description varchar2(255),
  CONSTRAINT app_setting_pkey PRIMARY KEY (id)
);
