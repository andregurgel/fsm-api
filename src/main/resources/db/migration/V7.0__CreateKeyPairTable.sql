CREATE SEQUENCE public.key_pair_id_seq;
CREATE TABLE public.key_pair
(
    id          BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('key_pair_id_seq'),
    public_key  TEXT               NOT NULL,
    private_key TEXT               NOT NULL,
    created_at  TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP
);
