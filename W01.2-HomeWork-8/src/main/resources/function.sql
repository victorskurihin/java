CREATE OR REPLACE FUNCTION insert_db_statistic(
  marker VARCHAR(255), pagename VARCHAR(255), ipaddr VARCHAR(255), uagent VARCHAR(255),
  ctime TIMESTAMP, stime TIMESTAMP, sid VARCHAR(255), uid BIGINT, pid BIGINT)
RETURNS BIGINT LANGUAGE plpgsql AS
$function$
DECLARE
  id BIGINT := nextval('statistic_id_seq');
BEGIN
  IF uid < 0 THEN
    uid := NULL;
  END IF;
  INSERT INTO
    statistic(id,name_marker,jsp_page_name,ip_address,user_agent,client_time,server_time,session_id,user_id,prev_id)
    VALUES (id, marker, pagename, ipaddr, uagent, ctime, stime, sid, uid, pid);
  RETURN id;
END;
$function$
;

