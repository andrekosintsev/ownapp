package io.youngkoss.app.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import io.youngkoss.app.jpa.domain.Statistic;

@Component
public class ResponseMapper {

   public Statistic mapAggregateToStatistic(List<Object[]> aggergateResult) {
      if ((null == aggergateResult) || aggergateResult.isEmpty()) {
         return null;
      }
      final Object[] arrayElements = aggergateResult.get(0);
      return Statistic.builder()
            .sum((null != arrayElements[0]) ? (BigDecimal) arrayElements[0] : new BigDecimal(0))
            .avg((null != arrayElements[1]) ? (BigDecimal) arrayElements[1] : new BigDecimal(0))
            .max((null != arrayElements[2]) ? (BigDecimal) arrayElements[2] : new BigDecimal(0))
            .min((null != arrayElements[3]) ? (BigDecimal) arrayElements[3] : new BigDecimal(0))
            .count((BigInteger) arrayElements[4])
            .build();
   }

}
