create table USER (
    ID integer primary key ,
    USERNAME varchar ,
    PASSWORD varchar ,
    FIRSTNAME varchar ,
    LASTNAME varchar ,
    EMAIL varchar ,
    ENABLED bit ,
    LASTPASSWORDRESETDATE date default sysdate);

create table AUTHORITY (
    ID integer primary key ,
    NAME varchar );

create table USER_AUTHORITY (
    USER_ID int,
    AUTHORITY_ID int,
    foreign key (user_id)
        references user(id),
    foreign key (authority_id)
        references AUTHORITY(ID));

