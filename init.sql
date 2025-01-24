CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS parceiro (
    id SERIAL PRIMARY KEY,
    trading_name VARCHAR(255) NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    document VARCHAR(50) NOT NULL,
    coverage_area GEOMETRY(MultiPolygon, 4326),
    address GEOMETRY(Point, 4326)
);
