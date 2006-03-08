-- Table: correlatedevents

-- DROP TABLE correlatedevents;

CREATE TABLE correlatedevents
(
  id int4 NOT NULL,
  guid varchar(36),
  eventxml varchar(7800),
  dbtimecreated timestamp,
  CONSTRAINT correlatedevents_pkey PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE correlatedevents OWNER TO postgres;




-- Table: correlationsets

-- DROP TABLE correlationsets;

CREATE TABLE correlationsets
(
  id int4 NOT NULL,
  correlationsetdef varchar(100) NOT NULL,
  correlationsetguid varchar(36) NOT NULL,
  correlatingdata varchar(100) NOT NULL,
  eventtype varchar(100) NOT NULL,
  dbtimecreated timestamp NOT NULL,
  eventid int4,
  CONSTRAINT correlationset_pkey PRIMARY KEY (id),
  CONSTRAINT fk5dcbd0abe90ad0d3 FOREIGN KEY (eventid)
      REFERENCES correlatedevents (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk7a821fa0e90ad0d3 FOREIGN KEY (eventid)
      REFERENCES correlatedevents (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkd5c1d4d3e90ad0d3 FOREIGN KEY (eventid)
      REFERENCES correlatedevents (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE correlationsets OWNER TO postgres;


-- Index: correlationsets_correlationsetguid_index

-- DROP INDEX correlationsets_correlationsetguid_index;

CREATE INDEX correlationsets_correlationsetguid_index
  ON correlationsets
  USING btree
  (correlationsetguid);



