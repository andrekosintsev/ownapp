package io.youngkoss.app.mapper;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyTransaction implements Serializable {

   private static final long serialVersionUID = -1L;
   private double amount;
   private long timestamp;
}
