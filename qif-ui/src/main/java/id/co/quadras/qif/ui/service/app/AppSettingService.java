package id.co.quadras.qif.ui.service.app;

import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.basic.service.WinWorkService;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 14:21
 *
 * Edit By : Nova, 02/05/13
 * Add method getByCategory
 */
public interface AppSettingService extends WinWorkService<AppSetting, String> {

    public AppSetting getByCode(String code);
    public List<AppSetting> getByCategory(String category);

    public String getSettingStringValue(String code);
    public int getSettingIntValue(String code);
}
