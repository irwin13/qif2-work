package id.co.quadras.qif.engine.dao.app;

import com.irwin13.winwork.basic.model.entity.app.AppSetting;

import java.util.List;

/**
 * @author irwin Timestamp : 07/06/2014 23:47
 */
public interface AppSettingDao {
    public List<AppSetting> select(AppSetting filter);
}
