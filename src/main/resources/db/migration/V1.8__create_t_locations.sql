create table t_locations(
    loc_id          bigserial,
    loc_expiry_date timestamp with time zone,
    loc_latitude    float,
    loc_longitude   float,
    loc_type        text,
    loc_description text
);

alter table t_locations add constraint pk_t_locations_loc_id
    primary key (loc_id);