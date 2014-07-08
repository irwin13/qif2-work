package id.co.quadras.qif.ui.dao.monitoring;

import id.co.quadras.qif.ui.dto.monitoring.ProcessInstance;
import id.co.quadras.qif.ui.dto.monitoring.ProcessInstanceData;
import id.co.quadras.qif.ui.dto.monitoring.TaskInputMsg;
import id.co.quadras.qif.ui.dto.monitoring.TaskOutputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 07/07/2014 17:18
 */
public interface ProcessInstanceDao {
    public List<ProcessInstance> selectProcessInstance(int start, int fetchSize);
    public List<ProcessInstanceData> selectProcessData(String processInstanceId);
    public TaskInputMsg getTaskInputMsg(String processId);
    public TaskOutputMsg getTaskOutputMsg(String processId);
}
