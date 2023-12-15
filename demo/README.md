
## Создание таблицы с использованием партиционирования:
```
    CREATE TABLE measurement (
    city_id         int not null,
    logdate         date not null,
    peaktemp        int,
    unitsales       int
) PARTITION BY RANGE (logdate);
```
### Запросы:
## а)вставки данных:
```
        INSERT INTO public.measurement(
	         city_id, logdate, peaktemp, unitsales)
	      VALUES (1, '2006-03-16', 1, 1);
```
## б)1.добавления партиций:
```
      CREATE TABLE measurement_y2006m02 PARTITION OF measurement
          FOR VALUES FROM ('2006-02-01') TO ('2006-03-01');
      CREATE TABLE measurement_y2006m03 PARTITION OF measurement
          FOR VALUES FROM ('2006-03-01') TO ('2006-04-01');
```
          
## б)2. удаления партиций:
```
        DROP TABLE measurement_y2006m02;
```
        
## в)создания  локальных индексов:
```
         CREATE INDEX ON measurement_y2006m02 (logdate)
         CREATE INDEX ON measurement_y2006m03 (logdate)
```
         
## г)выборки данных и использованием индексов:
```
        SELECT * FROM measurement WHERE logdate >= DATE '2006-01-01';
        SELECT * FROM measurement_y2006m03 WHERE logdate >= DATE '2006-01-01';
```

