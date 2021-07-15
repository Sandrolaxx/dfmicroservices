CREATE sequence IF NOT EXISTS GEN_CART start 1 increment 1;
CREATE sequence IF NOT EXISTS GEN_DF_PRODUCT_CART start 1 increment 1;
CREATE sequence IF NOT EXISTS GEN_ORDER start 1 increment 1;

CREATE TABLE public.DF_CART (
    id int4 not null,
    ACTIVE boolean,
    CREATED_AT timestamp,
    ID_USER int4,
    primary key (id)
);

CREATE TABLE public.DF_ORDER (
    id int4 not null,
    ADDRESS_DESCRIPTION varchar(255),
    CREATED_AT timestamp,
    DELIVERY_VALUE float8,
    LATITUDE float8,
    LONGITUDE float8,
    ORDER_STATUS varchar(255),
    PAYMENT_TYPE varchar(255),
    TOTAL float8,
    UPDATED_AT timestamp,
    ID_USER int4,
    primary key (id)
);

CREATE TABLE public.DF_PRODUCT (
    id int4 not null,
    ACTIVE boolean,
    CATEGORY varchar(255),
    DESCRIPTION varchar(255),
    DISCOUNT float8,
    IMAGE_URI varchar(255),
    NAME varchar(255),
    PLATE_SIZE varchar(255),
    PRICE float8,
    primary key (id)
);

CREATE TABLE public.DF_PRODUCT_CART (
    id int4 not null,
    CREATED_AT timestamp,
    ID_PRODUCT int4,
    QUANTITY int4,
    REMOVED boolean,
    UPDATED_AT timestamp,
    ID_CART int4,
    primary key (id)
);

CREATE TABLE public.DF_USER (
    ID int4 not null,
    ACCEPT_TERMS boolean,
    ACTIVE boolean,
    DOCUMENT varchar(255),
    EMAIL varchar(255),
    NAME varchar(255),
    PASSWORD varchar(255),
    PHONE varchar(255),
    SECRET varchar(255),
    primary key (ID)
);

CREATE TABLE public.DF_USER_ADDRESS (
    ID int4 not null,
    ACTIVE boolean,
    CITY varchar(255),
    DISTRICT varchar(255),
    LATITUDE varchar(255),
    LONGITUDE varchar(255),
    MAIN boolean,
    NUMBER int4,
    NUMBER_AP int4,
    SECRET varchar(255),
    STATE varchar(255),
    STREET varchar(255),
    ID_USER int4,
    primary key (ID)
);

alter table if exists DF_CART 
    add constraint DF_CART_FKEY 
    foreign key (ID_USER) 
    references DF_USER;

alter table if exists DF_ORDER 
    add constraint DF_ORDER_FKEY 
    foreign key (ID_USER) 
    references DF_USER;

alter table if exists DF_PRODUCT_CART 
    add constraint DF_PRODUCT_CART_KEY 
    foreign key (ID_CART) 
    references DF_CART;
    
alter table if exists DF_USER_ADDRESS 
    add constraint DF_USER_ADDRESS_KEY 
    foreign key (ID_USER) 
    references DF_USER;