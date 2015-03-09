create table student(
    pid varchar(5),
    plocation SDO_GEOMETRY,
    primary key(pid));
    
create table building(
    bid varchar(5),
    bname varchar(100) ,
    blocation SDO_GEOMETRY,
    primary key(bid));
    
create table announcement(
    asid varchar(10) ,
    aslocation SDO_GEOMETRY,
    radius int,
    primary key(asid));
    
INSERT INTO USER_SDO_GEOM_METADATA 
  VALUES (
  'student',
  'plocation',
  SDO_DIM_ARRAY(   -- 820X580 grid
    SDO_DIM_ELEMENT('X', 0, 820, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 580, 0.005)
     ),
  NULL   -- SRID
);
INSERT INTO USER_SDO_GEOM_METADATA 
  VALUES (
  'building',
  'blocation',
  SDO_DIM_ARRAY(   -- 820X580 grid
    SDO_DIM_ELEMENT('X', 0, 820, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 580, 0.005)
     ),
  NULL   -- SRID
);
INSERT INTO USER_SDO_GEOM_METADATA 
  VALUES (
  'announcement',
  'aslocation',
  SDO_DIM_ARRAY(   -- 820X580 grid
    SDO_DIM_ELEMENT('X', 0, 820, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 580, 0.005)
     ),
  NULL   -- SRID
);
    
create index student_idx 
    ON student(plocation) 
    INDEXTYPE IS MDSYS.SPATIAL_INDEX;
create index building_idx 
    ON building(blocation) 
    INDEXTYPE IS MDSYS.SPATIAL_INDEX;
create index announcement_idx 
    ON announcement(aslocation) 
    INDEXTYPE IS MDSYS.SPATIAL_INDEX;