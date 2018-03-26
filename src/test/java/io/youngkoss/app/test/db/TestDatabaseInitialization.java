package io.youngkoss.app.test.db;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import io.youngkoss.app.jpa.domain.Transaction;
import io.youngkoss.app.jpa.repo.TransactionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDatabaseInitialization {

   @Autowired
   private TransactionRepository transactionRepository;

   private static Transaction.TransactionBuilder transaction;

   @BeforeClass
   public static void initTransaction() {
      transaction = Transaction.builder()
            .amount(new BigDecimal(12.0))
            .transactionTime(1478192204000L);
   }

   @Test
   @ExpectedDatabase(value = "/transaction_record.xml", table = "ntwentysix.transaction", assertionMode = DatabaseAssertionMode.NON_STRICT)
   public void aInitFirstValue() {
      transactionRepository.save(transaction.build());
   }

}
