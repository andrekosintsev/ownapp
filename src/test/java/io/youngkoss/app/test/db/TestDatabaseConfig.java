package io.youngkoss.app.test.db;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = { "io.youngkoss.app.jpa.repo" })
@PropertySource("classpath:application.properties")
public class TestDatabaseConfig {

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
      final StringBuilder urlHostInit = new StringBuilder();
      urlHostInit.append(jdbcPrefix);
      urlHostInit.append(dbhost);
      urlHostInit.append(":"); //$NON-NLS-1$
      urlHostInit.append(dbport);
      urlHostInit.append("/"); //$NON-NLS-1$
      urlHostInit.append(database_init);
      urlHostInit.append(STRING_QUESTION_NOTREQUIRED);

      // INIT DB
      final DataSource dataSourceInit = DataSourceBuilder.create()
            .driverClassName(jdbcDriverName)
            .url(urlHostInit.toString())
            .username(dbuser)
            .password(dbpassword)
            .build();
      final Resource initSchemaCreate = new ClassPathResource(createDropDatabaseFile);
      final DatabasePopulator databasePopulatorCreate = new ResourceDatabasePopulator(initSchemaCreate);
      DatabasePopulatorUtils.execute(databasePopulatorCreate, dataSourceInit);
      dataSourceInit.getConnection()
            .close();

      // APPLY SCHEMA
      final StringBuilder urlHost = new StringBuilder();
      urlHost.append(jdbcPrefix);
      urlHost.append(dbhost);
      urlHost.append(":"); //$NON-NLS-1$
      urlHost.append(dbport);
      urlHost.append("/"); //$NON-NLS-1$
      urlHost.append(database);
      urlHost.append(STRING_QUESTION_NOTREQUIRED);

      final DataSource dataSourceUpdate = DataSourceBuilder.create()
            .driverClassName(jdbcDriverName)
            .url(urlHost.toString())
            .username(dbuser)
            .password(dbpassword)
            .build();

      DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(new ClassPathResource(applySchemaDatabaseFile)), dataSourceUpdate);

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
