CREATE SEQUENCE public.group_invite_id_seq;
CREATE TABLE public.group_invite
(
    id         BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('group_invite_id_seq'),
    group_id   BIGINT             NOT NULL,
    uses       INTEGER            NOT NULL,
    hash       UUID               NOT NULL,
    created_at TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP          NOT NULL,

    CONSTRAINT fk_group_invite_group
        FOREIGN KEY (group_id)
            REFERENCES public."group" (id)
            ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT uk_group_invite_hash UNIQUE (hash)
);
