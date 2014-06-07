package id.co.quadras.qif.engine.service.app.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import id.co.quadras.qif.engine.dao.app.AppSettingDao;
import id.co.quadras.qif.engine.service.app.AppSettingService;

import java.util.List;

/**
 * @author irwin Timestamp : 07/06/2014 23:50
 */
public class AppSettingServiceImp implements AppSettingService {

    private final AppSettingDao dao;

    @Inject
    public AppSettingServiceImp(AppSettingDao dao) {
        this.dao = dao;
    }

    @Override
    public AppSetting selectByCode(String code) {
        AppSetting appSetting = null;

        AppSetting filter = new AppSetting();
        filter.setActive(Boolean.TRUE);
        filter.setCode(code);

        List<AppSetting> appSettingList = dao.select(filter);
        if (appSettingList != null && !appSettingList.isEmpty()) {
            appSetting = appSettingList.get(0);
        }
        return appSetting;
    }
}
