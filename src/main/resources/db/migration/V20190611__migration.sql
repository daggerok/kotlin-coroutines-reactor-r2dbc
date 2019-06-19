DROP SCHEMA IF EXISTS schema1 CASCADE;

CREATE SCHEMA schema1;

CREATE TABLE employee (
    name character varying NOT NULL,
    salary integer NOT NULL,
    id serial PRIMARY KEY,
    organization_id bigint
);
CREATE TABLE organization (
    name character varying NOT NULL,
    id serial PRIMARY KEY
);
