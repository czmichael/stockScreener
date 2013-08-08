select * from finance.stock;
select * from finance.stock where symbol like '%KALU%';
select * from finance.asset where stock_id=3940;
select count(*) from finance.stock;
select * from finance.asset where stock_id in (select id from finance.stock where symbol like '%AEL%');

update finance.stock set symbol='ADDF' where id=168;
