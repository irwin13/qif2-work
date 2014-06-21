package id.co.quadras.qif.ui.service.app.imp;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import com.irwin13.winwork.basic.utilities.SecurityUtil;
import id.co.quadras.qif.ui.dao.app.AppUserDao;
import id.co.quadras.qif.ui.service.app.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 14:39
 */
public class AppUserServiceImp implements AppUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserServiceImp.class);

    private final BasicEntityCommonService commonService;
    private final AppUserDao dao;

    @Inject
    public AppUserServiceImp(BasicEntityCommonService commonService, AppUserDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<AppUser> select(AppUser filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<AppUser> select(AppUser filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppUser filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<AppUser> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<AppUser> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public AppUser getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(AppUser model) {
        commonService.onInsert(model);
        if (Strings.isNullOrEmpty(model.getPassword())) {
            try {
                model.setPassword(SecurityUtil.createHash(WinWorkConstants.DEFAULT_PASSWORD, SecurityUtil.DEFAULT_HASH));
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }

        if (Strings.isNullOrEmpty(model.getDisplayLang())) {
            model.setDisplayLang(WinWorkConstants.DEFAULT_LANG);
        }
        return dao.insert(model);
    }

    @Override
    public void update(AppUser model) {
        commonService.onUpdate(model);
        dao.merge(model);
    }

    @Override
    public void softDelete(AppUser model) {
        commonService.onSoftDelete(model);
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(AppUser model) {
        commonService.onInsertOrUpdate(model) ;
        dao.saveOrUpdate(model);
    }
}
