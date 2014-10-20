package comlib.adk.util.move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.Edge;
import rescuecore2.worldmodel.EntityID;

//This is SUNTORI Route
public class Route {
    private EntityID mySelfID;
    private Area mySelf;
    /**
    * 通行可能なEdgeを保持するリスト
    */
    private List<Edge> passableEdge;
    
    /**
    * 接続しているAreaとそのエリアの中心までの予想移動距離のマップ
    */
    private Map<Route, Integer> distanceMap;
    
    /**
    * 接続しているAreaへ移動するために通らなければならないEdgeを格納しているマップ
    */
    private Map<EntityID, Edge> connectionMap;
    
    /**
    * mySelfの中心からisPassableなEdgeまでの距離のマップ
    */
    private Map<Edge, Integer> rangeMap;
    
    private Map<Route, AreaConnectCondition> conditionMap;
    
    private Map<EntityID, Route> blockadeMap;
    
    // private int lastaConditionUpdatedTime;
    
    private int cost;
    private int costUpdatedTime;
    
    public enum AreaConnectCondition {
        UNKNOWN, PASSABLE, UNPASSABLE;
    }
    
    public Route(Area a, List<EntityID> areaList) {
		this.mySelf = a;
		this.mySelfID = a.getID();
		this.passableEdge = new ArrayList<Edge>();
		// for (Edge e : a.getEdges()) {
		// if (e.isPassable() && areaList.contains(e.getNeighbour())) {
		// this.passableEdge.add(e);
		// }
		// }
		this.distanceMap = new HashMap<Route, Integer>();
		this.connectionMap = new HashMap<EntityID, Edge>();
		this.rangeMap = new HashMap<Edge, Integer>();
		for (Edge e : this.mySelf.getEdges()) {
			if (e.isPassable() && areaList.contains(e.getNeighbour())) {
				this.passableEdge.add(e);
				this.rangeMap.put(e, distance(e));
				this.connectionMap.put(e.getNeighbour(), e);
			}
		}
		this.conditionMap = new HashMap<Route, AreaConnectCondition>();
		this.cost = -1;
		this.costUpdatedTime = -1;
		this.blockadeMap = new HashMap<EntityID, Route>();
	}

	public void addLink(Route r) {
		if (!this.distanceMap.containsKey(r)) {
			Map<Edge, Edge> commonMap = new HashMap<Edge, Edge>();
			for (Edge e : passableEdge) {
				int distance = Integer.MAX_VALUE;
				Edge tmp1 = null;
				Edge tmp2 = null;
				if (e.getNeighbour().equals(r.getID())) {
					for (Edge e2 : r.getPassableEdge()) {
						if (e2.getNeighbour().equals(this.getID())) {
							int n = Util.distance(e, e2);
							if (n < distance) {
								distance = n;
								tmp1 = e;
								tmp2 = e2;
							}
						}
					}
					commonMap.put(tmp1, tmp2);
				}
			}
			for (Edge e : passableEdge) {
				int dis1 = this.getDistance(e);// Edgeとの距離
				int dis2 = r.getDistance(commonMap.get(e));
				if (dis1 > 0 && dis2 > 0) {// 通行可能なエッジであればdis1,dis2は正であるはず
					int dis = dis1 + dis2;
					Integer length = this.distanceMap.get(r);
					if (length != null && length < dis) {
						dis = length;
					} else {
						connectionMap.put(r.getID(), e);
						r.getConnectionMap()
								.put(this.getID(), commonMap.get(e));
					}
					// else if (length == null) {
					// if ((this.getID().getValue() == 267 &&
					// r.getID().getValue() ==
					// 254)
					// || (this.getID().getValue() == 254 && r.getID()
					// .getValue() == 267)) {
					// System.out.println(dis);
					// }
					// } else if (length != null && dis < length) {
					// System.out.println("更新" + dis);
					// }
					this.distanceMap.put(r, dis);// 1
					r.getDistanceMap().put(this, dis);
				}
				conditionMap.put(r, AreaConnectCondition.UNKNOWN);
				r.getConditionMap().put(this, AreaConnectCondition.UNKNOWN);

			}
		}
	}

	/**
	 * リンクを追加する<br>
	 * 1.自エリアから指定されたRouteのエリアへの予測移動距離を追加<br>
	 * ・自エリアから指定されたRouteのエリアへ移動するために通過する必要があるEdgeを追加<br>
	 * ・自エリアから指定されたRouteのエリアへ移動することができるかを格納(初期値としてUNKNOWN)
	 * 
	 * @param r
	 * @param e
	 *            輪郭
	 */
	public void addLink(Route r, Edge e) {
		// if (!this.distanceMap.containsKey(r)) {
		int dis1 = this.getDistance(this.connectionMap.get(r.getID()));// Edgeとの距離

		int dis2 = r.getDistance(e);
		if (dis1 > 0 && dis2 > 0) {// 通行可能なエッジであればdis1,dis2は正であるはず
			int dis = dis1 + dis2;
			if ((this.getID().getValue() == 267 && r.getID().getValue() == 254)
					|| (this.getID().getValue() == 254 && r.getID().getValue() == 267)) {
				System.out.println(dis + "::" + e);
				System.out.println(connectionMap);
			}
			Integer length = this.distanceMap.get(r);
			if (length != null && length < dis) {
				// if ((this.getID().getValue() == 267 && r.getID().getValue()
				// == 254)
				// || (this.getID().getValue() == 254 && r.getID()
				// .getValue() == 267)) {
				// System.out.println(dis + "<=" + length + ":" + e);
				// }
				dis = length;
			}
			// else if (length == null) {
			// if ((this.getID().getValue() == 267 && r.getID().getValue() ==
			// 254)
			// || (this.getID().getValue() == 254 && r.getID()
			// .getValue() == 267)) {
			// System.out.println(dis);
			// }
			// } else if (length != null && dis < length) {
			// System.out.println("更新" + dis);
			// }
			this.distanceMap.put(r, dis);// 1
			r.getDistanceMap().put(this, dis);
		}
		// connectionMap.put(r, e);
		// r.getConnectionMap().put(this, e);
		conditionMap.put(r, AreaConnectCondition.UNKNOWN);
		r.getConditionMap().put(this, AreaConnectCondition.UNKNOWN);
		// }
	}

	public Map<Route, Integer> getDistanceMap() {
		return this.distanceMap;
	}

	public int getDistance(Route r) {
		try {
			return this.distanceMap.get(r);
		} catch (Exception e) {
			System.err.println(this.getID() + "::" + r.getID());
			e.printStackTrace();
			return this.distanceMap.get(r);
		}
	}

	public Map<EntityID, Edge> getConnectionMap() {
		return this.connectionMap;
	}

	/**
	 * 自エリアから接続エリアへ移動可能かどうかを記録しているマップを取得する
	 * 
	 * @return
	 */
	public Map<Route, AreaConnectCondition> getConditionMap() {
		return this.conditionMap;
	}

	/**
	 * 接続状況を設定する
	 * 
	 * @param r
	 * @param con
	 *            接続状況
	 */
	public void setCondition(Route r, AreaConnectCondition con) {
		this.conditionMap.put(r, con);
		r.getConditionMap().put(this, con);
	}

	public EntityID getID() {
		return this.mySelfID;
	}

	public Area getArea() {
		return this.mySelf;
	}

	public int getX() {
		return this.mySelf.getX();
	}

	public int getY() {
		return this.mySelf.getY();
	}

	public List<Edge> getPassableEdge() {
		return this.passableEdge;
	}

	/**
	 * 自エリアの中心からあるエッジまでの距離を取得する.<br>
	 * とりあえず今はエッジの中心までの距離を返す
	 * 
	 * @param a
	 *            エリア
	 * @param e
	 *            エッジ
	 * @return 距離S
	 */
	private int distance(Edge e) {
		// edgeの中心点
		int cx = (e.getStartX() + e.getEndX()) / 2;
		int cy = (e.getStartY() + e.getEndY()) / 2;
		int dx = this.mySelf.getX();
		int dy = this.mySelf.getY();
		return Util.distance(cx, cy, dx, dy);
	}

	/**
	 * 自エリアからある通行可能なエッジまでの距離を取得する.<br>
	 * エッジには自エリアの通行可能なエッジが指定されることを前提としている.<br>
	 * そうでないエッジが指定されたときは-1を返す.
	 * 
	 * @param e
	 *            距離を知りたいエッジ
	 * @return 距離
	 */
	public int getDistance(Edge e) {
		int res = -1;
		if (rangeMap.containsKey(e)) {
			res = rangeMap.get(e);
		}
		return res;
	}

	/**
	 * 出発地点から目的地へ移動するために通らなければならないEdgeを取得する.
	 * 
	 * @return
	 */
	public Edge getHaveToPassEdge(Route r) {
		return this.connectionMap.get(r.getID());
	}

	public Set<Route> getNeighbors() {
		return this.distanceMap.keySet();
	}

	public void setCost(int cost, int time) {
		this.cost = cost;
		this.costUpdatedTime = time;
	}

	public int getCost() {
		return this.cost;
	}

	public int getCostUpdatedTime() {
		return this.costUpdatedTime;
	}

	/**
	 * 自エリアから指定されたRouteのエリアへの接続状態を取得する
	 * 
	 * @param r
	 * @return
	 */
	public AreaConnectCondition getAreaConnectCondition(Route r) {
		return this.conditionMap.get(r);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mySelfID == null) ? 0 : mySelfID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (mySelfID == null) {
			if (other.mySelfID != null)
				return false;
		} else if (!mySelfID.equals(other.mySelfID))
			return false;
		return true;
	}

	public String toString() {
		return "(" + mySelfID + "," + cost + ")";
	}

	public Map<EntityID, Route> getBlockadeMap() {
		return this.blockadeMap;
	}

	public boolean isPassable(Route r) {
		boolean res = true;
		if (conditionMap.get(r).equals(AreaConnectCondition.UNPASSABLE)) {
			res = false;
		}
		return res;
	}
}
