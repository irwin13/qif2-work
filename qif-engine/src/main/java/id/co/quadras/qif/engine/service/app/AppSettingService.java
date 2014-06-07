package id.co.quadras.qif.engine.service.app;

import com.irwin13.winwork.basic.model.entity.app.AppSetting;

/**
 * @author irwin Timestamp : 07/06/2014 23:49
 */
public interface AppSettingService {
    public AppSetting selectByCode(String code);
}
