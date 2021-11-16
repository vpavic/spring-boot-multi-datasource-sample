package sample;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class MultiDataSourceSampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceSampleApplication.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(MultiDataSourceSampleApplication.class);

    @Autowired
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource;

    @Override
    public void run(String... args) throws Exception {
        logDataSourceMetadata("primaryDataSource", this.primaryDataSource);
        logDataSourceMetadata("secondaryDataSource", this.secondaryDataSource);
    }

    private static void logDataSourceMetadata(String dataSourceName, DataSource dataSource) throws Exception {
        logger.info("{}: {}", dataSourceName, dataSource.getConnection().getMetaData());
    }

}
