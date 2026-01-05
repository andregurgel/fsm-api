CREATE SEQUENCE group_id_seq;
CREATE TABLE "group"
(
    id            BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('group_id_seq'),
    user_owner_id BIGINT             NOT NULL,
    name          VARCHAR(128)       NOT NULL,
    created_at    TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_group_owner
        FOREIGN KEY (user_owner_id)
            REFERENCES public."user" (id)
            ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE public.group_user
(
    group_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,

    CONSTRAINT pk_group_user PRIMARY KEY (group_id, user_id),

    CONSTRAINT fk_group_user_group
        FOREIGN KEY (group_id)
            REFERENCES public."group" (id)
            ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_group_user_user
        FOREIGN KEY (user_id)
            REFERENCES public."user" (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);