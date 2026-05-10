-- Active: 1778441286099@@gondola.proxy.rlwy.net@25356@bootcamp_project_db@public
-- ============================================
-- 01: Create Database and Application User
-- Run this script as the PostgreSQL superuser (postgres).
-- ============================================

CREATE DATABASE bootcamp_project_db;

CREATE USER bootcamp_app_user WITH PASSWORD 'ChangeMe123!';

GRANT CONNECT ON DATABASE bootcamp_project_db TO bootcamp_app_user;



GRANT USAGE, CREATE ON SCHEMA public TO bootcamp_app_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bootcamp_app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bootcamp_app_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL PRIVILEGES ON TABLES TO bootcamp_app_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL PRIVILEGES ON SEQUENCES TO bootcamp_app_user;
