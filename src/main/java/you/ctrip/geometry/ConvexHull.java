package you.ctrip.geometry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;

import you.ctrip.entity.LatLngEntity;

/**
 * 
 * 凸包算法,对于给定的2维空间的点集合，计算包含这些点的最小凸多边形。
 * @author chen.yp
 * @return
 * 返回该凸多边形的顶点集合
 */
public class ConvexHull {


	 	
	//列表对象去重
	public  ArrayList<LatLngEntity> removeduplicate(List<LatLngEntity> pts) {
		
		HashSet<LatLngEntity> set = new HashSet<LatLngEntity>();
		for (int i = 0; i < pts.size(); i++) {
			set.add(pts.get(i));
		}
		ArrayList<LatLngEntity> result = new ArrayList<LatLngEntity>();
		result.addAll(set);

		return result;
	}
	
	//列表对象排序
	public  ArrayList<LatLngEntity> _sort(ArrayList<LatLngEntity> al) {
		ComparatorLatLng comparator = new ComparatorLatLng();
		Collections.sort(al, comparator);
		return al;
	}
	
	/**
	 * Computes the convex hull of a set of 2D points. 
	 * Input: an iterable sequence of (x, y) pairs representing the points.
	 * Output: a list of vertices of the convex hull in counter-clockwise order,
	 * starting from the vertex with the lexicographically smallest coordinates.
	 * Implements Andrew's monotone chain algorithm. O(n log n) complexity.
	 * 
	 * 
	 * @param pts
	 * @return
	 * 
	 */
	public ArrayList<LatLngEntity> getConvexHull(List<LatLngEntity> pts) {
		ArrayList<LatLngEntity> points;
		ArrayList<LatLngEntity> lower;
		ArrayList<LatLngEntity> upper;
		ArrayList<LatLngEntity> output;
		
		points = _sort(removeduplicate(pts));
		
		lower = new ArrayList<LatLngEntity>();
		for (int i=0; i<points.size(); i++) {

			while (lower.size() >= 2 && cross(
					new double[] {lower.get(lower.size()-2).getLat(), lower.get(lower.size()-2).getLng()}, 
					new double[] {lower.get(lower.size()-1).getLat(), lower.get(lower.size()-1).getLng()},
					new double[] {points.get(i).getLat(), points.get(i).getLng()}) <=0) {
					lower.remove(lower.size()-1);
			}

			lower.add(points.get(i));
		}
		
		upper = new ArrayList<LatLngEntity>();
		for (int i=points.size()-1; i>=0; i--) {

			while (upper.size() >= 2 && cross(
					new double[] {upper.get(upper.size()-2).getLat(), upper.get(upper.size()-2).getLng()}, 
					new double[] {upper.get(upper.size()-1).getLat(), upper.get(upper.size()-1).getLng()},
					new double[] {points.get(i).getLat(), points.get(i).getLng()}) <=0) {
					upper.remove(upper.size()-1);
			}

			upper.add(points.get(i));
		}

		
		output = new ArrayList<LatLngEntity>();
		for (int i=0; i<lower.size()-1; i++) {
			output.add(lower.get(i));
		}
		for (int i=0; i<upper.size()-1; i++) {
			output.add(upper.get(i));
		}

		return output;
		
	}
	
	/**
	 * 计算两个向量夹角OAB是顺时针还是逆时针
	 * @param o
	 * @param a
	 * @param b
	 * @return
	 * 返回值为正，逆时针 
	 * 返回值为负，顺时针 
	 * 返回值为0， 共线 
	 * 
	 */
	public double cross(double[] o, double[] a, double[] b) {
		return (a[0] - o[0]) * (b[1] - o[1]) - (a[1] - o[1]) * (b[0] - o[0]);
	}
	
}
