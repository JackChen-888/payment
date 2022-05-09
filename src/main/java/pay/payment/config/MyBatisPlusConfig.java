package pay.payment.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Chen
 * 启用事务管理
 */
@Configuration
@MapperScan("pay.payment.mapper")
@EnableTransactionManagement
public class MyBatisPlusConfig {

}