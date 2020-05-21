
######### quiz Start ##############
-- Table: public.quiz

-- DROP TABLE public.quiz;

CREATE TABLE public.quiz
(
    quiz_id bigint NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    is_publish character varying(255) COLLATE pg_catalog."default",
    pin character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT quiz_pkey PRIMARY KEY (quiz_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.quiz
    OWNER to postgres;
    
######### quiz End ##############


######### question Start ##############
-- Table: public.question

-- DROP TABLE public.question;

CREATE TABLE public.question
(
    question_id bigint NOT NULL,
    questn_descp character varying(255) COLLATE pg_catalog."default",
    quiz_id bigint NOT NULL,
    CONSTRAINT question_pkey PRIMARY KEY (question_id),
    CONSTRAINT fkb0yh0c1qaxfwlcnwo9dms2txf FOREIGN KEY (quiz_id)
        REFERENCES public.quiz (quiz_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.question
    OWNER to postgres;
    
######### question End ##############

######### answer Start ##############
-- Table: public.answer

-- DROP TABLE public.answer;

CREATE TABLE public.answer
(
    answer_id bigint NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    is_true_answr character varying(255) COLLATE pg_catalog."default",
    quiz_id bigint NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT answer_pkey PRIMARY KEY (answer_id),
    CONSTRAINT fk8frr4bcabmmeyyu60qt7iiblo FOREIGN KEY (question_id)
        REFERENCES public.question (question_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.answer
    OWNER to postgres;      

######### answer End ##############


######### player Start ##############
-- Table: public.player

-- DROP TABLE public.player;

CREATE TABLE public.player
(
    player_id bigint NOT NULL,
    nick_name character varying(255) COLLATE pg_catalog."default",
    quiz_id bigint NOT NULL,
    CONSTRAINT player_pkey PRIMARY KEY (player_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.player
    OWNER to postgres;    

######### player End ##############

######### playquiz Start ##############
-- Table: public.playquiz

-- DROP TABLE public.playquiz;

CREATE TABLE public.playquiz
(
    playquiz_id bigint NOT NULL,
    actual_answer character varying(255) COLLATE pg_catalog."default",
    answer_time integer NOT NULL,
    configure_time bigint NOT NULL,
    player_answr character varying(255) COLLATE pg_catalog."default",
    question_id bigint NOT NULL,
    quiz_id bigint NOT NULL,
    score bigint NOT NULL,
    state character varying(255) COLLATE pg_catalog."default",
    player_id bigint NOT NULL,
    CONSTRAINT playquiz_pkey PRIMARY KEY (playquiz_id),
    CONSTRAINT fkllyff5dstpnb6ht01294qke1g FOREIGN KEY (player_id)
        REFERENCES public.player (player_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.playquiz
    OWNER to postgres;
######### playquiz End ##############

####### VIEW Deprecated ########################

--DROP VIEW public.show_quiz_result;
CREATE VIEW SHOW_QUIZ_RESULT AS
select player.player_id,player.nick_name,playquiz.state,playquiz.quiz_id,playquiz.question_id,playquiz.score,playquiz.answer_time
from player,playquiz
where player.quiz_id = playquiz.quiz_id and player.player_id=playquiz.player_id
and playquiz.state='right';
####################################

######### admin_user_sync Start ##############  
-- Table: public.admin_user_sync

-- DROP TABLE public.admin_user_sync;

CREATE TABLE public.admin_user_sync
(
    sync_id bigint NOT NULL,
    is_timer_on character varying(255) COLLATE pg_catalog."default" NOT NULL,
    question_index bigint NOT NULL,
    quiz_id bigint NOT NULL,
    CONSTRAINT admin_user_sync_pkey PRIMARY KEY (sync_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.admin_user_sync
    OWNER to postgres;
######### admin_user_sync End ##############

######### global_configuration Start ###################
-- Table: public.global_configuration

-- DROP TABLE public.global_configuration;

CREATE TABLE public.global_configuration
(
    config_id bigint NOT NULL,
    config_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    config_value character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT global_configuration_pkey PRIMARY KEY (config_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.global_configuration
    OWNER to postgres;

######### global_configuration End ##################### 

##### Insert Script ######

INSERT INTO public.global_configuration(config_id, config_name, config_value) VALUES (1,'QUIZ_TIMER','20'); 