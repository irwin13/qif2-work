package id.co.quadras.qif.dev.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author irwin Timestamp : 05/06/2014 14:20
 */
public class AfterMidnightJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // TODO
        // when change day or after 00:00, do clean log data

    }
}
