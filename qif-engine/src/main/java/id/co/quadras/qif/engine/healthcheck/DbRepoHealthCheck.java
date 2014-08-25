package id.co.quadras.qif.engine.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import id.co.quadras.qif.engine.service.app.AppSettingService;

/**
 * @author irwin Timestamp : 25/08/2014 17:05
 */
public class DbRepoHealthCheck extends HealthCheck {

    private static final String TEST = "TEST";

    private final AppSettingService service;

    public DbRepoHealthCheck(AppSettingService service) {
        this.service = service;
    }

    @Override
    protected Result check() throws Exception {
        service.selectByCode(TEST);
        return Result.healthy();
    }
}
