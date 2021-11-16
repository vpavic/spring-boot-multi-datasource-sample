package sample;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

// tag::configuration-tests[]
class DataSourceConfigurationTests {

    private static final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
            .withUserConfiguration(DataSourceConfiguration.class);

    @Test
    void defaultConfiguration() {
        contextRunner.run(context -> assertThat(context.getBeansOfType(DataSource.class)).hasSize(2));
    }

    @Test
    void primaryDataSourceConfiguration() {
        contextRunner.withPropertyValues("sample.datasource.primary.url=jdbc:h2:mem:primary",
                        "sample.datasource.primary.configuration.maximum-pool-size=1")
                .run(context -> {
                    assertThat(context.getBeansOfType(DataSource.class)).hasSize(2);
                    HikariDataSource primaryDataSource = context.getBean(HikariDataSource.class);
                    assertThat(primaryDataSource.getJdbcUrl()).isEqualTo("jdbc:h2:mem:primary");
                    assertThat(primaryDataSource.getMaximumPoolSize()).isEqualTo(1);
                });
    }

    @Test
    void secondaryDataSourceConfiguration() {
        contextRunner.withPropertyValues("sample.datasource.secondary.url=jdbc:h2:mem:secondary",
                        "sample.datasource.secondary.configuration.maximum-pool-size=1")
                .run(context -> {
                    assertThat(context.getBeansOfType(DataSource.class)).hasSize(2);
                    HikariDataSource secondaryDataSource = context.getBean("secondaryDataSource",
                            HikariDataSource.class);
                    assertThat(secondaryDataSource.getJdbcUrl()).isEqualTo("jdbc:h2:mem:secondary");
                    assertThat(secondaryDataSource.getMaximumPoolSize()).isEqualTo(1);
                });
    }

}
// end::configuration-tests[]
