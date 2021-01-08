alter table t_alerts
    add alr_creation_instant timestamp with time zone;

update t_alerts
set alr_creation_instant = now()
where alr_creation_instant is null;