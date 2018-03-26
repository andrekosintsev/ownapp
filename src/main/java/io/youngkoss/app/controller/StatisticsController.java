package io.youngkoss.app.controller;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.youngkoss.app.jpa.domain.Statistic;
import io.youngkoss.app.jpa.repo.TransactionRepository;
import io.youngkoss.app.mapper.ResponseMapper;

@RestController
public class StatisticsController {
   @Autowired
   private TransactionRepository statisticRepository;
   @Autowired
   private ResponseMapper respMapper;

   @Value("${io.youngkoss.statistic.interval.ms}")
   private int interval;

   @RequestMapping("/statistics")
   public ResponseEntity<Statistic> getStatistics() throws Exception {
      // throw new RuntimeException("Here is a runtime");
      final long now = Calendar.getInstance(TimeZone.getTimeZone("UTC")) //$NON-NLS-1$
            .getTimeInMillis();
      return new ResponseEntity<>(respMapper.mapAggregateToStatistic(statisticRepository.getStatistics(now - interval, now)), HttpStatus.OK);

   }
}
