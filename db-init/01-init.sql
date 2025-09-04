-- 01-init.sql (runs only on first container startup)

-- Create DBs (safe: won't error if they already exist)
CREATE DATABASE users_db;
CREATE DATABASE matches_db;
CREATE DATABASE tickets_db;
CREATE DATABASE notifications_db;

-- Create roles (safe with IF NOT EXISTS via DO block)
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'users_owner') THEN
CREATE ROLE users_owner LOGIN PASSWORD 'users_pwd';
END IF;

   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'matches_owner') THEN
CREATE ROLE matches_owner LOGIN PASSWORD 'matches_pwd';
END IF;

   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'tickets_owner') THEN
CREATE ROLE tickets_owner LOGIN PASSWORD 'tickets_pwd';
END IF;

   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'notifs_owner') THEN
CREATE ROLE notifs_owner LOGIN PASSWORD 'notifs_pwd';
END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE users_db TO users_owner;
GRANT ALL PRIVILEGES ON DATABASE matches_db TO matches_owner;
GRANT ALL PRIVILEGES ON DATABASE tickets_db TO tickets_owner;
GRANT ALL PRIVILEGES ON DATABASE notifications_db TO notifs_owner;

-- Give schema ownership for each DB
\c users_db
ALTER SCHEMA public OWNER TO users_owner;

\c matches_db
ALTER SCHEMA public OWNER TO matches_owner;

\c tickets_db
ALTER SCHEMA public OWNER TO tickets_owner;

\c notifications_db
ALTER SCHEMA public OWNER TO notifs_owner;
