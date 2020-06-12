--
-- NOTE:
--
-- File paths need to be edited. Search for $$PATH$$ and
-- replace it with the path to the directory containing
-- the extracted data files.
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE isell;
--
-- Name: isell; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE isell WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE isell OWNER TO postgres;

\connect isell

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: travel_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.travel_details (
    travel_id integer NOT NULL,
    created_at timestamp without time zone,
    dest_country character varying(60) NOT NULL,
    dest_location character varying(60) NOT NULL,
    emp_id integer NOT NULL,
    img_name character varying(50),
    img_pic bytea,
    img_type character varying(20),
    mob_num bigint NOT NULL,
    return_date date NOT NULL,
    src_country character varying(60) NOT NULL,
    src_location character varying(60) NOT NULL,
    start_date date NOT NULL,
    status boolean,
    travel_type character varying(60),
    updated_at timestamp without time zone
);


ALTER TABLE public.travel_details OWNER TO postgres;

--
-- Data for Name: travel_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.travel_details (travel_id, created_at, dest_country, dest_location, emp_id, img_name, img_pic, img_type, mob_num, return_date, src_country, src_location, start_date, status, travel_type, updated_at) FROM stdin;
\.
COPY public.travel_details (travel_id, created_at, dest_country, dest_location, emp_id, img_name, img_pic, img_type, mob_num, return_date, src_country, src_location, start_date, status, travel_type, updated_at) FROM '$$PATH$$/2894.dat';

--
-- Name: travel_details travel_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_details
    ADD CONSTRAINT travel_details_pkey PRIMARY KEY (travel_id);


--
-- PostgreSQL database dump complete
--

