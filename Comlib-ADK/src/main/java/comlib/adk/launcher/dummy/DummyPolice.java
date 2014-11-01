package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.straight.StraightPolice;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BlockadeSelector;
import comlib.adk.util.target.sample.SampleBlockadeSelector;
import comlib.manager.MessageManager;

public class DummyPolice extends StraightPolice {

    @Override
    public BlockadeSelector getBlockadeSelector() {
        return new SampleBlockadeSelector(this);
    }

    @Override
    public RouteSearcher getRouteSearcher() {
        return new SampleRouteSearcher(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {
    }

    /*private List<EntityID> getBlockedRoads() {
        Collection<StandardEntity> e = model.getEntitiesOfType(StandardEntityURN.ROAD);
        List<EntityID> result = new ArrayList<>();
        for (StandardEntity next : e) {
            Road r = (Road)next;
            if (r.isBlockadesDefined() && !r.getBlockades().isEmpty()) {
                result.add(r.getID());
            }
        }
        return result;
    }*/

    /*private Blockade getTargetBlockade() {
        Area location = (Area)location();
        Blockade result = getTargetBlockade(location, distance);
        if (result != null) {
            return result;
        }
        for (EntityID next : location.getNeighbours()) {
            location = (Area)model.getEntity(next);
            result = getTargetBlockade(location, distance);
            if (result != null) {
                return result;
            }
        }
        return null;
    }*/

    /*private Blockade getTargetBlockade(Area area, int maxDistance) {
        if (area == null || !area.isBlockadesDefined()) {
            return null;
        }
        List<EntityID> ids = area.getBlockades();
        // Find the first blockade that is in range.
        int x = me().getX();
        int y = me().getY();
        for (EntityID next : ids) {
            Blockade b = (Blockade)model.getEntity(next);
            double d = findDistanceTo(b, x, y);
            if (maxDistance < 0 || d < maxDistance) {
                return b;
            }
        }
        return null;
    }*/

    /*private int findDistanceTo(Blockade b, int x, int y) {
        List<Line2D> lines = GeometryTools2D.pointsToLines(GeometryTools2D.vertexArrayToPoints(b.getApexes()), true);
        double best = Double.MAX_VALUE;
        Point2D origin = new Point2D(x, y);
        for (Line2D next : lines) {
            Point2D closest = GeometryTools2D.getClosestPointOnSegment(next, origin);
            double d = GeometryTools2D.getDistance(origin, closest);
            if (d < best) {
                best = d;
            }
        }
        return (int)best;
    }*/
}
