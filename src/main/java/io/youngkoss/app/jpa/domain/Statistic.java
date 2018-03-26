package io.youngkoss.app.jpa.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statistic implements Serializable {
   private static final long serialVersionUID = -1L;
   private BigDecimal sum;
   private BigDecimal avg;
   private BigDecimal max;
   private BigDecimal min;
   private BigInteger count;

}