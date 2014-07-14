package id.co.quadras.qif.ui.service.monitoring;

import id.co.quadras.qif.ui.dto.monitoring.ProcessInstance;
import id.co.quadras.qif.ui.dto.monitoring.ProcessInstanceData;
import id.co.quadras.qif.ui.dto.monitoring.TaskInputMsg;
import id.co.quadras.qif.ui.dto.monitoring.TaskOutputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 08/07/2014 17:34
 */
public interface ProcessInstanceService {

    public List<ProcessInstance> selectProcessInstance(int start, int fetchSize);
    public List<ProcessInstance> selectProcessTasks(String processId);
    public long selectCountProcessInstance();
    public List<ProcessInstanceData> selectActivityData(String processInstanceId);
    public TaskInputMsg getTaskInputMsg(String processId);
    public TaskOutputMsg getTaskOutputMsg(String processId);
    public ProcessInstance selectProcessInstance(String processId);
}
