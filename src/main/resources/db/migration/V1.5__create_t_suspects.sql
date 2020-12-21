create table t_suspects (
        sus_id bigserial,
        sus_start_date timestamp with time zone,
        sus_suspiciousness text,
        sus_usr_id int,
        sus_pat_id int
);

alter table t_suspects add constraint pk_t_suspects_sus_id
    primary key (sus_id);

alter table t_suspects add constraint fk_t_suspects_sus_usr_id
foreign key (sus_usr_id) references t_users(usr_id);

alter table t_suspects add constraint fk_t_suspects_sus_pat_id
    foreign key (sus_pat_id) references t_pathogens(pat_id);