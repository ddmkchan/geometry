package you.ctrip.geometry;

import java.util.Comparator;

import you.ctrip.entity.LatLngEntity;

/**
 * 
 * @author chen.yp
 * LatLngEntity对象比较类，实现Comparator接口
 */

public class ComparatorLatLng implements Comparator<Object>{

	public int compare(Object arg0, Object arg1) {

		LatLngEntity latlng0 = (LatLngEntity) arg0;
		LatLngEntity latlng1 = (LatLngEntity) arg1;
  	
//		首先比较lat，如果lat相同，再比较lng
		int flag = Double.toString(latlng0.getLat()).compareTo(Double.toString(latlng1.getLat()));
  	
		if (flag == 0) {
			return Double.toString(latlng0.getLng()).compareTo(Double.toString(latlng1.getLng()));
		}else {
			return flag;
		}

	}
 
}

