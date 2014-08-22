package id.co.quadras.qif.engine.config;

import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

/**
 * @author irwin Timestamp ; 22/08/2014 16;05
 */
public class JdbcDataSource {

    @NotNull
    private String url;
    @NotNull
    private String driver;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String validationQuery;
    @NotNull
    private String jdbcInterceptors;
    @NotNull
    private String maxWait;

    private int initialSize;
    private int maxActive;
    private int maxIdle;
    private int minIdle;
    private int minEvictableIdleTimeMillis;
    private int validationInterval;
    private int timeBetweenEvictionRunsMillis;
    private int  removeAbandonedTimeout;

    private boolean testOnBorrow;
    private boolean jmxEnabled;
    private boolean testWhileIdle;
    private boolean testOnReturn;
    private boolean logAbandoned;
    private boolean removeAbandoned;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public String getJdbcInterceptors() {
        return jdbcInterceptors;
    }

    public void setJdbcInterceptors(String jdbcInterceptors) {
        this.jdbcInterceptors = jdbcInterceptors;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public int getValidationInterval() {
        return validationInterval;
    }

    public void setValidationInterval(int validationInterval) {
        this.validationInterval = validationInterval;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isJmxEnabled() {
        return jmxEnabled;
    }

    public void setJmxEnabled(boolean jmxEnabled) {
        this.jmxEnabled = jmxEnabled;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isLogAbandoned() {
        return logAbandoned;
    }

    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("url", url)
                .add("driver", driver)
                .add("username", username)
                .add("password", password)
                .add("validationQuery", validationQuery)
                .add("jdbcInterceptors", jdbcInterceptors)
                .add("initialSize", initialSize)
                .add("maxActive", maxActive)
                .add("maxIdle", maxIdle)
                .add("minIdle", minIdle)
                .add("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis)
                .add("validationInterval", validationInterval)
                .add("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis)
                .add("maxWait", maxWait)
                .add("removeAbandonedTimeout", removeAbandonedTimeout)
                .add("testOnBorrow", testOnBorrow)
                .add("jmxEnabled", jmxEnabled)
                .add("testWhileIdle", testWhileIdle)
                .add("testOnReturn", testOnReturn)
                .add("logAbandoned", logAbandoned)
                .add("removeAbandoned", removeAbandoned)
                .toString();
    }
}
