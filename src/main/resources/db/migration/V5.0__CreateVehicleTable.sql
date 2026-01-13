CREATE SEQUENCE public.vehicle_id_seq;
CREATE TABLE public.vehicle
(
    id          BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('vehicle_id_seq'),
    description VARCHAR(255)       NOT NULL,
    type        VARCHAR(32)        NOT NULL,
    plate       VARCHAR(10),
    group_id    BIGINT             NOT NULL,
    CONSTRAINT fk_vehicle_group
        FOREIGN KEY (group_id)
            REFERENCES public."group" (id)
            ON UPDATE CASCADE ON DELETE RESTRICT
);
