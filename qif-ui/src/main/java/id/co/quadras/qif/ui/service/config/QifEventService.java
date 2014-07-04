package id.co.quadras.qif.ui.service.config;

import com.irwin13.winwork.basic.model.KeyValue;
import com.irwin13.winwork.basic.service.WinWorkService;
import id.co.quadras.qif.core.model.entity.QifEvent;

import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 13:59
 */
public interface QifEventService extends WinWorkService<QifEvent, String> {
    public List<KeyValue> getQifProcessList();
}
