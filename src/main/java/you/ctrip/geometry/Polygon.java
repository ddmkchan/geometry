package you.ctrip.geometry;

import java.util.ArrayList;
import java.util.Collections;

import you.ctrip.entity.LatLngEntity;

public class Polygon {


	Bounds bounds = null;
	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	
	public Polygon (ArrayList<LatLngEntity> pts) {
		
		LatLngEntity minlatlngEntity;
		LatLngEntity maxlatlngEntity;
		
		ArrayList<Double> latList = new ArrayList<Double>();
		ArrayList<Double> lngList = new ArrayList<Double>();
		
		for (LatLngEntity lg:pts) {
			latList.add(Double.valueOf(lg.getLat()).doubleValue());
			lngList.add(Double.valueOf(lg.getLng()).doubleValue());
		}
		
		Collections.sort(latList);
		Collections.sort(lngList);
		
		minlatlngEntity = new LatLngEntity(latList.get(0), lngList.get(0));
		maxlatlngEntity = new LatLngEntity(latList.get(latList.size()-1), lngList.get(lngList.size()-1));
		
		bounds = new Bounds(minlatlngEntity, maxlatlngEntity);
		

	}
	
	//根据给点经纬度对象，判断是否在对应的多边形区域
	public boolean containsLatLng(LatLngEntity latlng, ArrayList<LatLngEntity> pts) {

		ArrayList<LatLngEntity> path = null;//给定的多个坐标点列表
		//多边形区域的顶点也属于该区域范围
		for (LatLngEntity lg : pts) {
			if (lg.getLat() == latlng.getLat() && lg.getLng() == latlng.getLng()) {
				return true;
			}
		}
		
		if (bounds != null && !bounds.contains(latlng)) {
			return false;
		}
		
		boolean inpoly = false;
		path = pts;
		int j = path.size()-1;
		
		for (int i=0; i<path.size(); i++) {
			LatLngEntity vertex1 = path.get(i);
			LatLngEntity vertex2 = path.get(j);
			
			if (vertex1.getLng() < latlng.getLng() && vertex2.getLng() >= latlng.getLng()
					|| vertex2.getLng() < latlng.getLng() && vertex1.getLng() >= latlng.getLng()) {
				if (vertex1.getLat() + (latlng.getLng() - vertex1.getLng()) / (vertex2.getLng() - vertex1.getLng())
						* (vertex2.getLat() - vertex1.getLat()) < latlng.getLat()) {
					inpoly = !inpoly ;
				}
			}
			j = i;
		}
		
		return inpoly;
	}
}
