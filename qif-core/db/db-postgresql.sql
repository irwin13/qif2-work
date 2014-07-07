CREATE role qif LOGIN PASSWORD 'qif' CREATEDB VALID UNTIL 'infinity' CONNECTION LIMIT 300;

CREATE SCHEMA qif AUTHORIZATION qif;

ALTER ROLE qif SET search_path TO qif;

CREATE OR REPLACE FUNCTION qif.gen_uuid()
  RETURNS character varying AS
'select md5(clock_timestamp()::text||random()::text)::character varying'
  LANGUAGE sql VOLATILE
  COST 100;

ALTER FUNCTION qif.gen_uuid() OWNER TO qif;

CREATE TABLE qif.QIF_COUNTER
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  sequence_key character varying(500),
  last_sequence integer DEFAULT 0,
  CONSTRAINT pk_counter PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_EVENT
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  name character varying(500),
  event_interface character varying(500),
  event_type character varying(500),
  qif_process character varying(500),
  audit_trail_enabled char(1) default 'Y',
  active_accept_message CHAR(1) default 'Y',
  keep_message_content char(1) default 'Y',
  description character varying(500),
  CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_EVENT_PROPERTY
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  property_key character varying(500),
  property_value character varying(500),
  description character varying(500),
  qif_event_id CHAR(32),
  CONSTRAINT pk_event_prop PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_ADAPTER
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  name character varying(500),
  adapter_interface character varying(500),
  description character varying(500),
  CONSTRAINT pk_adapter PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_ADAPTER_PROPERTY
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  property_key character varying(500),
  property_value character varying(500),
  description character varying(500),
  qif_adapter_id CHAR(32),
  CONSTRAINT pk_adapter_prop PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_EVENT_LOG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  event_id CHAR(32),
  reference_key character varying(500),
  node_name character varying(500),
  CONSTRAINT pk_event_log PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_EVENT_LOG_MSG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  message_content text,
  event_log_id CHAR(32),
  CONSTRAINT pk_event_log_msg PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_ACTIVITY_LOG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  event_log_id CHAR(32),
  parent_activity_id CHAR(32),
  node_name character varying(500),
  activity_type character varying(500),
  activity_status character varying(50),
  start_time bigint,
  execution_time bigint,
  CONSTRAINT pk_act_log PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_ACTIVITY_LOG_INPUT_MSG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  input_message_content text,
  activity_log_id CHAR(32),
  CONSTRAINT pk_act_log_in PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_ACTIVITY_LOG_OUTPUT_MSG
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  output_message_content text,
  activity_log_id CHAR(32),
  CONSTRAINT pk_act_log_out PRIMARY KEY (id)
);

CREATE TABLE qif.QIF_ACTIVITY_LOG_DATA
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  activity_log_id CHAR(32),
  data_key character varying(1000),
  data_value character varying(1000),
  CONSTRAINT pk_act_log_data PRIMARY KEY (id)
);

CREATE TABLE qif.APP_SETTING
(
  id CHAR(32) NOT NULL,
  active char(1) default 'Y',
  create_by character varying(500),
  create_date timestamp default now(),
  last_update_by character varying(500),
  last_update_date timestamp default now(),
  code character varying(500),
  string_value character varying(500),
  setting_category character varying(500),
  description character varying(255),
  CONSTRAINT pk_app_setting PRIMARY KEY (id)
);

ALTER TABLE qif.QIF_COUNTER OWNER TO qif;
ALTER TABLE qif.QIF_EVENT OWNER TO qif;
ALTER TABLE qif.QIF_EVENT_PROPERTY OWNER TO qif;
ALTER TABLE qif.QIF_ADAPTER OWNER TO qif;
ALTER TABLE qif.QIF_ADAPTER_PROPERTY OWNER TO qif;
ALTER TABLE qif.QIF_EVENT_LOG OWNER TO qif;
ALTER TABLE qif.QIF_EVENT_LOG_MSG OWNER TO qif;
ALTER TABLE qif.QIF_ACTIVITY_LOG OWNER TO qif;
ALTER TABLE qif.QIF_ACTIVITY_LOG_DATA OWNER TO qif;
ALTER TABLE qif.QIF_ACTIVITY_LOG_INPUT_MSG OWNER TO qif;
ALTER TABLE qif.QIF_ACTIVITY_LOG_OUTPUT_MSG OWNER TO qif;
ALTER TABLE qif.APP_SETTING OWNER TO qif;