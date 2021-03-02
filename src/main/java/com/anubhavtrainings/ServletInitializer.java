package com.anubhavtrainings;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.anubhavtrainings.odata.VendorODataAgent;
import com.anubhavtrainings.processor.MyODataServiceFactory;
import com.anubhavtrainings.SimpleODataServlet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean emf(JpaVendorAdapter adapter, DataSource ds) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPackagesToScan("com.anubhavtrainings");
		factory.setJpaVendorAdapter(adapter);
		factory.setJtaDataSource(ds);
		return factory;
	}
	
	@Bean 
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Bean(name="AnubhavODataServiceFactory")
	public ODataServiceFactory AnubhavServiceFactory(){
		return new MyODataServiceFactory("com.anubhavtrainings.entities");
	}
	
	
	@Bean(name="com.anubhavtrainings.bean.VendorODataAgent")
	public VendorODataAgent VendorODataAgent(){
		log.info("return VendorODataAgent object");
		return new VendorODataAgent();
	}
	
	
	@WebServlet(urlPatterns = { "/anubhav.svc/*" })
	class AnubhavODataServlet extends SimpleODataServlet {

		private static final long serialVersionUID = 1L;

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			super.init(servletConfig);
			setoDataServiceFactoryBeanName("AnubhavODataServiceFactory");
		}
	}

}
