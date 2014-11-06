package you.ctrip.entity;

/**
 * 
 * @author chen.yp
 * 存储经纬度的对象，重写equals和hashCode这两个方法
 */

public class LatLngEntity {
	
	private double lat;
	private double lng;
	
	public LatLngEntity (double _lat, double _lng) {
		lat = _lat;
		lng = _lng;
	}

	public LatLngEntity() {
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj){  
            return true ;  
        }  
        if(!(obj instanceof LatLngEntity)){  
            return false ;  
        }  
        LatLngEntity latlngInfo = (LatLngEntity)obj ;
        return this.lat == latlngInfo.lat &&
        		this.lng == latlngInfo.lng;
	}

	 @Override  
	public int hashCode() {
		return (int) (this.lat*this.lng);
	}

}
