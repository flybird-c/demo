package com.example.batchprocessing;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lzp
 * @version 1.0.0
 * @date 2022/5/17 18:10:23
 * @apiNote
 */
@Configuration
@Primary
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    public DataSource dataSource() {
        //如果冒号之后到结尾或者到问号这一段没有/,代表着没有指定数据库
        //正常来说,指定数据库直接拿/到?这一段就是数据库名
        String var1 = datasourceUrl.substring(0,datasourceUrl.indexOf("?"));
        String database = var1.substring(var1.lastIndexOf("/")+1);
        String linkUrl = var1.replace(database, "");

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName(database);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(linkUrl,username,password);
            statement = connection.createStatement();
            statement.executeUpdate("create database if not exists `" + database + "` default character set utf8 COLLATE utf8_general_ci");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataSource;
    }
}
