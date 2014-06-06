package id.co.quadras.qif.engine.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author irwin Timestamp : 05/06/2014 14:19
 */
public class BeforeMidnightJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO
        // insert QifCounter by event, process, task
        // if today date is 01-01-2013 then insert data for 02-01-2013
        // ex : processA_02-01-2013
        // taskA_02-01-2013

    }
}
