DELETE FROM user_sdo_geom_metadata;
drop index announcement_idx;
drop index building_idx;
drop index people_idx;

drop table people;
drop table building;
drop table announcement;