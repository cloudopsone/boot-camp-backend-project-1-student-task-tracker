-- Active: 1772299351599@@127.0.0.1@5432@bootcamp_project_db
-- ============================================
-- 03: Sample Data
-- Run this script connected to bootcamp_project_db.
-- ============================================

INSERT INTO app_user (full_name, email) VALUES
('Alice Johnson', 'alice@example.com'),
('Bob Smith', 'bob@example.com'),
('Charlie Brown', 'charlie@example.com');

INSERT INTO project_board (name, description, owner_id) VALUES
('Sprint 1', 'First sprint tasks', 1),
('Bug Fixes', 'Bug tracking board', 2);

INSERT INTO task_item (title, description, status, due_date, board_id, created_by) VALUES
('Setup project', 'Initialize Spring Boot and React projects', 'DONE', '2026-03-11', 1, 1),
('Create database schema', 'Write SQL scripts for table creation', 'DONE', '2026-03-11', 1, 1),
('Implement task API', 'Build CRUD endpoints for tasks', 'IN_PROGRESS', '2026-03-12', 1, 2),
('Design task list page', 'Create React component for task listing', 'OPEN', '2026-03-12', 1, 3),
('Fix login bug', 'Login form not validating email', 'OPEN', '2026-03-13', 2, 2);
