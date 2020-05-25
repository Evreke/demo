alter table if exists movie_sessions
    alter column started_at set data type timestamp;

alter table if exists movie_sessions
    alter column ended_at set data type timestamp;

alter table if exists movie_sessions
    drop column date;