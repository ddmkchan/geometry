package you.ctrip.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import you.ctrip.biz.ZoneRangeService;
import you.ctrip.common.CheckUtil;
import you.ctrip.common.SimpleHttpClient;
import you.ctrip.entity.GaoDeCoords;
import you.ctrip.entity.LatLngEntity;
import you.ctrip.entity.ZoneEntity;
import you.ctrip.geometry.ConvexHull;
import you.ctrip.geometry.Polygon;


@Controller
public class ZoneRangeController {

	 private static final Logger logger  =  Logger.getLogger(ZoneRangeController.class);
	
	@Resource
    private ZoneRangeService zoneRangeService;
	
	ConvexHull convexHull = null;
	Polygon poly = null;
	
	private SimpleHttpClient httpClient = new SimpleHttpClient(10, 50, 300000, 8000);
	
	@ResponseBody 
	@RequestMapping(value = "/get_zone_range")
    public List<ZoneEntity> iscontains(@RequestParam(value="lnglat", required=false, defaultValue="") String lnglat, Model model) {

		List<ZoneEntity> zones = new ArrayList<ZoneEntity>();
		try {
		    Map<Integer, ArrayList<LatLngEntity>> zoneMap = zoneRangeService.getZoneMap();
		    Map<Integer, String> zoneNameMap = zoneRangeService.getZoneNameMap();
		    convexHull = new ConvexHull();
			if (!CheckUtil.isEmpty(lnglat)) {//url参数，传入经纬度
				String[] _latlng = lnglat.split("\\,");
				LatLngEntity inputPoint = new LatLngEntity();
				inputPoint.setLat(Double.parseDouble(_latlng[1]));
				inputPoint.setLng(Double.parseDouble(_latlng[0]));
				Iterator<Entry<Integer, ArrayList<LatLngEntity>>> iter = zoneMap.entrySet().iterator(); 
				while (iter.hasNext()) {
					Map.Entry me = (Map.Entry) iter.next();
					ArrayList<LatLngEntity> pts = (ArrayList<LatLngEntity>) me.getValue();//酒店商圈经纬度集合
					try {
						if (pts.size() >= 3) {
							ArrayList<LatLngEntity> convexHullPts = convexHull.getConvexHull(pts);//转换成凸多边形
							if (convexHullPts.size() >= 3) {
								poly = new Polygon(convexHullPts);
								boolean iscontain = poly.containsLatLng(inputPoint);//
								if (iscontain) {
									int gsZoneId = (Integer) me.getKey();
									String gsZoneName = "";
									if (zoneNameMap.containsKey(gsZoneId)) {
										gsZoneName = zoneNameMap.get(gsZoneId);
									}
									ZoneEntity gsZone = new ZoneEntity();
									gsZone.setZoneName(gsZoneName);
									gsZone.setZone(gsZoneId);
									zones.add(gsZone);
								}		
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
			        	logger.info("异常信息:"
			                    + ex + "\n请求参数: " +JSON.toJSONString(lnglat)
			                    +"\n商圈经纬度：" + JSON.toJSONString(pts));
			        }

				}
			} else {
				logger.info("传入内容为空:\t"+lnglat.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
        	logger.info("异常信息:"
                    + ex + "\n请求参数: " +JSON.toJSONString(lnglat));
        }
    	return zones;
    }
	
	@ResponseBody 
	@RequestMapping(value = "/convert_coords")
    public String convertCoords(@RequestParam(value="lnglat", required=false, defaultValue="") String lnglat, Model model) {
		String rs = "";
		try {
			if (!CheckUtil.isEmpty(lnglat)) {//url参数，传入经纬度
				StringBuilder strUrl = new StringBuilder();
				strUrl.append("http://restapi.amap.com/coordinate/simple?src=baidu&xys=");
				strUrl.append(lnglat);
				strUrl.append("&resType=json&Key=0026ff5ef64722f9099cc4d7b1f1be33&sid=15001");
				String httpRs = httpClient.getContent(strUrl.toString());
				GaoDeCoords jsonobj = JSON.parseObject(httpRs, GaoDeCoords.class);
				logger.info("请求参数： \t"+lnglat+"\r\n返回信息： "+ jsonobj.getDesc()+"\r\n返回status： "+ jsonobj.getStatus());
				if (jsonobj.getXys() != null) {
					rs = jsonobj.getXys();
				}
			}
		} catch (Exception ex) {
        	logger.info("异常信息:"
                    + ex.getMessage() + ",堆栈信息:" + ex.getStackTrace() + "请求参数: " +JSON.toJSONString(lnglat));
        }
		return rs;
	}
	
}
