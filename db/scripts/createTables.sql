-- main tables

create table if not exists hall (
	id serial primary key,
	row int,
	number int,
	isEmpty boolean,
	price int
);

create table if not exists accounts (
	id serial primary key,
	name varchar(30),
	phone varchar (30)
);

create table if not exists tickets (
	id serial primary key,
	account_id int references accounts(id),
	place_id int references hall(id)
);
