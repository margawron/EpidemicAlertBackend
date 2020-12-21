create table t_proximity_measurements(
    prx_id     bigserial,
    prx_msr_id int,
    prx_alr_id int,
    prx_type   text
);

alter table t_proximity_measurements add constraint pk_t_proximity_measurements_prx_id
    primary key (prx_id);

alter table t_proximity_measurements add constraint fk_t_proximity_measurements_prx_msr_id
    foreign key (prx_msr_id) references t_location_measurements (msr_id);

alter table t_proximity_measurements add constraint fk_t_proximity_measurements_prx_alr_id
    foreign key (prx_alr_id) references t_alerts (alr_id);
