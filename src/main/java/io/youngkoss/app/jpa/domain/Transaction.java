package io.youngkoss.app.jpa.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ntwentysix.transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
   private static final long serialVersionUID = -1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "transaction_id")
   private int transactionId;
   @Column(name = "amount")
   private BigDecimal amount;
   @Column(name = "transaction_time")
   private long transactionTime;
}
