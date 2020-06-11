package cn.com.capinfo.ea.example.demo.conf;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.beans.factory.annotation.Autowired

@Configuration
@EnableAutoConfiguration
class DemoApplicationConfig implements WebMvcConfigurer, TransactionManagementConfigurer {
    @Autowired
    private HibernateDatastore hibernateDatastore;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return hibernateDatastore.transactionManager;
    }
}
