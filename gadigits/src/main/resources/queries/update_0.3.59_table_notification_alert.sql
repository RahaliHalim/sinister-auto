CREATE TABLE public.app_notification_alert
(
 id bigint NOT NULL DEFAULT nextval('app_notification_alert_seq'::regclass),
 source bigint,
 destination bigint,
 profile_id bigint,
 notification_id bigint,
 entity_name character varying(50),
 entity_id bigint,
 sending_date date,
 settings character varying(255),
 read boolean,
 CONSTRAINT pk_app_notification_alert PRIMARY KEY (id)
)

CREATE TABLE public.ref_notification_alert
(
 id bigint NOT NULL DEFAULT nextval('ref_notif_alert_seq'::regclass),
 label character varying(255),
 code character varying(50),
 sending_date date,
 type character varying(20),
 is_closed boolean,
 translation_code character varying(250),
 CONSTRAINT pk_ref_notif_alert PRIMARY KEY (id)
)
