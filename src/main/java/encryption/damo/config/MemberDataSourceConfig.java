package encryption.damo.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Profile(value = {"dev", "stg", "member"})
@Configuration
@ConfigurationProperties(prefix = "datasource.member")
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMember",
        transactionManagerRef = "transactionManagerMember",
        basePackages = {"encryption.damo.repository.member"})
public class MemberDataSourceConfig extends HikariConfig {

    @Bean
    public DataSource dataSourceMember() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(this));
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryMember() {

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(this.dataSourceMember());
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaPropertyMap(ImmutableMap.of(
                "hibernate.hdm2ddl.auto", "none",
                "hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect",
                "hibernate.show_sql", "false"
        ));
        factory.setPackagesToScan("encryption.damo.model.member");
        factory.setPersistenceUnitName("member");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManagerMember() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(this.entityManagerFactoryMember());

        return manager;
    }
}
