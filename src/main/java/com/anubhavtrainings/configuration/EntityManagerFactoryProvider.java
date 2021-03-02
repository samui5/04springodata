package com.anubhavtrainings.configuration;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

public class EntityManagerFactoryProvider {

    /**
     * Based on the given {@link DataSource} instance, create and configure an EntityManagerFactory. Part of this
     * configuration is that the given packages are scanned for entity classes, so that the created EntityManagers know
     * about them.
     */
    public static LocalContainerEntityManagerFactoryBean get(DataSource dataSource, String... packagesToScan) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setPersistenceProvider(new PersistenceProvider() {
			
			@Override
			public ProviderUtil getProviderUtil() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean generateSchema(String persistenceUnitName, Map map) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void generateSchema(PersistenceUnitInfo info, Map map) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
				// TODO Auto-generated method stub
				return null;
			}
		});
        entityManagerFactoryBean.setPackagesToScan(packagesToScan);
        entityManagerFactoryBean.setDataSource(dataSource);

        // for JPA we use the classloader that Spring uses to avoid classloader issues
        entityManagerFactoryBean.setLoadTimeWeaver(new SimpleLoadTimeWeaver());
        entityManagerFactoryBean.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());

        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;
    }


}

