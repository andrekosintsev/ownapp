package io.youngkoss.app;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = { "io.youngkoss.app.jpa.repo" })
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

   private static final String COM_INGENIOUS_TECHNOLOGIES_JPA = "io.youngkoss.app.jpa"; //$NON-NLS-1$

   private static final String STRING_QUESTION_NOTREQUIRED = "?"; //$NON-NLS-1$

   @Value("${test.db.prefix}")
   private String jdbcPrefix;

   @Value("${test.db.driver}")
   private String jdbcDriverName;

   @Value("${database}")
   private String database;

   @Value("${dbuser}")
   private String dbuser;

   @Value("${dbpassword}")
   private String dbpassword;

   @Value("${dbhost}")
   private String dbhost;

   @Value("${dbport}")
   private String dbport;

   @Value("${database_init}")
   private String database_init;

   @Value("${test.db.create.path}")
   private String createDropDatabaseFile;

   @Value("${test.db.shema.path}")
   private String applySchemaDatabaseFile;

   @Primary
   @Bean(name = "dataSource")
   public DataSource dataSourceUpd() throws Exception {
      // APPLY SCHEMA
      StringBuilder urlHost = new StringBuilder();
      urlHost.append(jdbcPrefix);
      urlHost.append(dbhost);
      urlHost.append(":"); //$NON-NLS-1$
      urlHost.append(dbport);
      urlHost.append("/"); //$NON-NLS-1$
      urlHost.append(database);
      urlHost.append(STRING_QUESTION_NOTREQUIRED);

      DataSource dataSourceUpdate = DataSourceBuilder.create()
            .driverClassName(jdbcDriverName)
            .url(urlHost.toString())
            .username(dbuser)
            .password(dbpassword)
            .build();

      return dataSourceUpdate;
   }

   @Primary
   @Bean(name = "entityManagerFactory")
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
      return builder.dataSource(dataSource)
            .packages(COM_INGENIOUS_TECHNOLOGIES_JPA)
            .persistenceUnit("pgsql") //$NON-NLS-1$
            .build();
   }

   @Primary
   @Bean(name = "transactionManager")
   public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
      return new JpaTransactionManager(entityManagerFactory);
   }

}
