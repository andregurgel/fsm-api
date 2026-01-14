CREATE TABLE public.supply
(
    id              UUID PRIMARY KEY NOT NULL,
    liter           DECIMAL(10, 2)   NOT NULL,
    price           DECIMAL(10, 2)   NOT NULL,
    current_mileage INTEGER,
    type            VARCHAR(32)      NOT NULL,
    supplied_at     TIMESTAMP        NOT NULL,
    created_at      TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted         BOOLEAN          NOT NULL DEFAULT FALSE,
    deleted_at      TIMESTAMP,
    vehicle_id      BIGINT           NOT NULL,
    CONSTRAINT fk_supply_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES public.vehicle (id)
            ON UPDATE CASCADE ON DELETE RESTRICT
);
