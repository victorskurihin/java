-- cm ----------------------------------------------------------------
-- Groups
CREATE TABLE IF NOT EXISTS cm_group (
  group_id          BIGINT       NOT NULL AUTO_INCREMENT,
  name              VARCHAR(255) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  admin_user_id     BIGINT REFERENCES cm_user (user_id),
  UNIQUE (name),
  PRIMARY KEY (group_id)
);

-- Users
CREATE TABLE IF NOT EXISTS cm_user (
  user_id           BIGINT       NOT NULL AUTO_INCREMENT,
  name              VARCHAR(255) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  primary_group_id  BIGINT REFERENCES cm_group (group_id),
  UNIQUE (name),
  PRIMARY KEY (user_id)
);

-- Administrators
CREATE TABLE IF NOT EXISTS cm_admin (
  admin_user_id            BIGINT REFERENCES cm_user (user_id),
  admin_group_id           BIGINT REFERENCES cm_group (group_id)
);

-- Configuration types
CREATE TABLE IF NOT EXISTS cm_ctype (
  type_id           BIGINT       NOT NULL AUTO_INCREMENT,
  name              VARCHAR(255) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  UNIQUE (name),
  PRIMARY KEY (type_id)
);

-- Configuration units
CREATE TABLE IF NOT EXISTS cm_cunit (
  cunit_id          BIGINT       NOT NULL AUTO_INCREMENT,
  name              VARCHAR(128) NOT NULL,
  environ           ENUM('prod', 'test', 'dev'),
  description       VARCHAR(254) NOT NULL,
  admin_user_id     BIGINT REFERENCES cm_admin (admin_user_id),
  group_id          BIGINT REFERENCES cm_group (group_id),
  owner_user_id     BIGINT REFERENCES cm_user (user_id),
  type_id           BIGINT REFERENCES cm_ctype (type_id),
  UNIQUE (name),
  PRIMARY KEY (cunit_id)
);

-- pm ----------------------------------------------------------------
-- Messages
CREATE TABLE IF NOT EXISTS pm_message (
  message_id               BIGINT       NOT NULL AUTO_INCREMENT,
  message_text             VARCHAR(254) NOT NULL,
  PRIMARY KEY (message_id)
);

-- Tasks
CREATE TABLE IF NOT EXISTS pm_task (
  task_id           BIGINT       NOT NULL AUTO_INCREMENT,
  title             VARCHAR(128) NOT NULL,
  text              VARCHAR(254) NOT NULL,
  consumer_user_id  BIGINT REFERENCES cm_user (user_id),
  PRIMARY KEY (task_id)
);

-- Task actors
CREATE TABLE IF NOT EXISTS pm_task_actor (
  task_id           BIGINT REFERENCES pm_task (task_id),
  user_id           BIGINT REFERENCES cm_user (user_id)
);

-- Task actuaries
CREATE TABLE IF NOT EXISTS pm_task_actuary (
  task_id           BIGINT REFERENCES pm_task (task_id),
  user_id           BIGINT REFERENCES cm_user (user_id)
);

-- Task records
CREATE TABLE IF NOT EXISTS pm_task_record (
  task_id           BIGINT REFERENCES pm_task (task_id),
  message_id        BIGINT REFERENCES pm_message (message_id)
);

-- Incidents
CREATE TABLE IF NOT EXISTS pm_incident (
  incident_id       BIGINT       NOT NULL AUTO_INCREMENT,
  title             VARCHAR(128) NOT NULL,
  text              VARCHAR(254) NOT NULL,
  consumer_user_id  BIGINT REFERENCES cm_user (user_id),
  PRIMARY KEY (task_id)
);

-- Incident records
CREATE TABLE IF NOT EXISTS pm_inc_record (
  incident_id       BIGINT REFERENCES pm_incident (incident_id),
  message_id        BIGINT REFERENCES pm_message (message_id)
);

------------------------------------------------------------------------
