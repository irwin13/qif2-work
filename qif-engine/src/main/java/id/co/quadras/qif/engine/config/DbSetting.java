package id.co.quadras.qif.engine.config;

import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

/**
 * @author irwin Timestamp : 22/08/2014 16:05
 */
public class DbSetting {

    private boolean cacheEnabled;
    private boolean lazyLoadingEnabled;
    private boolean multipleResultSetsEnabled;
    private boolean useColumnLabel;
    private boolean useGeneratedKeys;
    private boolean safeRowBoundsEnabled;
    private boolean mapUnderscoreToCamelCase;

    @NotNull
    private String autoMappingBehavior;
    @NotNull
    private String defaultExecutorType;
    @NotNull
    private String localCacheScope;
    @NotNull
    private String jdbcTypeForNull;
    @NotNull
    private String lazyLoadTriggerMethods;
    @NotNull
    private String logPrefix;

    private String defaultStatementTimeout;

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    public boolean isMultipleResultSetsEnabled() {
        return multipleResultSetsEnabled;
    }

    public void setMultipleResultSetsEnabled(boolean multipleResultSetsEnabled) {
        this.multipleResultSetsEnabled = multipleResultSetsEnabled;
    }

    public boolean isUseColumnLabel() {
        return useColumnLabel;
    }

    public void setUseColumnLabel(boolean useColumnLabel) {
        this.useColumnLabel = useColumnLabel;
    }

    public boolean isUseGeneratedKeys() {
        return useGeneratedKeys;
    }

    public void setUseGeneratedKeys(boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
    }

    public boolean isSafeRowBoundsEnabled() {
        return safeRowBoundsEnabled;
    }

    public void setSafeRowBoundsEnabled(boolean safeRowBoundsEnabled) {
        this.safeRowBoundsEnabled = safeRowBoundsEnabled;
    }

    public boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    public String getAutoMappingBehavior() {
        return autoMappingBehavior;
    }

    public void setAutoMappingBehavior(String autoMappingBehavior) {
        this.autoMappingBehavior = autoMappingBehavior;
    }

    public String getDefaultExecutorType() {
        return defaultExecutorType;
    }

    public void setDefaultExecutorType(String defaultExecutorType) {
        this.defaultExecutorType = defaultExecutorType;
    }

    public String getLocalCacheScope() {
        return localCacheScope;
    }

    public void setLocalCacheScope(String localCacheScope) {
        this.localCacheScope = localCacheScope;
    }

    public String getJdbcTypeForNull() {
        return jdbcTypeForNull;
    }

    public void setJdbcTypeForNull(String jdbcTypeForNull) {
        this.jdbcTypeForNull = jdbcTypeForNull;
    }

    public String getLazyLoadTriggerMethods() {
        return lazyLoadTriggerMethods;
    }

    public void setLazyLoadTriggerMethods(String lazyLoadTriggerMethods) {
        this.lazyLoadTriggerMethods = lazyLoadTriggerMethods;
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public String getDefaultStatementTimeout() {
        return defaultStatementTimeout;
    }

    public void setDefaultStatementTimeout(String defaultStatementTimeout) {
        this.defaultStatementTimeout = defaultStatementTimeout;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("cacheEnabled", cacheEnabled)
                .add("lazyLoadingEnabled", lazyLoadingEnabled)
                .add("multipleResultSetsEnabled", multipleResultSetsEnabled)
                .add("useColumnLabel", useColumnLabel)
                .add("useGeneratedKeys", useGeneratedKeys)
                .add("safeRowBoundsEnabled", safeRowBoundsEnabled)
                .add("mapUnderscoreToCamelCase", mapUnderscoreToCamelCase)
                .add("autoMappingBehavior", autoMappingBehavior)
                .add("defaultExecutorType", defaultExecutorType)
                .add("localCacheScope", localCacheScope)
                .add("jdbcTypeForNull", jdbcTypeForNull)
                .add("lazyLoadTriggerMethods", lazyLoadTriggerMethods)
                .add("logPrefix", logPrefix)
                .add("defaultStatementTimeout", defaultStatementTimeout)
                .toString();
    }
}
