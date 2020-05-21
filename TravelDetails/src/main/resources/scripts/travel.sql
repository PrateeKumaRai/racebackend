-- Table: public.travel_details

-- DROP TABLE public.travel_details;

CREATE TABLE public.travel_details
(
    travel_id integer NOT NULL,
    created_at timestamp without time zone,
    dest_country character varying(255) COLLATE pg_catalog."default",
    dest_location character varying(255) COLLATE pg_catalog."default",
    employee_id integer NOT NULL,
    img_name character varying(255) COLLATE pg_catalog."default",
    img_pic bytea,
    img_type character varying(255) COLLATE pg_catalog."default",
    mob_num bigint,
    return_date date,
    source_location character varying(255) COLLATE pg_catalog."default",
    src_country character varying(255) COLLATE pg_catalog."default",
    start_date date,
    status boolean,
    travel_type character varying(255) COLLATE pg_catalog."default",
    updated_at timestamp without time zone,
    CONSTRAINT travel_details_pkey PRIMARY KEY (travel_id)
)

TABLESPACE pg_default;

ALTER TABLE public.travel_details
    OWNER to postgres;
	
	
	
--Delete Scripts

DELETE FROM public.travel_details
	WHERE <condition>;

--Insert Scripts

INSERT INTO public.travel_details(
	travel_id, created_at, dest_country, dest_location, employee_id, img_name, img_pic, img_type, mob_num, return_date, source_location, src_country, start_date, status, travel_type, updated_at)
	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);	
	
	
--update Scripts

UPDATE public.travel_details
	SET travel_id=?, created_at=?, dest_country=?, dest_location=?, employee_id=?, img_name=?, img_pic=?, img_type=?, mob_num=?, return_date=?, source_location=?, src_country=?, start_date=?, status=?, travel_type=?, updated_at=?
	WHERE <condition>;	
	
	