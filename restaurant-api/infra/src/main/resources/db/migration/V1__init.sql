create table if not exists RESTAURANT
(
    ID          varchar(36)  not null
        primary key,
    CREATE_DATE datetime     null,
    UPDATE_DATE datetime     null,
    DESCRIPTION varchar(255) null,
    IMAGE_URL   varchar(255) null,
    NAME        varchar(255) null,
    STATUS      varchar(255) null
);

INSERT INTO FOOD_ORDER_CASE_RESTAURANT.RESTAURANT (ID, CREATE_DATE, UPDATE_DATE, DESCRIPTION, IMAGE_URL, NAME, STATUS)
VALUES ('0ec13e48-4c4e-479c-bb73-de8b1a557c97', '2023-04-01 16:36:23', '2023-04-01 16:36:23', 'Mc Donalds',
        'Mc Donalds Url', 'Mc Donalds', 'ACTIVE');


create table if not exists RESTAURANT_FRANCHISING
(
    ID            varchar(36)  not null
        primary key,
    CREATE_DATE   datetime     null,
    UPDATE_DATE   datetime     null,
    ADDRESS       varchar(255) null,
    COUNTRY       varchar(255) null,
    DESCRIPTION   varchar(255) null,
    NAME          varchar(255) null,
    RESTAURANT_ID varchar(255) null,
    STATUS        varchar(255) null,
    constraint UKdieyqa2vvso9i5hyx73r56wjy
        unique (NAME, RESTAURANT_ID),
    constraint FKcyf9xp1ty25pq9dkgvnqv4uyy
        foreign key (RESTAURANT_ID) references RESTAURANT (ID)
);

INSERT INTO FOOD_ORDER_CASE_RESTAURANT.RESTAURANT_FRANCHISING (ID, CREATE_DATE, UPDATE_DATE, ADDRESS, COUNTRY, DESCRIPTION,
                                                          NAME, RESTAURANT_ID, STATUS)
VALUES ('5ebf353f-71df-4e60-b3ae-b2e75b924f60', '2023-04-01 16:36:34', '2023-04-01 16:36:34', 'Moda', 'Turkey',
        'Mc Donalds Moda', 'Mc Donalds Moda', '0ec13e48-4c4e-479c-bb73-de8b1a557c97', 'ACTIVE');


create table if not exists BASE_PRODUCT
(
    ID                 varchar(36)    not null
        primary key,
    CREATE_DATE        datetime       null,
    UPDATE_DATE        datetime       null,
    CATEGORY_ID        varchar(255)   null,
    DEFAULT_PRICE      decimal(19, 2) null,
    DEFAULT_PRICE_TYPE varchar(255)   null,
    DESCRIPTION        varchar(255)   null,
    IMAGE_URL          varchar(255)   null,
    NAME               varchar(255)   null,
    RESTAURANT_ID      varchar(255)   null,
    STATUS             varchar(255)   null
);

INSERT INTO FOOD_ORDER_CASE_RESTAURANT.BASE_PRODUCT (ID, CREATE_DATE, UPDATE_DATE, CATEGORY_ID, DEFAULT_PRICE,
                                                DEFAULT_PRICE_TYPE, DESCRIPTION, IMAGE_URL, NAME, RESTAURANT_ID, STATUS)
VALUES ('3ce8e42b-00cf-46b4-86f9-e969f106d39e', '2023-04-01 16:36:59', '2023-04-01 16:36:59',
        '3fa85f64-5717-4562-b3fc-2c963f66afa6', 70.00, 'TRY', 'Big Mac', 'Big Mac', 'Big Mac',
        '0ec13e48-4c4e-479c-bb73-de8b1a557c97', 'ACTIVE');


create table if not exists BASE_PRODUCT_OPTION
(
    ID                 varchar(36)    not null
        primary key,
    CREATE_DATE        datetime       null,
    UPDATE_DATE        datetime       null,
    DEFAULT_PRICE      decimal(19, 2) null,
    DEFAULT_PRICE_TYPE varchar(255)   null,
    DESCRIPTION        varchar(255)   null,
    NAME               varchar(255)   null,
    STATUS             varchar(255)   null,
    BASE_PRODUCT_ID    varchar(36)    null,
    constraint FKfcd0xrswb7j2ooxyki201o503
        foreign key (BASE_PRODUCT_ID) references BASE_PRODUCT (ID)
);

INSERT INTO FOOD_ORDER_CASE_RESTAURANT.BASE_PRODUCT_OPTION (ID, CREATE_DATE, UPDATE_DATE, DEFAULT_PRICE, DEFAULT_PRICE_TYPE,
                                                       DESCRIPTION, NAME, STATUS, BASE_PRODUCT_ID)
VALUES ('e1cf9b88-4697-4ad1-a2f9-090c16f4b992', '2023-04-01 16:37:20', '2023-04-01 16:37:20', 10.00, 'TRY',
        'Exstra Cheddar', 'Exstra Cheddar', 'ACTIVE', '3ce8e42b-00cf-46b4-86f9-e969f106d39e');


create table if not exists PRODUCT
(
    ID                        varchar(36)    not null
        primary key,
    CREATE_DATE               datetime       null,
    UPDATE_DATE               datetime       null,
    CATEGORY_ID               varchar(255)   null,
    DESCRIPTION               varchar(255)   null,
    IMAGE_URL                 varchar(255)   null,
    NAME                      varchar(255)   null,
    PRICE                     decimal(19, 2) null,
    PRICE_TYPE                varchar(255)   null,
    RESTAURANT_FRANCHISING_ID varchar(255)   null,
    STATUS                    varchar(255)   null,
    BASE_PRODUCT_ID           varchar(36)    null,
    constraint FKfxtucsadbcam1md5nmg2oxjw4
        foreign key (BASE_PRODUCT_ID) references BASE_PRODUCT (ID)
);

INSERT INTO FOOD_ORDER_CASE_RESTAURANT.PRODUCT (ID, CREATE_DATE, UPDATE_DATE, CATEGORY_ID, DESCRIPTION, IMAGE_URL, NAME,
                                           PRICE, PRICE_TYPE, RESTAURANT_FRANCHISING_ID, STATUS, BASE_PRODUCT_ID)
VALUES ('66d551b7-2612-4bda-96e3-7cd082937465', '2023-04-01 16:37:57', '2023-04-01 16:37:57',
        '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'Big Mac Moda', 'Big Mac Moda', 'Big Mac Moda', 110.00, 'TRY',
        '5ebf353f-71df-4e60-b3ae-b2e75b924f60', 'ACTIVE', '3ce8e42b-00cf-46b4-86f9-e969f106d39e');

create table if not exists PRODUCT_OPTION
(
    ID                        varchar(36)    not null
        primary key,
    CREATE_DATE               datetime       null,
    UPDATE_DATE               datetime       null,
    DESCRIPTION               varchar(255)   null,
    NAME                      varchar(255)   null,
    PRICE                     decimal(19, 2) null,
    PRICE_TYPE                varchar(255)   null,
    RESTAURANT_FRANCHISING_ID varchar(255)   null,
    STATUS                    varchar(255)   null,
    BASE_PRODUCT_OPTION_ID    varchar(36)    null,
    PRODUCT_ID                varchar(36)    null,
    constraint FK1jbwhmhm5ol2aamcl8uwole9w
        foreign key (BASE_PRODUCT_OPTION_ID) references BASE_PRODUCT_OPTION (ID),
    constraint FKce5s9kcidsu22c94cepxr6hco
        foreign key (PRODUCT_ID) references PRODUCT (ID)
);

INSERT INTO FOOD_ORDER_CASE_RESTAURANT.PRODUCT_OPTION (ID, CREATE_DATE, UPDATE_DATE, DESCRIPTION, NAME, PRICE, PRICE_TYPE,
                                                  RESTAURANT_FRANCHISING_ID, STATUS, BASE_PRODUCT_OPTION_ID, PRODUCT_ID)
VALUES ('915a606b-ae98-4cd6-963e-6cccda571f07', '2023-04-01 16:38:37', '2023-04-01 16:38:37', 'Exstra Pickle',
        'Exstra Pickle', 10.00, 'TRY', '5ebf353f-71df-4e60-b3ae-b2e75b924f60', 'ACTIVE',
        'e1cf9b88-4697-4ad1-a2f9-090c16f4b992', '66d551b7-2612-4bda-96e3-7cd082937465');
