package id.co.quadras.qif.engine.dao;

import id.co.quadras.qif.model.entity.QifEventProperty;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:18
 */
public interface EventPropertyDao {
    public List<QifEventProperty> selectByEvent(String eventId);
    public List<QifEventProperty> select(QifEventProperty filter);
    public void batchUpdate(SqlSession session, List<QifEventProperty> modelList);
}
