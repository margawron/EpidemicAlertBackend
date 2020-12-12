create table t_devices (
    dev_id bigserial,
    dev_manufacturer text,
    dev_name text,
    dev_serial_number text,
    dev_usr_id bigint
);

alter table t_devices add constraint pk_t_devices_dev_id
primary key (dev_id);

alter table t_devices add constraint fk_t_devices_dev_usr_id
foreign key (dev_usr_id) references t_users(usr_id);