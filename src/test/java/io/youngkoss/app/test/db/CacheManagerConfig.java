package io.youngkoss.app.test.db;

import javax.cache.CacheManager;
import javax.cache.Caching;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheManagerConfig {

   private static final Logger LOGGER = LoggerFactory.getLogger(CacheManagerConfig.class.getName());

   @Bean
   public CacheManager cacheManager() {
      EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();
      CacheManager manager = null;
      String xmlClassPath = System.getProperty("jsr107.config.classpath", "ehcache.xml"); //$NON-NLS-1$//$NON-NLS-2$
      try {
         manager = provider.getCacheManager(Thread.currentThread()
               .getContextClassLoader()
               .getResource(xmlClassPath)
               .toURI(),
               Thread.currentThread()
                     .getContextClassLoader());
      } catch (Exception e) {
         LOGGER.error(e.getMessage(), e);
         manager = null;
      }
      return manager;
   }

}
