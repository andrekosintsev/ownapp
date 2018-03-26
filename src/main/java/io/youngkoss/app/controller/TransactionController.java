package io.youngkoss.app.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.youngkoss.app.jpa.domain.Transaction;
import io.youngkoss.app.jpa.repo.TransactionRepository;
import io.youngkoss.app.mapper.RequestBodyTransaction;

@SuppressWarnings("nls")
@RestController
public class TransactionController {
   @Autowired
   private TransactionRepository transactionRepository;

   @Value("${io.youngkoss.statistic.interval.ms}")
   private int interval;

   @RequestMapping(method = RequestMethod.POST, value = "/transactions")

   public ResponseEntity<?> getTransaction(@RequestBody RequestBodyTransaction request) throws Exception {
      if (request == null) {
         throw new RuntimeException("Request should not be null"); //$NON-NLS-1$
      }
      if (Long.valueOf(request.getTimestamp()) == null || Long.valueOf(request.getTimestamp()) == 0L) {
         throw new RuntimeException("Timestamp Should be specified and properly not null not 0"); //$NON-NLS-1$
      }
      if (Double.valueOf(request.getAmount()) == null) {
         throw new RuntimeException("Amount value is wrong"); //$NON-NLS-1$
      }
      if (request.getTimestamp() < (Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            .getTimeInMillis() - interval)) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      transactionRepository.save(Transaction.builder()
            .amount(new BigDecimal(request.getAmount()))
            .transactionTime(Long.valueOf(request.getTimestamp()))
            .build());
      return new ResponseEntity<>(HttpStatus.CREATED);

   }

}
