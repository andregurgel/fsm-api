CREATE SEQUENCE user_id_seq;
CREATE TABLE "user"
(
    id         BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('user_id_seq'),
    name       VARCHAR(128)       NOT NULL,
    email      VARCHAR(128)       NOT NULL,
    password   VARCHAR(128)        NOT NULL,
    phone      VARCHAR(16),
    active     BOOLEAN            NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT user_uk UNIQUE (email)
);

INSERT INTO public."user" (email, password, name, phone, active)
VALUES ('andregurgeldev@gmail.com', '$2a$10$zxsKPrxOolibkBCOhXSbCu2cZfS6mYUmOsa.oEcwUtX6sBJX.ckou',
        'André Gurgel', '83998575577', true);