create table t_alerts(
    alr_id     bigserial,
    alr_sus_id int,
    alr_usr_id int
);

alter table t_alerts add constraint pk_t_alerts_alr_id
    primary key (alr_id);

alter table t_alerts add constraint fk_t_alerts_alr_sus_id
    foreign key (alr_sus_id) references t_suspects(sus_id);

alter table t_alerts add constraint fk_t_alerts_alr_usr_id
    foreign key (alr_usr_id) references t_users(usr_id);
