package you.ctrip.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import you.ctrip.biz.ZoneRangeService;
import you.ctrip.biz.BeanManager;

/**
 * 初始化数据的Servlet，将基本数据放到本地缓存，需在web.xml中添加配置。<br/>
 * 
 * @author aiming
 * @date 2014-03-10 上午18:04:17
 * 
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 324234231L;

    private static final Logger logger = Logger.getLogger(ZoneRangeService.class);
    private ZoneRangeService zoneRangeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
//    	Config.initialize("config.properties");
//        Config.initialize("config-uat.properties");
//        Config.initialize("config-fat.properties");
//        Config.initialize("config-local.properties");
//    	Config.initialize("rabbitmq.properties");
        logger.info("--------zoneRangeService init start--------");
        zoneRangeService = BeanManager.getBean("zoneRangeService");
        reInit();
        logger.info("--------zoneRangeService init over--------");
    }

    private void reInit() {
    	zoneRangeService.init();
    }
}
