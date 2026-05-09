-- Active: 1772299351599@@127.0.0.1@5432@bootcamp_project_db@public
-- ============================================
-- 02: Create Tables
-- Run this script connected to bootcamp_project_db.
-- ============================================

CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_board (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    owner_id BIGINT NOT NULL REFERENCES app_user(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task_item (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    due_date DATE,
    board_id BIGINT NOT NULL REFERENCES project_board(id),
    created_by BIGINT NOT NULL REFERENCES app_user(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
