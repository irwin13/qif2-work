package id.co.quadras.qif.ui.service.monitoring.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.ui.dao.monitoring.ProcessInstanceDao;
import id.co.quadras.qif.ui.dto.monitoring.ProcessInstance;
import id.co.quadras.qif.ui.dto.monitoring.ProcessInstanceData;
import id.co.quadras.qif.ui.dto.monitoring.TaskInputMsg;
import id.co.quadras.qif.ui.dto.monitoring.TaskOutputMsg;
import id.co.quadras.qif.ui.service.monitoring.ProcessInstanceService;

import java.util.List;

/**
 * @author irwin Timestamp : 08/07/2014 17:34
 */
public class ProcessInstanceServiceImp implements ProcessInstanceService {

    private final ProcessInstanceDao dao;

    @Inject
    public ProcessInstanceServiceImp(ProcessInstanceDao dao) {
        this.dao = dao;
    }

    @Override
    public List<ProcessInstance> selectProcessInstance(int start, int fetchSize) {
        return dao.selectProcessInstance(start, fetchSize);
    }

    @Override
    public List<ProcessInstance> selectProcessTasks(String processId) {
        return dao.selectProcessTasks(processId);
    }

    @Override
    public long selectCountProcessInstance() {
        return dao.selectCountProcessInstance();
    }

    @Override
    public List<ProcessInstanceData> selectActivityData(String processInstanceId) {
        return dao.selectActivityData(processInstanceId);
    }

    @Override
    public TaskInputMsg getTaskInputMsg(String processId) {
        return dao.getTaskInputMsg(processId);
    }

    @Override
    public TaskOutputMsg getTaskOutputMsg(String processId) {
        return dao.getTaskOutputMsg(processId);
    }

    @Override
    public ProcessInstance selectProcessInstance(String processId) {
        return dao.selectProcessInstance(processId);
    }
}
