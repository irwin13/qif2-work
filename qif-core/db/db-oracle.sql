-- BP TABLES
CREATE TABLE BP_SEQUENCE
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  sequence_key VARCHAR2(255),
  last_sequence integer DEFAULT 0,
  CONSTRAINT bp_sequence_pk PRIMARY KEY (id)
);

CREATE TABLE BP_LISTENER
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  name VARCHAR2(255),
  listener_interface VARCHAR2(255),
  listener_event VARCHAR2(255),
  bp_path VARCHAR2(255),
  audit_trail_enabled char(1) default 'Y',
  active_accept_message CHAR(1) default 'Y',
  description VARCHAR2(255),
  CONSTRAINT bp_listener_pk PRIMARY KEY (id)
);

CREATE TABLE BP_LISTENER_PROPERTY
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  property_key VARCHAR2(255),
  property_value VARCHAR2(255),
  description VARCHAR2(255),
  bp_listener CHAR(32 BYTE),
  CONSTRAINT bp_listener_property_pk PRIMARY KEY (id)
);

CREATE TABLE BP_ADAPTER
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  name VARCHAR2(255),
  adapter_interface VARCHAR2(255),
  description VARCHAR2(255),
  CONSTRAINT bp_adapter_pk PRIMARY KEY (id)
);

CREATE TABLE BP_ADAPTER_PROPERTY
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  property_key VARCHAR2(255),
  property_value VARCHAR2(255),
  description VARCHAR2(255),
  bp_adapter CHAR(32 BYTE),
  CONSTRAINT bp_adapter_property_pk PRIMARY KEY (id)
);

CREATE TABLE BP_RECEIVE_LOG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  bp_listener CHAR(32 BYTE),
  reference_key VARCHAR2(500),
  node_name VARCHAR2(255),
  CONSTRAINT bp_receive_log_pk PRIMARY KEY (id)
);

CREATE TABLE BP_RECEIVE_LOG_CONTENT
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  source_content clob,
  bp_receive_log CHAR(32),
  CONSTRAINT receive_log_content_pk PRIMARY KEY (id)
);

CREATE TABLE BP_ACTIVITY_LOG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  bp_receive_log CHAR(32 BYTE),
  parent_activity CHAR(32 BYTE),
  node_name VARCHAR2(255),
  activity_type VARCHAR2(255),
  activity_status VARCHAR2(50),
  starts NUMBER(16),
  durations NUMBER(16),
  CONSTRAINT bp_activity_log_pk PRIMARY KEY (id)
);

CREATE TABLE BP_ACTIVITY_LOG_IN
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  input_content clob,
  bp_activity_log CHAR(32),
  CONSTRAINT activity_log_in_pk PRIMARY KEY (id)
);

CREATE TABLE BP_ACTIVITY_LOG_OUT
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  output_content clob,
  bp_activity_log CHAR(32),
  CONSTRAINT activity_log_out_pk PRIMARY KEY (id)
);

CREATE TABLE BP_ACTIVITY_LOG_DATA
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(255),
  create_date timestamp,
  last_update_by VARCHAR2(255),
  last_update_date timestamp,
  bp_activity_log CHAR(32 BYTE),
  data_key VARCHAR2(1000),
  data_value VARCHAR2(1000),
  CONSTRAINT activity_log_data_pk PRIMARY KEY (id)
);
