package id.co.quadras.qif.engine.dao;

import id.co.quadras.qif.core.model.entity.QifEvent;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:17
 */
public interface EventDao {
    public List<QifEvent> select(QifEvent filter);
    public QifEvent selectById(String id);

    public void update(QifEvent qifEvent);
    public void update(SqlSession session, QifEvent qifEvent);
    public SqlSession openSqlSession(ExecutorType executorType);
}
