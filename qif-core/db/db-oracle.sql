CREATE TABLE QIF_COUNTER
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  sequence_key VARCHAR2(500),
  last_sequence integer DEFAULT 0,
  CONSTRAINT qif_counter_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  name VARCHAR2(500),
  event_interface VARCHAR2(500),
  event_type VARCHAR2(500),
  qif_process VARCHAR2(500),
  audit_trail_enabled char(1) default 'Y',
  active_accept_message CHAR(1) default 'Y',
  keep_message_content char(1) default 'Y',
  description VARCHAR2(500),
  CONSTRAINT qif_event_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_PROPERTY
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  property_key VARCHAR2(500),
  property_value VARCHAR2(500),
  description VARCHAR2(500),
  qif_event CHAR(32 BYTE),
  CONSTRAINT qif_event_property_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ADAPTER
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  name VARCHAR2(500),
  adapter_interface VARCHAR2(500),
  description VARCHAR2(500),
  CONSTRAINT qif_adapter_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ADAPTER_PROPERTY
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  property_key VARCHAR2(500),
  property_value VARCHAR2(500),
  description VARCHAR2(500),
  qif_adapter CHAR(32 BYTE),
  CONSTRAINT qif_adapter_property_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_LOG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  event_id CHAR(32 BYTE),
  reference_key VARCHAR2(500),
  node_name VARCHAR2(500),
  CONSTRAINT qif_event_log_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_LOG_MESSAGE
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  message_content clob,
  event_log_id CHAR(32),
  CONSTRAINT event_log_msg_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  event_log_id CHAR(32 BYTE),
  parent_activity_id CHAR(32 BYTE),
  node_name VARCHAR2(500),
  activity_type VARCHAR2(500),
  activity_status VARCHAR2(50),
  start_time NUMBER(16),
  execution_time NUMBER(16),
  CONSTRAINT qif_activity_log_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_INPUT_MESSAGE
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  input_message_content clob,
  activity_log_id CHAR(32),
  CONSTRAINT activity_log_in_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_OUTPUT_MESSAGE
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  output_message_content clob,
  activity_log_id CHAR(32),
  CONSTRAINT activity_log_out_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_DATA
(
  id CHAR(32 BYTE) NOT NULL,
  active char(1) default 'Y',
  create_by VARCHAR2(500),
  create_date timestamp,
  last_update_by VARCHAR2(500),
  last_update_date timestamp,
  activity_log_id CHAR(32 BYTE),
  data_key VARCHAR2(1000),
  data_value VARCHAR2(1000),
  CONSTRAINT activity_log_data_pk PRIMARY KEY (id)
);
