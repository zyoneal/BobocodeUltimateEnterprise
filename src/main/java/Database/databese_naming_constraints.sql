create table persons (
    id bigserial primary key,
    first_name text not null,
    last_name text not null,
    email text not null unique
);

create table notes (
    id bigserial primary key,
    title text not null,
    body text,
    person_id bigint references persons      //     constraint foreign key (person_id) references persons(id)

);
// --- Need to name constraint!!! Example:
alter table notes drop constraint ...???

// --- get name constraints
select * from pg_constraint cn inner join pg_class pc on cn.conrelid = pc.oid where relname = 'notes';

---
insert into notes (title,body,person_id) values ('Test note', 'This is invalid note', 666);

update pg_constraint set conname = 'UPDATED_NOTES_TO_PERSONS_FK' where oid = 16558;

alter table notes rename constraint "UPDATED_NOTES_TO_PERSONS_FK" to notes_persons_fk;

