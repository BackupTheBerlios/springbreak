/* ============================================================================	*/
/* $Revision:$ 																	*/
/* $Id:$ 																		*/
/* $Author:$  																	*/
/* ============================================================================	*/

/*==============================================================*/
/* DBMS name:      PGSQL73                                      */
/* Created on:     03.02.2006 16:20:03                          */
/*==============================================================*/


drop table dbinfos;

drop table eventattributes;

drop table events;

drop table eventtype;

drop table rwtime;

drop table txtime;

/*==============================================================*/
/* Table: dbinfos                                               */
/*==============================================================*/
create table dbinfos (
id                   INT4                 not null,
updatestart          VARCHAR(255)         null,
updatestop           VARCHAR(255)         null,
processeditems       INT2                 null,
constraint PK_DBINFOS primary key (id)
);

/*==============================================================*/
/* Table: eventattributes                                       */
/*==============================================================*/
create table eventattributes (
eventid              int8                 not null,
attributeid          int8                 not null,
attributename        varchar(255)         null,
datatype             varchar(255)         null,
xmluri               varchar(255)         null,
value                varchar(255)         null,
constraint eventattributes_pkey primary key (eventid, attributeid)
);

/*==============================================================*/
/* Table: events                                                */
/*==============================================================*/
create table events (
eventid              int8                 not null,
xmlcontent           text                 null,
guid                 varchar(255)         null,
priority             varchar(255)         null,
localtimeid          timestamp            null,
utctimeid            timestamp            null,
localrwtimeid        timestamp            null,
utcrwtimeid          timestamp            null,
rwtimeid             int4                 null,
txtimeid             int4                 null,
eventtypeid          INT4                 null,
constraint PK_EVENTS primary key (eventid)
);

/*==============================================================*/
/* Table: eventtype                                             */
/*==============================================================*/
create table eventtype (
eventtypeid          INT4                 not null,
eventname            VARCHAR(255)         not null,
constraint PK_EVENTTYPE primary key (eventtypeid)
);

/*==============================================================*/
/* Table: rwtime                                                */
/*==============================================================*/
create table rwtime (
rwtimeid             int4                 not null,
rwday                int2                 null,
rwmonth              int2                 null,
rwyear               int2                 null,
constraint rwtime_pkey primary key (rwtimeid)
);

/*==============================================================*/
/* Table: txtime                                                */
/*==============================================================*/
create table txtime (
txtimeid             int4                 not null,
txday                int2                 null,
txmonth              int2                 null,
txyear               int2                 null,
constraint txtime_pkey primary key (txtimeid)
);

alter table eventattributes
   add constraint FK_EVENTATT_REFERENCE_EVENTS foreign key (eventid)
      references events (eventid)
      on delete restrict on update restrict;

alter table events
   add constraint FK_EVENTS_REFERENCE_RWTIME foreign key (rwtimeid)
      references rwtime (rwtimeid)
      on delete restrict on update restrict;

alter table events
   add constraint FK_EVENTS_REFERENCE_TXTIME foreign key (txtimeid)
      references txtime (txtimeid)
      on delete restrict on update restrict;

alter table events
   add constraint FK_EVENTS_REFERENCE_EVENTTYP foreign key (eventtypeid)
      references eventtype (eventtypeid)
      on delete restrict on update restrict;
