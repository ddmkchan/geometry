package you.ctrip.geometry;

import you.ctrip.entity.LatLngEntity;

/**
 * Bounds 构建由最大和最小经纬度组成的矩形区域 
 * 
 * @author chen.yp <br/> 
 * 
 */

public class Bounds {
	
	private LatLngEntity minlatlngEntity;
	private LatLngEntity maxlatlngEntity;
	
	public Bounds (LatLngEntity minlatlng, LatLngEntity maxlatlng) {
		minlatlngEntity = minlatlng;
		maxlatlngEntity = maxlatlng;
	}
	
	//判断经纬度是否在该矩形区域
	public boolean contains(LatLngEntity latlng) {
		boolean status = true;
		if (latlng.getLat() > maxlatlngEntity.getLat() ||
				latlng.getLng() > maxlatlngEntity.getLng() || 
				latlng.getLat() < minlatlngEntity.getLat() || 
				latlng.getLng() < minlatlngEntity.getLng()) {
			status = false;
		}
		return status;
	}
}
