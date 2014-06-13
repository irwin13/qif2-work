SET search_path = qif;

--
-- TOC entry 1950 (class 0 OID 25843)
-- Dependencies: 174
-- Data for Name: qif_adapter; Type: TABLE DATA; Schema: qif_test; Owner: qif_test
--

INSERT INTO qif_adapter (id, active, create_by, create_date, last_update_by, last_update_date, name, adapter_interface, description) VALUES ('f9bd1632beb04e498f915a0975ae4831', 'Y', 'SYSTEM', '2014-06-02 16:27:08.95', 'SYSTEM', '2014-06-02 16:27:08.95', 'IrwinFtp', 'FTP', NULL);
INSERT INTO qif_adapter (id, active, create_by, create_date, last_update_by, last_update_date, name, adapter_interface, description) VALUES ('f9bd1632beb04e498f915a0975ae4832', 'Y', 'SYSTEM', '2014-06-02 16:27:08.952', 'SYSTEM', '2014-06-02 16:27:08.952', 'IrwinFile', 'FILE', NULL);


--
-- TOC entry 1951 (class 0 OID 25854)
-- Dependencies: 175
-- Data for Name: qif_adapter_property; Type: TABLE DATA; Schema: qif_test; Owner: qif_test
--

INSERT INTO qif_adapter_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_adapter_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287A', 'Y', 'SYSTEM', '2014-06-02 16:27:08.959', 'SYSTEM', '2014-06-02 16:27:08.959', 'host', 'localhost', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_adapter_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_adapter_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287B', 'Y', 'SYSTEM', '2014-06-02 16:27:08.96', 'SYSTEM', '2014-06-02 16:27:08.96', 'port', '21', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_adapter_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_adapter_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287C', 'Y', 'SYSTEM', '2014-06-02 16:27:08.961', 'SYSTEM', '2014-06-02 16:27:08.961', 'user', 'irwin', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_adapter_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_adapter_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287D', 'Y', 'SYSTEM', '2014-06-02 16:27:08.963', 'SYSTEM', '2014-06-02 16:27:08.963', 'password', 'irwin', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_adapter_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_adapter_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287A', 'Y', 'SYSTEM', '2014-06-02 16:27:08.965', 'SYSTEM', '2014-06-02 16:27:08.965', 'folder', '/c/hotfolder-out', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_adapter_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_adapter_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287B', 'Y', 'SYSTEM', '2014-06-02 16:27:08.966', 'SYSTEM', '2014-06-02 16:27:08.966', 'append', 'false', NULL, 'f9bd1632beb04e498f915a0975ae4832');


--
-- TOC entry 1948 (class 0 OID 25818)
-- Dependencies: 172
-- Data for Name: qif_event; Type: TABLE DATA; Schema: qif_test; Owner: qif_test
--

INSERT INTO qif_event (id, active, create_by, create_date, last_update_by, last_update_date, name, event_interface, event_type, qif_process, audit_trail_enabled, active_accept_message, keep_message_content, description) VALUES ('f9bd1632beb04e498f915a0975ae4831', 'Y', 'SYSTEM', '2014-06-02 16:27:08.918', 'SYSTEM', '2014-06-02 16:27:08.918', 'FileJsonEvent', 'file', 'scheduler_interval', 'id.co.quadras.qif.dev.process.FileJsonToFtp', 'Y', 'Y', 'Y', NULL);
INSERT INTO qif_event (id, active, create_by, create_date, last_update_by, last_update_date, name, event_interface, event_type, qif_process, audit_trail_enabled, active_accept_message, keep_message_content, description) VALUES ('f9bd1632beb04e498f915a0975ae4832', 'Y', 'SYSTEM', '2014-06-02 16:27:08.919', 'SYSTEM', '2014-06-02 16:27:08.919', 'FtpIrwinEvent', 'ftp', 'scheduler_interval', 'id.co.quadras.qif.dev.process.FtpXmlToFile', 'Y', 'Y', 'Y', NULL);


--
-- TOC entry 1949 (class 0 OID 25832)
-- Dependencies: 173
-- Data for Name: qif_event_property; Type: TABLE DATA; Schema: qif_test; Owner: qif_test
--

INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287A', 'Y', 'SYSTEM', '2014-06-02 16:27:08.925', 'SYSTEM', '2014-06-02 16:27:08.925', 'folder', '/c/hotfolder-in', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287B', 'Y', 'SYSTEM', '2014-06-02 16:27:08.926', 'SYSTEM', '2014-06-02 16:27:08.926', 'end_with', '.json', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287C', 'Y', 'SYSTEM', '2014-06-02 16:27:08.927', 'SYSTEM', '2014-06-02 16:27:08.927', 'max_fetch', '10', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287D', 'Y', 'SYSTEM', '2014-06-02 16:27:08.929', 'SYSTEM', '2014-06-02 16:27:08.929', 'last_modified_interval_seconds', '3', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287E', 'Y', 'SYSTEM', '2014-06-02 16:27:08.93', 'SYSTEM', '2014-06-02 16:27:08.93', 'delete_after_read', 'false', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287F', 'Y', 'SYSTEM', '2014-06-02 16:27:08.929', 'SYSTEM', '2014-06-02 16:27:08.929', 'interval', '3', NULL, 'f9bd1632beb04e498f915a0975ae4831');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('C11D75C1FEF64BF49D4CB0B7BA75287G', 'Y', 'SYSTEM', '2014-06-02 16:27:08.93', 'SYSTEM', '2014-06-02 16:27:08.93', 'concurrent_execution', 'false', NULL, 'f9bd1632beb04e498f915a0975ae4831');

INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287A', 'Y', 'SYSTEM', '2014-06-02 16:27:08.931', 'SYSTEM', '2014-06-02 16:27:08.931', 'folder', '/folder1/', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287B', 'Y', 'SYSTEM', '2014-06-02 16:27:08.931', 'SYSTEM', '2014-06-02 16:27:08.931', 'end_with', '.xml', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287C', 'Y', 'SYSTEM', '2014-06-02 16:27:08.932', 'SYSTEM', '2014-06-02 16:27:08.932', 'max_fetch', '1', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287D', 'Y', 'SYSTEM', '2014-06-02 16:27:08.932', 'SYSTEM', '2014-06-02 16:27:08.932', 'last_modified_interval_seconds', '3', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287E', 'Y', 'SYSTEM', '2014-06-02 16:27:08.933', 'SYSTEM', '2014-06-02 16:27:08.933', 'host', 'localhost', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287F', 'Y', 'SYSTEM', '2014-06-02 16:27:08.934', 'SYSTEM', '2014-06-02 16:27:08.934', 'port', '21', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287G', 'Y', 'SYSTEM', '2014-06-02 16:27:08.934', 'SYSTEM', '2014-06-02 16:27:08.934', 'user', 'irwin', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287H', 'Y', 'SYSTEM', '2014-06-02 16:27:08.935', 'SYSTEM', '2014-06-02 16:27:08.935', 'password', 'irwin', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287I', 'Y', 'SYSTEM', '2014-06-02 16:27:08.936', 'SYSTEM', '2014-06-02 16:27:08.936', 'delete_after_read', 'false', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287J', 'Y', 'SYSTEM', '2014-06-02 16:27:08.929', 'SYSTEM', '2014-06-02 16:27:08.929', 'interval', '3', NULL, 'f9bd1632beb04e498f915a0975ae4832');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('D11D75C1FEF64BF49D4CB0B7BA75287K', 'Y', 'SYSTEM', '2014-06-02 16:27:08.93', 'SYSTEM', '2014-06-02 16:27:08.93', 'concurrent_execution', 'false', NULL, 'f9bd1632beb04e498f915a0975ae4832');

-- http-event
INSERT INTO qif_event (id, active, create_by, create_date, last_update_by, last_update_date, name, event_interface, event_type, qif_process, audit_trail_enabled, active_accept_message, keep_message_content, description) VALUES ('f9bd1632beb04e498f915a0975ae4833', 'Y', 'SYSTEM', '2014-06-02 16:27:08.918', 'SYSTEM', '2014-06-02 16:27:08.918', 'HttpJsonEvent', 'http', 'incoming_message', 'id.co.quadras.qif.dev.process.FileJsonToFtp', 'Y', 'Y', 'Y', NULL);
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('E11D75C1FEF64BF49D4CB0B7BA75287A', 'Y', 'SYSTEM', '2014-06-02 16:27:08.93', 'SYSTEM', '2014-06-02 16:27:08.93', 'http_path', 'jsonToXml', NULL, 'f9bd1632beb04e498f915a0975ae4833');
INSERT INTO qif_event_property (id, active, create_by, create_date, last_update_by, last_update_date, property_key, property_value, description, qif_event_id) VALUES ('E11D75C1FEF64BF49D4CB0B7BA75287B', 'Y', 'SYSTEM', '2014-06-02 16:27:08.93', 'SYSTEM', '2014-06-02 16:27:08.93', 'http_method', 'post', NULL, 'f9bd1632beb04e498f915a0975ae4833');