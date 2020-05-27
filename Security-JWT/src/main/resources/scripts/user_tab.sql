-- Table: public.user_tab

-- DROP TABLE public.user_tab;

CREATE TABLE public.user_tab
(
    id bigint NOT NULL DEFAULT nextval('user_tab_id_seq'::regclass),
    area character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    email_id character varying(255) COLLATE pg_catalog."default",
    emp_id bigint,
    emp_name character varying(255) COLLATE pg_catalog."default",
    house_no character varying(255) COLLATE pg_catalog."default",
    is_enabled boolean,
    mobile bigint,
    otp integer,
    password character varying(255) COLLATE pg_catalog."default",
    user_name character varying(255) COLLATE pg_catalog."default",
    zip bigint,
    CONSTRAINT user_tab_pkey PRIMARY KEY (id),
    CONSTRAINT uk_aan08yvlq1w1a8h0p090i01qp UNIQUE (email_id),
    CONSTRAINT uk_k4bnxv87cbr0fq9512e9ppv4t UNIQUE (user_name),
    CONSTRAINT uk_qliw00orwy5q25scqwk3rxbr5 UNIQUE (emp_id)
)

TABLESPACE pg_default;

ALTER TABLE public.user_tab
    OWNER to postgres;