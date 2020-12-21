create table t_pathogens(
        pat_id bigserial,
        pat_name text,
        pat_contagious_period int,
        pat_period_resolution text,
        pat_detection_range float,
        pat_accuracy float
);

alter table t_pathogens add constraint pk_t_pathogens_pat_id
    primary key (pat_id);