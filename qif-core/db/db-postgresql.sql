CREATE TABLE QIF_COUNTER
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  sequence_key character varying(500),
  last_sequence integer DEFAULT 0,
  CONSTRAINT qif_counter_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  name character varying(500),
  event_interface character varying(500),
  event_type character varying(500),
  qif_receiver character varying(500),
  audit_trail_enabled char(1) default 'Y',
  active_accept_message CHAR(1) default 'Y',
  keep_message_content char(1) default 'Y',
  description character varying(500),
  CONSTRAINT qif_event_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_PROPERTY
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  property_key character varying(500),
  property_value character varying(500),
  description character varying(500),
  qif_event CHAR(32),
  CONSTRAINT qif_event_property_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ADAPTER
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  name character varying(500),
  adapter_interface character varying(500),
  description character varying(500),
  CONSTRAINT qif_adapter_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ADAPTER_PROPERTY
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  property_key character varying(500),
  property_value character varying(500),
  description character varying(500),
  qif_adapter CHAR(32),
  CONSTRAINT qif_adapter_property_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_LOG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  event_id CHAR(32),
  reference_key character varying(500),
  node_name character varying(500),
  CONSTRAINT qif_event_log_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_EVENT_LOG_MESSAGE
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  message_content clob,
  event_log_id CHAR(32),
  CONSTRAINT event_log_msg_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  event_log_id CHAR(32),
  parent_activity_id CHAR(32),
  node_name character varying(500),
  activity_type character varying(500),
  activity_status character varying(50),
  start_time NUMBER(16),
  execution_time NUMBER(16),
  CONSTRAINT qif_activity_log_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_INPUT_MESSAGE
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  input_message_content clob,
  activity_log_id CHAR(32),
  CONSTRAINT activity_log_in_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_OUTPUT_MESSAGE
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  output_message_content clob,
  activity_log_id CHAR(32),
  CONSTRAINT activity_log_out_pk PRIMARY KEY (id)
);

CREATE TABLE QIF_ACTIVITY_LOG_DATA
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp,
  last_update_by character varying(500),
  last_update_date timestamp,
  activity_log_id CHAR(32),
  data_key character varying(1000),
  data_value character varying(1000),
  CONSTRAINT activity_log_data_pk PRIMARY KEY (id)
);
