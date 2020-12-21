create table t_alerts(
    alr_id     bigserial,
    alr_sus_id int,
    alr_usr_id int
);

alter table t_alerts add constraint pk_t_alerts_alr_id
    primary key (alr_id);
