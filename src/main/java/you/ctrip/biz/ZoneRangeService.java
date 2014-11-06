package you.ctrip.biz;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import you.ctrip.dal.ZoneDao;
import you.ctrip.dal.ZoneRangeDao;
import you.ctrip.entity.LatLngEntity;
import you.ctrip.entity.ZoneEntity;
import you.ctrip.entity.ZoneRange;

@Service
@Scope("singleton")
public class ZoneRangeService {

	private static final Logger logger  =  Logger.getLogger(ZoneRangeService.class);

//    private SimpleHttpClient httpClient = new SimpleHttpClient(10, 50, 300000, 300000);
    //商圈id与商圈经纬度点集合的映射关系
    private final Map<Integer, ArrayList<LatLngEntity>> zoneMap = new HashMap<Integer, ArrayList<LatLngEntity>>();
    //商圈ID与商圈名称的映射关系
    private final Map<Integer, String> zoneNameMap = new HashMap<Integer, String>();

	public void init() {
		List<ZoneRange> zoneRangeList = getZoneRangeList();
		DateFormat d = DateFormat.getDateTimeInstance();
		logger.info(d.format(new Date())+"\tTotal zoneRange :"+zoneRangeList.size());
		if (zoneRangeList != null) {
			for (ZoneRange zoneRange : zoneRangeList) {
				if (zoneMap.containsKey(zoneRange.zone)) {
					zoneMap.get(zoneRange.zone).add(new LatLngEntity(zoneRange.pointLat, zoneRange.pointLon));
				} else {
					ArrayList<LatLngEntity> latlngList = new ArrayList<LatLngEntity>();
					latlngList.add(new LatLngEntity(zoneRange.pointLat, zoneRange.pointLon));
					zoneMap.put(zoneRange.zone, latlngList);
				}
			}
		}
		List<ZoneEntity> zoneList = getZoneList();
		if (zoneList != null) {
			for (ZoneEntity zone : zoneList) {
				if (!zoneNameMap.containsKey(zone.zone)) {
					zoneNameMap.put(zone.zone, zone.zoneName);
				}
			}
		}
	}
    
    public Map<Integer, ArrayList<LatLngEntity>> getZoneMap() {
    	return zoneMap;
    }
	
    public Map<Integer, String> getZoneNameMap() {
    	return zoneNameMap;
    }
    
	public List<ZoneRange> getZoneRangeList() {
		List<ZoneRange> zoneRangeList = ZoneRangeDao.getInstance().getZoneRangeList();
		return zoneRangeList;
	}
	
	public List<ZoneEntity> getZoneList() {
		List<ZoneEntity> zoneList = ZoneDao.getInstance().getZoneList();
		return zoneList;
	}
}
