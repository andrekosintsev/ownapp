package io.youngkoss.app.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.youngkoss.app.jpa.domain.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
   //@formatter:off
   @Query(value = "SELECT "
         + "SUM(amount) as sum, "
         + "AVG (amount) as average, "
         + "MAX(amount) as max, "
         + "MIN(amount) as min, "
         + "COUNT(amount) as count "
         + "FROM ntwentysix.transaction "
         + "WHERE "
         + "transaction_time >= :timestampFrom "
         + "AND "
         + "transaction_time <= :timestampTo", nativeQuery=true)
   //@formatter:on
   List<Object[]> getStatistics(@Param("timestampFrom") Long timestampFrom, @Param("timestampTo") Long timestampTo);
}
