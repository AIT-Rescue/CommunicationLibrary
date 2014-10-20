// Copyright (C) 2002 Takeshi Morimoto <morimoto@takopen.cs.uec.ac.jp>
// All rights reserved.
package comlib.adk.util.move;

import rescuecore2.misc.Pair;
import rescuecore2.standard.entities.Edge;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;

public class Util {
	public static final int distance(int x, int y, int x2, int y2) {
		float dx = x - x2;
		float dy = y - y2;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	public static final int distance(Edge e1, Edge e2) {
		int cx1 = (e1.getStartX() + e1.getEndX()) / 2;
		int cy1 = (e1.getStartY() + e1.getEndY()) / 2;
		int cx2 = (e2.getStartX() + e2.getEndX()) / 2;
		int cy2 = (e2.getStartY() + e2.getEndY()) / 2;
		return distance(cx1, cy1, cx2, cy2);
	}

	public static final int distance(int x, int y, Edge e) {
		int cx = (e.getStartX() + e.getEndX()) / 2;
		int cy = (e.getStartX() + e.getEndY()) / 2;
		return distance(x, y, cx, cy);
	}

	public static final int distance(StandardWorldModel model,
			StandardEntity from, int toX, int toY) {
		Pair<Integer, Integer> loc = from.getLocation(model);
		float dx = loc.first() - toX;
		float dy = loc.second() - toY;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	public static final int distance(StandardWorldModel model,
			StandardEntity from, StandardEntity to) {
		Pair<Integer, Integer> fromLoc = from.getLocation(model);
		Pair<Integer, Integer> toLoc = to.getLocation(model);
		float dx = fromLoc.first() - toLoc.first();
		float dy = fromLoc.second() - toLoc.second();
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	public static final int direction(StandardWorldModel model,
			StandardEntity from, StandardEntity to) {
		return direction(from.getLocation(model), to.getLocation(model));
	}

	// public static final int direction(int fromX, int fromY, StandardEntity
	// to) {
	public static final int direction(Pair<Integer, Integer> from,
			Pair<Integer, Integer> to) {
		int dx = to.first() - from.first();
		int dy = to.second() - from.second();
		float theta = (float) Math.atan2(-dx, dy);
		if (theta < 0)
			theta += 2 * Math.PI;
		return (int) (theta * 360 * 60 * 60 / (2 * Math.PI));
	}

	// public static final double radian(int fromX, int fromY, Entity to) {
	// int dx = to.x() - fromX;
	// int dy = to.y() - fromY;
	// double theta = Math.atan2(dx, dy);
	// if (theta < 0) {
	// theta += 2 * Math.PI;
	// }
	// return theta;
	// }

	public static String classBaseName(Object obj) {
		StringTokenizer st = new StringTokenizer(obj.getClass().getName(), ".");
		String token = null;
		while (st.hasMoreTokens())
			token = st.nextToken();
		return token;
	}

	public static int max(int x, int y) {
		return x >= y ? x : y;
	}

	public static int min(int x, int y) {
		return x <= y ? x : y;
	}

	public static Object randomChoice(Collection col, Random random) {
		if (col.isEmpty())
			throw new Error("the col must not be empty.");
		Iterator it = col.iterator();
		for (int i = random.nextInt(col.size()); i > 0; i--)
			it.next();
		return it.next();
	}

	/**
	 * Edgeの中心座標を取得する.
	 * 
	 * @param e
	 * @return
	 */
	public static Pair<Integer, Integer> getCenterCordinateOfEdge(Edge e) {
		int x = (e.getStartX() + e.getEndX()) / 2;
		int y = (e.getStartY() + e.getEndY()) / 2;
		return new Pair<Integer, Integer>(x, y);
	}
}
