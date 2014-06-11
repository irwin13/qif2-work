package id.co.quadras.qif.engine.dao.app.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.app.AppSettingDao;
import id.co.quadras.qif.engine.sqlmap.app.AppSettingSqlmap;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 07/06/2014 23:48
 */
public class AppSettingDaoImp implements AppSettingDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<AppSetting, String> basicDao;

    @Inject
    public AppSettingDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<AppSetting, String>(AppSetting.class, sqlSessionFactory,
                AppSettingSqlmap.class.getName());
    }


    @Override
    public List<AppSetting> select(AppSetting filter) {
        return basicDao.select(filter, null);
    }
}
