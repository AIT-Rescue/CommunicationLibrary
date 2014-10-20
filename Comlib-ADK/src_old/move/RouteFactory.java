package comlib.adk.util.move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

/**
 * FlyWeightを用いてRouteを生成するクラス
 * 
 * @author takefumi
 * 
 */
//This is SUNTORI RouteFactory
public class RouteFactory {
	private Map<EntityID, Route> routeMap;
	/**
	 * 地図上に存在するAreaのIDを取得する
	 */
	private List<EntityID> worldAreaList;

	public RouteFactory(StandardWorldModel model) {
		routeMap = new HashMap<EntityID, Route>();
		worldAreaList = new ArrayList<EntityID>();
		for (StandardEntity se : model.getEntitiesOfType(
				StandardEntityURN.AMBULANCE_CENTRE, StandardEntityURN.BUILDING,
				StandardEntityURN.FIRE_STATION,
				StandardEntityURN.POLICE_OFFICE, StandardEntityURN.REFUGE,
				StandardEntityURN.ROAD)) {
			worldAreaList.add(se.getID());
		}
	}

	/**
	 * Routeを取得する
	 * 
	 * @param a
	 * @return
	 */
	public Route getRoute(Area a) {
		Route res = null;
		EntityID id = a.getID();
		if (routeMap.containsKey(id)) {
			res = routeMap.get(id);
		} else {
			res = new Route(a, worldAreaList);
			routeMap.put(id, res);
		}
		return res;
	}

	public Route getRoute(EntityID id) {
		return routeMap.get(id);
	}

	public Route getRoute(int i) {
		Route res = null;
		for (EntityID id : routeMap.keySet()) {
			if (id.getValue() == i) {
				res = routeMap.get(id);
			}
		}
		return res;
	}

	public int size() {
		return routeMap.size();
	}

	public void showMap() {
		for (EntityID id : routeMap.keySet()) {
			System.out.println(id.getValue() + "::"
					+ routeMap.get(id).getDistanceMap());
		}
		Set<EntityID> keys = routeMap.keySet();
		for (EntityID id : keys) {
			System.out.println(routeMap.get(id).getCost());
		}
	}
}
