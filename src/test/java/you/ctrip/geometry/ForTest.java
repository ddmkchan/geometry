package you.ctrip.geometry;

import java.util.ArrayList;
import java.util.Collections;

import you.ctrip.entity.LatLngEntity;

public class ForTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ArrayList<Double> latList = new ArrayList<Double>();
//		latList.add(0.01);
//		latList.add(0.01);
//		latList.add(0.01);
//		latList.add(0.01);
//		Collections.sort(latList);
//		System.out.println(latList.size());
		ConvexHull convexHull = new ConvexHull();
		ArrayList<LatLngEntity> list = new ArrayList<LatLngEntity>();
		list.add(new LatLngEntity(102.72056702465,25.0206639155639));
		list.add(new LatLngEntity(102.72056702465,25.0206639155639));
		list.add(new LatLngEntity(102.72056702465,25.0206639155639));
		list.add(new LatLngEntity(102.72056702465,25.0206639155639));
		list.add(new LatLngEntity(102.72056702465,25.0206639155639));
		try {
			ArrayList<LatLngEntity> convexHullPts = convexHull.getConvexHull(list);
			Polygon poly = new Polygon(convexHullPts);
			System.out.println(poly);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
