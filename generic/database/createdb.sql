-- Table: txtime

-- DROP TABLE txtime;

CREATE TABLE txtime
(
  txtimeid int4 NOT NULL,
  txday int2,
  txmonth int2,
  txyear int2,
  CONSTRAINT txtime_pkey PRIMARY KEY (txtimeid)
) 
WITHOUT OIDS;
ALTER TABLE txtime OWNER TO postgres;


-- Index: txtime_txday_index

-- DROP INDEX txtime_txday_index;

CREATE INDEX txtime_txday_index
  ON txtime
  USING btree
  (txday);

-- Index: txtime_txmonth_index

-- DROP INDEX txtime_txmonth_index;

CREATE INDEX txtime_txmonth_index
  ON txtime
  USING btree
  (txmonth);

-- Index: txtime_txtimeid_index

-- DROP INDEX txtime_txtimeid_index;

CREATE INDEX txtime_txtimeid_index
  ON txtime
  USING btree
  (txtimeid);

-- Index: txtime_txyear_index

-- DROP INDEX txtime_txyear_index;

CREATE INDEX txtime_txyear_index
  ON txtime
  USING btree
  (txyear);






-- Table: rwtime

-- DROP TABLE rwtime;

CREATE TABLE rwtime
(
  rwtimeid int4 NOT NULL,
  rwday int2,
  rwmonth int2,
  rwyear int2,
  CONSTRAINT rwtime_pkey PRIMARY KEY (rwtimeid)
) 
WITHOUT OIDS;
ALTER TABLE rwtime OWNER TO postgres;


-- Index: rwtime_rwday_index

-- DROP INDEX rwtime_rwday_index;

CREATE INDEX rwtime_rwday_index
  ON rwtime
  USING btree
  (rwday);

-- Index: rwtime_rwmonth_index

-- DROP INDEX rwtime_rwmonth_index;

CREATE INDEX rwtime_rwmonth_index
  ON rwtime
  USING btree
  (rwmonth);

-- Index: rwtime_rwtimeid_index

-- DROP INDEX rwtime_rwtimeid_index;

CREATE INDEX rwtime_rwtimeid_index
  ON rwtime
  USING btree
  (rwtimeid);

-- Index: rwtime_rwyear_index

-- DROP INDEX rwtime_rwyear_index;

CREATE INDEX rwtime_rwyear_index
  ON rwtime
  USING btree
  (rwyear);




-- Table: eventtype

-- DROP TABLE eventtype;

CREATE TABLE eventtype
(
  eventtypeid int4 NOT NULL,
  eventname varchar(255) NOT NULL,
  CONSTRAINT pk_eventtype PRIMARY KEY (eventtypeid)
) 
WITHOUT OIDS;
ALTER TABLE eventtype OWNER TO postgres;


-- Index: eventtype_eventname_index

-- DROP INDEX eventtype_eventname_index;

CREATE INDEX eventtype_eventname_index
  ON eventtype
  USING btree
  (eventname);

-- Index: eventtype_eventtypeid_index

-- DROP INDEX eventtype_eventtypeid_index;

CREATE INDEX eventtype_eventtypeid_index
  ON eventtype
  USING btree
  (eventtypeid);





-- Table: events

-- DROP TABLE events;

CREATE TABLE events
(
  eventid int8 NOT NULL,
  xmlcontent text,
  guid varchar(255),
  priority varchar(255),
  localtimeid timestamp,
  utctimeid timestamp,
  localrwtimeid timestamp,
  utcrwtimeid timestamp,
  rwtimeid int4,
  txtimeid int4,
  eventtypeid int4,
  CONSTRAINT pk_events PRIMARY KEY (eventid),
  CONSTRAINT fk_events_reference_eventtyp FOREIGN KEY (eventtypeid)
      REFERENCES eventtype (eventtypeid) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_events_reference_rwtime FOREIGN KEY (rwtimeid)
      REFERENCES rwtime (rwtimeid) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_events_reference_txtime FOREIGN KEY (txtimeid)
      REFERENCES txtime (txtimeid) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fkb307e11943f4b58c FOREIGN KEY (rwtimeid)
      REFERENCES rwtime (rwtimeid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkb307e1194c15134a FOREIGN KEY (txtimeid)
      REFERENCES txtime (txtimeid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkb307e1197a77aad6 FOREIGN KEY (eventtypeid)
      REFERENCES eventtype (eventtypeid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE events OWNER TO postgres;


-- Index: events_eventid_index

-- DROP INDEX events_eventid_index;

CREATE INDEX events_eventid_index
  ON events
  USING btree
  (eventid);


-- Table: eventattributes

-- DROP TABLE eventattributes;

CREATE TABLE eventattributes
(
  eventid int8 NOT NULL,
  attributeid int8 NOT NULL,
  attributename varchar(255),
  datatype varchar(255),
  xmluri varchar(255),
  value varchar(255),
  CONSTRAINT eventattributes_pkey PRIMARY KEY (eventid, attributeid),
  CONSTRAINT fk87a12371cb216e62 FOREIGN KEY (eventid)
      REFERENCES events (eventid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_eventatt_reference_events FOREIGN KEY (eventid)
      REFERENCES events (eventid) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
) 
WITHOUT OIDS;
ALTER TABLE eventattributes OWNER TO postgres;


-- Index: eventattributes_attributename_index

-- DROP INDEX eventattributes_attributename_index;

CREATE INDEX eventattributes_attributename_index
  ON eventattributes
  USING btree
  (attributename);

-- Index: eventattributes_eventid_index

-- DROP INDEX eventattributes_eventid_index;

CREATE INDEX eventattributes_eventid_index
  ON eventattributes
  USING btree
  (eventid);

-- Index: eventattributes_value_index

-- DROP INDEX eventattributes_value_index;

CREATE INDEX eventattributes_value_index
  ON eventattributes
  USING btree
  (value);

-- Index: eventattributes_xmluri_index

-- DROP INDEX eventattributes_xmluri_index;

CREATE INDEX eventattributes_xmluri_index
  ON eventattributes
  USING btree
  (xmluri);



-- Table: dbinfos

-- DROP TABLE dbinfos;

CREATE TABLE dbinfos
(
  id int4 NOT NULL,
  updatestart varchar(255),
  updatestop varchar(255),
  processeditems int2,
  CONSTRAINT pk_dbinfos PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE dbinfos OWNER TO postgres;


-- Index: dbinfos_id_index

-- DROP INDEX dbinfos_id_index;

CREATE INDEX dbinfos_id_index
  ON dbinfos
  USING btree
  (id);



