alter table if exists bookings
    add column total_price decimal;

alter table if exists movie_sessions
    add column price decimal;