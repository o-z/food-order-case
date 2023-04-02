create table if not exists BASKET
(
    ID              varchar(36)  not null
        primary key,
    CREATE_DATE     datetime     null,
    UPDATE_DATE     datetime     null,
    COMMENT         varchar(255) null,
    PRICE_TYPE      varchar(255) null,
    USER_ADDRESS_ID varchar(255) null,
    USER_ID         varchar(255) null
);

INSERT INTO CRAFT_GATE_ORDER.BASKET (ID, CREATE_DATE, UPDATE_DATE, COMMENT, PRICE_TYPE, USER_ADDRESS_ID, USER_ID)
VALUES ('f3d5470b-0f27-48f8-a34b-1555eea82271', '2023-04-01 16:43:40', '2023-04-01 16:43:42', null, 'TRY',
        'f3d5470b-0f27-48f8-a34b-1555eea82272', 'f3d5470b-0f27-48f8-a34b-1555eea82273');


create table if not exists BASKET_PRODUCT
(
    ID          varchar(36)  not null
        primary key,
    CREATE_DATE datetime     null,
    UPDATE_DATE datetime     null,
    PRODUCT_ID  varchar(255) null,
    BASKET_ID   varchar(36)  null,
    constraint FKdr7j3v3isrvf2h14cv5q2p6sf
        foreign key (BASKET_ID) references BASKET (ID)
);

INSERT INTO CRAFT_GATE_ORDER.BASKET_PRODUCT (ID, CREATE_DATE, UPDATE_DATE, PRODUCT_ID, BASKET_ID)
VALUES ('3956e8bb-35a1-4af1-b662-e138c6ddc699', '2023-04-01 16:45:52', '2023-04-01 16:45:52',
        '66d551b7-2612-4bda-96e3-7cd082937465', 'f3d5470b-0f27-48f8-a34b-1555eea82271');


create table if not exists BASKET_PRODUCT_OPTION
(
    ID                varchar(36)  not null
        primary key,
    CREATE_DATE       datetime     null,
    UPDATE_DATE       datetime     null,
    PRODUCT_OPTION_ID varchar(255) null,
    BASKET_PRODUCT_ID varchar(36)  null,
    constraint FK83w16arywgoo9ngccin532wnk
        foreign key (BASKET_PRODUCT_ID) references BASKET_PRODUCT (ID)
);

INSERT INTO CRAFT_GATE_ORDER.BASKET_PRODUCT_OPTION (ID, CREATE_DATE, UPDATE_DATE, PRODUCT_OPTION_ID, BASKET_PRODUCT_ID)
VALUES ('dda5006d-951a-4513-accf-ce1175755f65', '2023-04-01 16:45:52', '2023-04-01 16:45:52',
        '915a606b-ae98-4cd6-963e-6cccda571f07', '3956e8bb-35a1-4af1-b662-e138c6ddc699');


create table if not exists PLACE_ORDER
(
    ID                              varchar(36)    not null
        primary key,
    CREATE_DATE                     datetime       null,
    UPDATE_DATE                     datetime       null,
    BASKET_ID                       varchar(255)   null,
    EXTERNAL_PAYMENT_TRANSACTION_ID varchar(255)   null,
    GATEWAY_TYPE                    varchar(255)   null,
    PRICE                           decimal(19, 2) null,
    PRICE_TYPE                      varchar(255)   null,
    RESTAURANT_FRANCHISING_ID       varchar(255)   null,
    RETRY_COUNT                     int default 0  null,
    STATUS                          varchar(255)   null,
    USER_ADDRESS_ID                 varchar(255)   null,
    USER_ID                         varchar(255)   null
);

INSERT INTO CRAFT_GATE_ORDER.PLACE_ORDER (ID, CREATE_DATE, UPDATE_DATE, BASKET_ID, EXTERNAL_PAYMENT_TRANSACTION_ID,
                                          GATEWAY_TYPE, PRICE, PRICE_TYPE, RESTAURANT_FRANCHISING_ID, RETRY_COUNT,
                                          STATUS, USER_ADDRESS_ID, USER_ID)
VALUES ('f4a2be0b-42c8-4eb8-8ad3-6c250f0e022b', '2023-04-01 16:47:26', '2023-04-01 16:47:28',
        'f3d5470b-0f27-48f8-a34b-1555eea82271', '263003', 'CRAFTGATE', 120.00, 'TRY',
        '5ebf353f-71df-4e60-b3ae-b2e75b924f60', 0, 'RECEIVED', 'f3d5470b-0f27-48f8-a34b-1555eea82272',
        'f3d5470b-0f27-48f8-a34b-1555eea82273');


create table if not exists PLACE_ORDER_PRODUCT
(
    ID           varchar(36)    not null
        primary key,
    CREATE_DATE  datetime       null,
    UPDATE_DATE  datetime       null,
    PRICE        decimal(19, 2) null,
    PRODUCT_ID   varchar(255)   null,
    PRODUCT_NAME varchar(255)   null,
    ORDER_ID     varchar(36)    null,
    constraint FKsvsm58jn5tnrr8hecnqpqff8u
        foreign key (ORDER_ID) references PLACE_ORDER (ID)
);

INSERT INTO CRAFT_GATE_ORDER.PLACE_ORDER_PRODUCT (ID, CREATE_DATE, UPDATE_DATE, PRICE, PRODUCT_ID, PRODUCT_NAME,
                                                  ORDER_ID)
VALUES ('2dea0eea-a2d9-4bf5-90e4-a84cf2e66084', '2023-04-01 16:47:26', '2023-04-01 16:47:26', 110.00,
        '66d551b7-2612-4bda-96e3-7cd082937465', 'Big Mac Moda', 'f4a2be0b-42c8-4eb8-8ad3-6c250f0e022b');


create table PLACE_ORDER_PRODUCT_OPTION
(
    ID                  varchar(36)    not null
        primary key,
    CREATE_DATE         datetime       null,
    UPDATE_DATE         datetime       null,
    PRICE               decimal(19, 2) null,
    PRODUCT_OPTION_ID   varchar(255)   null,
    PRODUCT_OPTION_NAME varchar(255)   null,
    ORDER_PRODUCT_ID    varchar(36)    null,
    constraint FKrcecqe0012aqjwj1cx633y48a
        foreign key (ORDER_PRODUCT_ID) references PLACE_ORDER_PRODUCT (ID)
);

INSERT INTO CRAFT_GATE_ORDER.PLACE_ORDER_PRODUCT_OPTION (ID, CREATE_DATE, UPDATE_DATE, PRICE, PRODUCT_OPTION_ID,
                                                         PRODUCT_OPTION_NAME, ORDER_PRODUCT_ID)
VALUES ('c2c6fc48-09a7-4f9a-9f2a-ba3c8aa57e3f', '2023-04-01 16:47:26', '2023-04-01 16:47:26', 10.00,
        '915a606b-ae98-4cd6-963e-6cccda571f07', 'Exstra Pickle', '2dea0eea-a2d9-4bf5-90e4-a84cf2e66084');
