package comlib.adk.util.move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.Edge;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

//Suntori Router
public class Router {
	private StandardWorldModel model;
	private List<Route> routes;
	private int costCalculatedTime;
	private RouteFactory rf;
	
	public enum AreaConnectCondition {
		UNKNOWN, PASSABLE, UNPASSABLE;
	}

	public Router(StandardWorldModel model) {
		this.model = model;
		this.rf = new RouteFactory(model);
		Collection<StandardEntity> areas = this.model.getEntitiesOfType(
				StandardEntityURN.BUILDING, StandardEntityURN.REFUGE,
				StandardEntityURN.ROAD);
		routes = new ArrayList<Route>();
		for (StandardEntity se : areas) {
			Route route = rf.getRoute((Area) se);
			routes.add(route);
			setRoute(route, this.model);
		}
		costCalculatedTime = -1;
	}

	private void setRoute(Route r, StandardWorldModel model) {
		Set<Area> connectSet = new HashSet<Area>();
		for (Edge e : r.getPassableEdge()) {
			connectSet.add((Area) this.model.getEntity(e.getNeighbour()));
		}
		for (Area a : connectSet) {
			rf.getRoute(a).addLink(r);
		}
		// for (Edge e : r.getPassableEdge()) {
		// Area area = ((Area) model.getEntity(e.getNeighbour()));
		// rf.getRoute(area).addLink(r, e);
		// if (r.getID().getValue() == 267 || r.getID().getValue() == 254) {
		// System.out.println(r+" to "+area);
		// }
		// }
	}

	/**
	 * 出発地点から目的地点までの移動経路を取得する
	 * 
	 * @param from
	 *            出発地点
	 * @param to
	 *            目的地点
	 * @return 移動経路
	 */
	public List<EntityID> getPath(EntityID from, EntityID to, int time) {
		Route fromRoute = rf.getRoute(from);
		Route toRoute = rf.getRoute(to);
		List<EntityID> res = new ArrayList<EntityID>();
		if (this.costCalculatedTime != time) {
			if (fromRoute != null) {
				fromRoute.setCost(0, time);
				calcMoveCost(fromRoute, time);
			}
			this.costCalculatedTime = time;
		}
		getPath(fromRoute, toRoute, res);
		return res;
	}

	private void getPath(Route from, Route to, List<EntityID> path) {
		Route now = to;
		path.add(0, to.getID());
		int cost = now.getCost();
		for (Route r : to.getNeighbors()) {
			if (cost > r.getCost() && now.isPassable(r)) {
				// if (!from.equals(r)) {
				getPath(from, r, path);
				// }
				break;
			}
		}
	}

	public void calculateMoveCost(Area a, int time) {
		calculateMoveCost(rf.getRoute(a), time);
	}

	public void calculateMoveCost(EntityID id, int time) {
		calculateMoveCost(rf.getRoute(id), time);
	}

	public void calculateMoveCost(Route r, int time) {
		if (this.costCalculatedTime != time) {
			System.out.println("---------------コスト計算--------------");
			long start = System.currentTimeMillis();

			r.setCost(0, time);
			calcMoveCost(r, time);
			this.costCalculatedTime = time;

			long finish = System.currentTimeMillis();
			System.out.println("コスト計算終了..... " + (finish - start) + " ms");
			System.out.println("-------------------------------------");
		}
	}

	public void calcMoveCost(Area a, int time) {
		Route r = rf.getRoute(a);
		clearCost(time);
		r.setCost(0, time);
		calcMoveCost(r, time);
	}

	public void calcMoveCost(EntityID id, int time) {
		Route r = rf.getRoute(id);
		clearCost(time);
		r.setCost(0, time);
		calcMoveCost(r, time);
	}

	private void calcMoveCost(Route r, int time) {
		for (Route connect : r.getNeighbors()) {
			int cost = r.getCost() + connect.getDistance(r);
			if (!r.getAreaConnectCondition(connect).equals(
					AreaConnectCondition.UNPASSABLE)) {
				if (connect.getCostUpdatedTime() != time) {// まだ一度もコストを計算していなかったとき
					connect.setCost(cost, time);
					calcMoveCost(connect, time);
				} else if (cost < connect.getCost()) {// 一度は計算したがより小さいコストが見つかったとき
					connect.setCost(cost, time);
					calcMoveCost(connect, time);
				}
			}
		}
	}

	private void clearCost(int time) {
		for (Route r : routes) {
			r.setCost(-1, time - 1);
		}
	}

	public Route getRoute(EntityID id) {
		return rf.getRoute(id);
	}

	public Route getRoute(Area a) {
		return rf.getRoute(a);
	}

	/**
	 * 登録されたRoute（エリア）のリストを取得する
	 * 
	 * @return Routeのリスト
	 */
	public List<Route> getAddedAreaList() {
		return this.routes;
	}

	public RouteFactory getRouteFactory() {
		return this.rf;
	}

	/**
	 * 
	 * @param id1
	 * @param id2
	 */
	public void setAreaCondition(EntityID id1, EntityID id2,
			Route.AreaConnectCondition acc) {
		try {
			rf.getRoute(id1).setCondition(rf.getRoute(id2), acc);
			rf.getRoute(id2).setCondition(rf.getRoute(id1), acc);
		} catch (Exception e) {
			System.out.println("-----------------------");
			if (rf.getRoute(id1) == null) {
				System.out.println(id1);
			}
			if (rf.getRoute(id2) == null) {
				System.out.println(id2);
			}
			System.out.println("-----------------------");
		}
	}

	public int getCost(EntityID id) {
		return rf.getRoute(id).getCost();
	}

}
