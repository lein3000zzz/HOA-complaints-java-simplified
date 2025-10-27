BEGIN;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'request_status') THEN
        CREATE TYPE request_status AS ENUM (
            'CREATED',
            'ASSIGNED',
            'COMPLETED',
            'CANCELLED',
            'SUSPENDED',
            'TRANSFERRED'
            );
    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'request_type') THEN
        CREATE TYPE request_type AS ENUM ('APARTMENT', 'HOUSE');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS residents (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(50),
    created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGSERIAL PRIMARY KEY,
    complaint_text TEXT NOT NULL,
    status request_status NOT NULL DEFAULT 'CREATED',
    type request_type NOT NULL DEFAULT 'APARTMENT',
    id_complaining BIGINT NOT NULL,
    house_address VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_requests_status ON requests(status);
CREATE INDEX IF NOT EXISTS idx_requests_id_complaining ON requests(id_complaining);
CREATE INDEX IF NOT EXISTS idx_requests_house_address ON requests(house_address);

COMMIT;