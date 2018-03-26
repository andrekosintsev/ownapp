# Statistic app

I have finished this application not based on real requirements.
In case of I do not like to read full requirements at firsth time: I have lost the main moment, that this application should be done without db. Actually, when I have found in requirements that it should be done without db, I ve got a point that I need to use something from concurrent java package to make it thread safe not like ```@Transactional``` annotation for save method in repository. It should be something what has extend ```AtomicLong``` based on HotSpot new package ```LongAdder``` and ```LongAccumulator```, and plus any Deque from this package. Plus in case to count a sum, avarage, max, min I have found that it possible to implement to have Fewnik tree structure, which will give me ability to have ```O(4*log(n)) approximately. But I have been lazy to modify code, which I have done with db.)) Sorry.

Hence, if you still have an interest to check this implementation you can make the following steps:
1. Download this application
2. Install it via ```mvn clean install```, please make sure that you have configured everything in file properties:
```database_init=postgres
database=own
dbuser=postgres
dbpassword=
dbhost=localhost
dbport=5432
connect_timeout=5000
read_timeout=5000
test.db.create.path=db/create.sql
test.db.shema.path=db/schema.sql
test.db.prefix=jdbc:postgresql://
test.db.driver=org.postgresql.Driver
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
spring.cache.jcache.config=classpath:ehcache.xml
io.youngkoss.statistic.interval.ms=60000```
The same fo test resources




