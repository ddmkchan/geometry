package you.ctrip.common;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import you.ctrip.biz.BeanManager;
import you.ctrip.biz.ZoneRangeService;


/**
 * 定时器类，定时执行更新初始化资源信息map
 * @author mai
 *
 */
public class IndexTimerTask extends TimerTask {
	
	private static final Logger logger = Logger.getLogger(IndexTimerTask.class);
    private ZoneRangeService zoneRangeService = null;
	
	@Override
	public void run() {
        logger.info("--------zoneRangeService init agin start--------");
        this.zoneRangeService = BeanManager.getBean("zoneRangeService");
        zoneRangeService.init();
        logger.info("--------zoneRangeService init again over--------");
	}
}
