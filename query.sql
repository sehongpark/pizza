select * from authorities;

select * from users;

select * from ingredients;

insert into authorities (authority, user_id)
	values ('ROLE_ADMIN', 1);

delete from authorities
	where id = 3;


select * from orders;

select * from orders_pizzas;


select
	*
from
	pizzas as P
left join orders_pizzas as O 
	on P.id = O.pizzas_id
where
	order_id = 1;