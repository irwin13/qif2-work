package id.co.quadras.qif.ui.service.config.imp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.KeyValue;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import com.irwin13.winwork.basic.utilities.RestClient;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.ui.dao.config.QifEventDao;
import id.co.quadras.qif.ui.service.app.AppSettingService;
import id.co.quadras.qif.ui.service.config.QifEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 14:00
 */
public class QifEventServiceImp implements QifEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEventServiceImp.class);

    @Inject
    private BasicEntityCommonService commonService;

    @Inject
    private QifEventDao dao;

    @Inject
    private RestClient restClient;

    @Inject
    private JsonParser jsonParser;

    @Inject
    private AppSettingService appSettingService;

    @Override
    public List<QifEvent> select(QifEvent filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<QifEvent> select(QifEvent filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(QifEvent filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<QifEvent> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<QifEvent> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public QifEvent getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(QifEvent model) {
        commonService.onInsert(model);
        if (model.getQifEventPropertyList() != null) {
            for (QifEventProperty property : model.getQifEventPropertyList()) {
                property.setQifEventId(model.getId());
                property.setCreateBy(model.getCreateBy());
                property.setLastUpdateBy(model.getLastUpdateBy());
                commonService.onInsert(property);
            }
        }
        return dao.insert(model);
    }

    @Override
    public void update(QifEvent model) {
        commonService.onUpdate(model);
        if (model.getQifEventPropertyList() != null) {
            for (QifEventProperty property : model.getQifEventPropertyList()) {
                property.setQifEventId(model.getId());
                property.setCreateBy(model.getCreateBy());
                property.setLastUpdateBy(model.getLastUpdateBy());
                commonService.onInsertOrUpdate(property);
            }
        }
        dao.merge(model);
    }

    @Override
    public void softDelete(QifEvent model) {
        commonService.onSoftDelete(model);
        if (model.getQifEventPropertyList() != null) {
            for (QifEventProperty property : model.getQifEventPropertyList()) {
                property.setQifEventId(model.getId());
                property.setLastUpdateBy(model.getLastUpdateBy());
                commonService.onSoftDelete(property);
            }
        }
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(QifEvent model) {
        commonService.onInsertOrUpdate(model) ;
        if (model.getQifEventPropertyList() != null) {
            for (QifEventProperty property : model.getQifEventPropertyList()) {
                property.setQifEventId(model.getId());
                property.setCreateBy(model.getCreateBy());
                property.setLastUpdateBy(model.getLastUpdateBy());
                commonService.onInsertOrUpdate(property);
            }
        }
        dao.saveOrUpdate(model);
    }

    @Override
    public List<KeyValue> getQifProcessList() {
        List<KeyValue> keyValueList = new LinkedList<KeyValue>();

        String apiUrl = appSettingService.getSettingStringValue("engine.api.url");
        LOGGER.debug("apiUrl = {}", apiUrl);

        try {
            String json = restClient.get(apiUrl + "processApi");
            LOGGER.debug("json response = {}", json);
            if (!Strings.isNullOrEmpty(json)) {
                List<String> stringList = jsonParser.parseToObject(false, json, new TypeReference<List<String>>(){});
                for (String val : stringList) {
                    KeyValue keyValue = new KeyValue(val, val);
                    keyValueList.add(keyValue);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        return keyValueList;
    }
}
