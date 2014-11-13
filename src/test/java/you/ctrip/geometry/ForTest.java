package you.ctrip.geometry;

import java.util.ArrayList;
import java.util.Collections;

import you.ctrip.entity.LatLngEntity;

public class ForTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConvexHull convexHull = new ConvexHull();
		ArrayList<LatLngEntity> list = new ArrayList<LatLngEntity>();
		list.add(new LatLngEntity(0.0, 1.0));
		list.add(new LatLngEntity(0.0, 0.0));
		list.add(new LatLngEntity(1.0, 1.0));
		list.add(new LatLngEntity(2.0, 0.0));
		list.add(new LatLngEntity(1.0, 2.0));
		list.add(new LatLngEntity(1.0, 2.0));
		try {
			ArrayList<LatLngEntity> convexHullPts = convexHull.getConvexHull(list);//凸包多边形
			
			Polygon poly = new Polygon(convexHullPts);
			System.out.println(poly.containsLatLng(new LatLngEntity(1.0, 1.0)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
