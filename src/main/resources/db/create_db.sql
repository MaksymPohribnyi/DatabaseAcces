
/* CREATION TABLE POST
*/

-- DROP TABLE IF EXISTS public.post;

CREATE TABLE IF NOT EXISTS public.post
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    content varchar(100),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    status integer,
    writter_id bigint NOT NULL
    CONSTRAINT post_pk PRIMARY KEY (id),
    CONSTRAINT writter_id_fk FOREIGN KEY (writter_id)
        REFERENCES public.writter (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- INSERTION DATA TO POST

INSERT INTO post(content, status, writter_id) VALUES('The olimpic games 2024', 0, 4);
INSERT INTO post(content, status, writter_id) VALUES('A strongest men in the world', 1, 2);
INSERT INTO post(content, status, writter_id) VALUES('Swimming tournament 1997', 2, 3);

INSERT INTO post(content, status, writter_id) VALUES('World War III', 2, 2);
INSERT INTO post(content, status, writter_id) VALUES('russian against Ukraine war 2022', 0, 3);

INSERT INTO post(content, status, writter_id) VALUES('The rate of Bitcoin', 0, 3);
INSERT INTO post(content, status, writter_id) VALUES('Actual currencies in 2024', 1, 4);

INSERT INTO post(content, status, writter_id) VALUES('Social environment', 0, 5);
INSERT INTO post(content, status, writter_id) VALUES('Social equality', 0, 5);

/* CREATION TABLE Label
*/

-- DROP TABLE IF EXISTS public.label;

CREATE TABLE IF NOT EXISTS public.label
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY),
    name varchar(100) ,
    CONSTRAINT label_pkey PRIMARY KEY (id)
);

-- INSERTION DATA TO Label

INSERT INTO post(name) VALUES('Sport');
INSERT INTO post(name) VALUES('Economy');
INSERT INTO post(name) VALUES('Politic');
INSERT INTO post(name) VALUES('Society');

/* CREATION TABLE Writter
*/

-- DROP TABLE IF EXISTS public.writter;

CREATE TABLE IF NOT EXISTS public.writter
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    first_name varchar(80),
    last_name varchar(80),
    CONSTRAINT writter_pkey PRIMARY KEY (id)
  );

-- INSERTION DATA TO Writter

INSERT INTO writter(first_name, last_name) VALUES('Ritta', 'Scitter');
INSERT INTO writter(first_name, last_name) VALUES('William', 'Shakespeare');
INSERT INTO writter(first_name, last_name) VALUES('Franz', 'Kafka');
INSERT INTO writter(first_name, last_name) VALUES('Taras', 'Shenchenko');
INSERT INTO writter(first_name, last_name) VALUES('Mark', 'Twain');

/* CREATION TABLE Label_Post
*/

-- DROP TABLE IF EXISTS public.label_post;

CREATE TABLE IF NOT EXISTS public.label_post
(
    label_id bigint NOT NULL,
    post_id bigint NOT NULL,
    CONSTRAINT label_post_pk PRIMARY KEY (label_id, post_id)
    CONSTRAINT label_id_fk FOREIGN KEY (label_id)
        REFERENCES public.label (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT post_id_fk FOREIGN KEY (post_id)
        REFERENCES public.post (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- INSERTION DATA TO Label_Post

INSERT INTO public.label_post(label_id, post_id) VALUES (1, 1);
INSERT INTO public.label_post(label_id, post_id) VALUES (1, 2);
INSERT INTO public.label_post(label_id, post_id) VALUES (1, 3);
INSERT INTO public.label_post(label_id, post_id) VALUES (3, 4);
INSERT INTO public.label_post(label_id, post_id) VALUES (3, 5);
INSERT INTO public.label_post(label_id, post_id) VALUES (2, 6);
INSERT INTO public.label_post(label_id, post_id) VALUES (2, 7);
INSERT INTO public.label_post(label_id, post_id) VALUES (4, 8);
INSERT INTO public.label_post(label_id, post_id) VALUES (4, 9);
