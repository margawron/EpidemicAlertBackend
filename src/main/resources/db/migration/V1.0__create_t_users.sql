create table t_users (
    usr_id bigserial,
    usr_username text,
    usr_password_hash text,
    usr_mail text,
    usr_permission_group text,
    usr_account_creation_date timestamp,
    usr_account_expiry_date timestamp,
    usr_account_state text
);

alter table t_users add constraint pk_t_users_usr_id
primary key (usr_id);