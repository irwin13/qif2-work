CREATE TABLE QIF_COUNTER
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  sequence_key VARCHAR2(500),
  last_sequence integer DEFAULT 0,
  CONSTRAINT pk_counter PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  name VARCHAR2(500),
  event_interface VARCHAR2(500),
  event_type VARCHAR2(500),
  qif_process VARCHAR2(500),
  audit_trail_enabled char(1) default 'Y',
  active_accept_message CHAR(1) default 'Y',
  keep_message_content char(1) default 'Y',
  description VARCHAR2(500),
  CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_PROPERTY
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  property_key VARCHAR2(500),
  property_value VARCHAR2(500),
  description VARCHAR2(500),
  qif_event_id CHAR(32 BYTE),
  CONSTRAINT pk_event_prop PRIMARY KEY (id)
);

CREATE TABLE QIF_ADAPTER
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  name VARCHAR2(500),
  adapter_interface VARCHAR2(500),
  description VARCHAR2(500),
  CONSTRAINT pk_adapter PRIMARY KEY (id)
);

CREATE TABLE QIF_ADAPTER_PROPERTY
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  property_key VARCHAR2(500),
  property_value VARCHAR2(500),
  description VARCHAR2(500),
  qif_adapter_id CHAR(32 BYTE),
  CONSTRAINT pk_adapter_prop PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_LOG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  event_id CHAR(32 BYTE),
  reference_key VARCHAR2(500),
  node_name VARCHAR2(500),
  CONSTRAINT pk_event_log PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_LOG_MSG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  message_content clob,
  event_log_id CHAR(32),
  CONSTRAINT pk_event_log_msg PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  event_log_id CHAR(32 BYTE),
  parent_activity_id CHAR(32 BYTE),
  node_name VARCHAR2(500),
  activity_type VARCHAR2(500),
  activity_status VARCHAR2(50),
  start_time NUMBER(16),
  execution_time NUMBER(16),
  CONSTRAINT pk_act_log PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_INPUT_MSG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  input_message_content clob,
  activity_log_id CHAR(32),
  CONSTRAINT pk_act_log_in PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_OUTPUT_MSG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  output_message_content clob,
  activity_log_id CHAR(32),
  CONSTRAINT pk_act_log_out PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_DATA
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp default SYSTIMESTAMP(),
  last_update_by VARCHAR2(500),
  last_update_date timestamp default SYSTIMESTAMP(),
  activity_log_id CHAR(32 BYTE),
  data_key VARCHAR2(1000),
  data_value VARCHAR2(1000),
  CONSTRAINT pk_act_log_data PRIMARY KEY (id)
);
