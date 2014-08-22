package id.co.quadras.qif.engine.config;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author irwin Timestamp : 22/08/2014 16:05
 */
public class RootPackage {

    @NotEmpty
    private String task;

    @NotEmpty
    private String process;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("task", task)
                .add("process", process)
                .toString();
    }
}
