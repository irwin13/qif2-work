package id.co.quadras.qif.engine.service.imp;

import id.co.quadras.qif.engine.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.engine.queue.reader.ActivityLogInputMsgQueueReader;
import id.co.quadras.qif.engine.queue.reader.ActivityLogOutputMsgQueueReader;
import id.co.quadras.qif.engine.queue.reader.ActivityLogQueueReader;
import id.co.quadras.qif.engine.queue.reader.EventLogMsgQueueReader;
import id.co.quadras.qif.engine.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.engine.service.QueueDrainer;
import id.co.quadras.qif.engine.service.log.ActivityLogDataService;
import id.co.quadras.qif.engine.service.log.ActivityLogInputMsgService;
import id.co.quadras.qif.engine.service.log.ActivityLogOutputMsgService;
import id.co.quadras.qif.engine.service.log.ActivityLogService;
import id.co.quadras.qif.engine.service.log.EventLogMsgService;
import id.co.quadras.qif.engine.service.log.EventLogService;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.model.entity.log.QifEventLog;
import id.co.quadras.qif.model.entity.log.QifEventLogMsg;

import java.util.List;

import com.google.inject.Inject;

public class QueueDrainerImp implements QueueDrainer {

	@Inject
	private ActivityLogQueueReader activityLogQueueReader;
	
	@Inject
	private ActivityLogService activityLogService;

	@Inject
	private ActivityLogDataQueueReader logDataQueueReader;	
	
	@Inject
	private ActivityLogDataService activityLogDataService;

	@Inject
	private ActivityLogInputMsgQueueReader logInputMsgQueueReader;	
	
	@Inject
	private ActivityLogInputMsgService logInputMsgService;
	
	@Inject
	private ActivityLogOutputMsgQueueReader logOutputMsgQueueReader;	
	
	@Inject
	private ActivityLogOutputMsgService logOutputMsgService;

	@Inject
	private EventLogQueueReader eventLogQueueReader;	
	
	@Inject
	private EventLogService eventLogService;
	
	@Inject
	private EventLogMsgQueueReader eventLogMsgQueueReader;	
	
	@Inject
	private EventLogMsgService eventLogMsgService;


	@Override
	public void drainQueue() {	
		List<QifActivityLog> activityLogList = activityLogQueueReader.drainQueue();
		activityLogService.batchInsert(activityLogList);
		
		List<QifActivityLogData> activityLogDataList = logDataQueueReader.drainQueue();
		activityLogDataService.batchInsert(activityLogDataList);

		List<QifActivityLogInputMsg> logInputMsgList = logInputMsgQueueReader.drainQueue();
		logInputMsgService.batchInsert(logInputMsgList);

		List<QifActivityLogOutputMsg> logOutputMsgList = logOutputMsgQueueReader.drainQueue();
		logOutputMsgService.batchInsert(logOutputMsgList);

		List<QifEventLog> eventLogList = eventLogQueueReader.drainQueue();
		eventLogService.batchInsert(eventLogList);

		List<QifEventLogMsg> eventLogMsgList = eventLogMsgQueueReader.drainQueue();
		eventLogMsgService.batchInsert(eventLogMsgList);
	}
}
