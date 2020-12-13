create table t_location_measurements(
    msr_id bigserial,
    msr_timestamp timestamp with time zone,
    msr_latitude float,
    msr_longitude float,
    msr_accuracy float,
    msr_bearing float,
    msr_bearing_accuracy float,
    msr_usr_id bigint,
    msr_dev_id bigint
);

alter table t_location_measurements add constraint pk_t_location_measurements_msr_id
primary key (msr_id);

alter table t_location_measurements add constraint fk_t_location_measurements_msr_usr_id
foreign key (msr_usr_id) references t_users(usr_id);

alter table t_location_measurements add constraint fk_t_location_measurements_msr_dev_id
foreign key (msr_dev_id) references t_devices(dev_id);