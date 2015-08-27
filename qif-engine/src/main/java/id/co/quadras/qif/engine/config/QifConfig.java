package id.co.quadras.qif.engine.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author irwin Timestamp : 22/08/2014 15:51
 */
public class QifConfig extends Configuration {

    private int qifTaskMaxConcurrent;
    private boolean internalLogActive;

    @NotNull
    private String transactionManager;

    @Valid
    @NotNull
    @JsonProperty("batchConfig")
    private BatchConfig batchConfig = new BatchConfig();

    @Valid
    @NotNull
    @JsonProperty("dbSetting")
    private DbSetting dbSetting = new DbSetting();

    @Valid
    @NotNull
    @JsonProperty("rootPackage")
    private RootPackage rootPackage = new RootPackage();

    @Valid
    @NotNull
    @JsonProperty("jdbcDataSource")
    private JdbcDataSource jdbcDataSource = new JdbcDataSource();

    @Valid
    @NotNull
    @JsonProperty("jndiDataSource")
    private JndiDataSource jndiDataSource = new JndiDataSource();

    public int getQifTaskMaxConcurrent() {
        return qifTaskMaxConcurrent;
    }

    public void setQifTaskMaxConcurrent(int qifTaskMaxConcurrent) {
        this.qifTaskMaxConcurrent = qifTaskMaxConcurrent;
    }

    public String getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(String transactionManager) {
        this.transactionManager = transactionManager;
    }

    public BatchConfig getBatchConfig() {
        return batchConfig;
    }

    public void setBatchConfig(BatchConfig batchConfig) {
        this.batchConfig = batchConfig;
    }

    public DbSetting getDbSetting() {
        return dbSetting;
    }

    public void setDbSetting(DbSetting dbSetting) {
        this.dbSetting = dbSetting;
    }

    public RootPackage getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(RootPackage rootPackage) {
        this.rootPackage = rootPackage;
    }

    public JdbcDataSource getJdbcDataSource() {
        return jdbcDataSource;
    }

    public void setJdbcDataSource(JdbcDataSource jdbcDataSource) {
        this.jdbcDataSource = jdbcDataSource;
    }

    public JndiDataSource getJndiDataSource() {
        return jndiDataSource;
    }

    public void setJndiDataSource(JndiDataSource jndiDataSource) {
        this.jndiDataSource = jndiDataSource;
    }

    public boolean isInternalLogActive() {
        return internalLogActive;
    }

    public void setInternalLogActive(boolean internalLogActive) {
        this.internalLogActive = internalLogActive;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("qifTaskMaxConcurrent", qifTaskMaxConcurrent)
                .add("transactionManager", transactionManager)
                .add("batchConfig", batchConfig)
                .add("dbSetting", dbSetting)
                .add("rootPackage", rootPackage)
                .add("jdbcDataSource", jdbcDataSource)
                .add("jndiDataSource", jndiDataSource)
                .toString();
    }
}
