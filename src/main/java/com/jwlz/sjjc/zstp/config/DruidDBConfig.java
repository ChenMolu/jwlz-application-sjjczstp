//package com.jwlz.sjjc.zstp.config;
//
//import com.alibaba.druid.filter.stat.StatFilter;
//import com.alibaba.druid.pool.DruidDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.sql.SQLException;
//
///**
// * @ClassName: DruidDBConfig
// * @Description: DruidDBConfig类被@Configuration标注，用作配置信息；
// *               DataSource对象被@Bean声明，为Spring容器所管理， @Primary表示这里定义的DataSource将覆盖其他来源的DataSource。
// */
//@Configuration
//@EnableTransactionManagement
//public class DruidDBConfig implements TransactionManagementConfigurer {
//
//	private static final Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);
//
//	@Value("${spring.datasource.url}")
//	private String dbUrl;
//
//	@Value("${spring.datasource.username}")
//	private String username;
//
//	@Value("${spring.datasource.password}")
//	private String password;
//
//	@Value("${spring.datasource.driverClassName}")
//	private String driverClassName;
//
//	@Value("${spring.datasource.initialSize}")
//	private int initialSize;
//
//	@Value("${spring.datasource.minIdle}")
//	private int minIdle;
//
//	@Value("${spring.datasource.maxActive}")
//	private int maxActive;
//
//	@Value("${spring.datasource.maxWait}")
//	private int maxWait;
//
//	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
//	private int timeBetweenEvictionRunsMillis;
//
//	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
//	private int minEvictableIdleTimeMillis;
//
//	@Value("${spring.datasource.validationQuery}")
//	private String validationQuery;
//
//	@Value("${spring.datasource.testWhileIdle}")
//	private boolean testWhileIdle;
//
//	@Value("${spring.datasource.testOnBorrow}")
//	private boolean testOnBorrow;
//
//	@Value("${spring.datasource.testOnReturn}")
//	private boolean testOnReturn;
//
//	@Value("${spring.datasource.poolPreparedStatements}")
//	private boolean poolPreparedStatements;
//
//	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
//	private int maxPoolPreparedStatementPerConnectionSize;
//
//	@Value("${spring.datasource.filters}")
//	private String filters;
//
//	@Value("${spring.datasource.connectionProperties}")
//	private String connectionProperties;
//
//	@Bean // 声明其为Bean实例
//	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
//	public DruidDataSource dataSource() {
//		DruidDataSource datasource = new DruidDataSource();
//
//		datasource.setUrl(this.dbUrl);
//		datasource.setUsername(username);
//		datasource.setPassword(password);
//		datasource.setDriverClassName(driverClassName);
//
//		// configuration
//		datasource.setInitialSize(initialSize);
//		datasource.setMinIdle(minIdle);
//		datasource.setMaxActive(maxActive);
//		datasource.setMaxWait(maxWait);
//		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		datasource.setValidationQuery(validationQuery);
//		datasource.setTestWhileIdle(testWhileIdle);
//		datasource.setTestOnBorrow(testOnBorrow);
//		datasource.setTestOnReturn(testOnReturn);
//		datasource.setPoolPreparedStatements(poolPreparedStatements);
//		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//		try {
//			datasource.setFilters(filters);
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//		}
//		datasource.setConnectionProperties(connectionProperties);
//
//		return datasource;
//	}
//
//	@Bean
//	public StatFilter statFilter() {
//		StatFilter statFilter = new StatFilter();
//		statFilter.setLogSlowSql(true);
//		statFilter.setSlowSqlMillis(0L);
//		statFilter.setMergeSql(true);
//		return statFilter;
//	}
//
//	@Bean
//	@ConditionalOnMissingBean
//	public TransactionTemplate transactionTemplate(DataSourceTransactionManager transactionManager) {
//		return new TransactionTemplate(transactionManager);
//	}
//
//	@Bean
//	@ConditionalOnMissingBean
//	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		return new DataSourceTransactionManager(dataSource());
//	}
//
//	@Bean
//	public JdbcTemplate jdbcTemplate(DruidDataSource dataSource) {
//		return new JdbcTemplate(dataSource);
//	}
//}
