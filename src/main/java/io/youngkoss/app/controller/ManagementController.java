package io.youngkoss.app.controller;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagementController {
   @Autowired
   @Qualifier("dataSource")
   private DataSource mDataSource;

   @RequestMapping("/shutdown")
   @PreDestroy
   public void shutdown() throws Exception {
      mDataSource.getConnection()
            .close();
   }

}
