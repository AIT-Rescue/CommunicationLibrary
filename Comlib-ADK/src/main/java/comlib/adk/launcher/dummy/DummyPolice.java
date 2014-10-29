package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.PoliceForceTactics;
import comlib.adk.util.action.PoliceAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BlockadeSelector;
import comlib.adk.util.target.sample.SampleBlockadeSelector;
import comlib.manager.MessageManager;
import comlib.message.information.BuildingMessage;
import comlib.message.information.CivilianMessage;
import rescuecore2.messages.Message;
import rescuecore2.misc.geometry.GeometryTools2D;
import rescuecore2.misc.geometry.Line2D;
import rescuecore2.misc.geometry.Point2D;
import rescuecore2.misc.geometry.Vector2D;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DummyPolice extends PoliceForceTactics {

    public BlockadeSelector blockadeSelector;

    public RouteSearcher routeSearcher;

    @Override
    public void preparation() {
        this.model.indexClass(StandardEntityURN.ROAD);
        this.routeSearcher = this.getRouteSearcher();
        this.blockadeSelector = this.getBlockadeSelector();
    }

    public BlockadeSelector getBlockadeSelector() {
        return new SampleBlockadeSelector(this);
    }

    public RouteSearcher getRouteSearcher() {
        return new SampleRouteSearcher(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {
    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        this.updateInfo(changed, manager);

        //Blockade targetBlockade = getTargetBlockade();
        Blockade targetBlockade = (Blockade)this.model.getEntity(this.target != null ? this.target : this.blockadeSelector.getTarget(time));
        if (targetBlockade != null) {
            List<Line2D> lines = GeometryTools2D.pointsToLines(GeometryTools2D.vertexArrayToPoints(targetBlockade.getApexes()), true);
            double best = Double.MAX_VALUE;
            Point2D bestPoint = null;
            Point2D origin = new Point2D(me().getX(), me().getY());
            for (Line2D next : lines) {
                Point2D closest = GeometryTools2D.getClosestPointOnSegment(next, origin);
                double d = GeometryTools2D.getDistance(origin, closest);
                if (d < best) {
                    best = d;
                    bestPoint = closest;
                }
            }
            Vector2D v = bestPoint.minus(new Point2D(me().getX(), me().getY()));
            v = v.normalised().scale(1000000);
            return PoliceAction.clear(this, time, (int)(me().getX() + v.getX()), (int)(me().getY() + v.getY()));
        }
        // Plan a path to a blocked area
        //List<EntityID> path = search.breadthFirstSearch(me().getPosition(), getBlockedRoads());
        List<EntityID> path = this.routeSearcher.getPath(time, this.me, getBlockedRoads().get(0));
        if (path != null) {
            Road r = (Road)model.getEntity(path.get(path.size() - 1));
            Blockade b = getTargetBlockade(r, -1);
            return PoliceAction.move(this, time, path, b.getX(), b.getY());
        }
        return PoliceAction.move(this, time, this.routeSearcher.randomWalk());
    }

    private List<EntityID> getBlockedRoads() {
        Collection<StandardEntity> e = model.getEntitiesOfType(StandardEntityURN.ROAD);
        List<EntityID> result = new ArrayList<EntityID>();
        for (StandardEntity next : e) {
            Road r = (Road)next;
            if (r.isBlockadesDefined() && !r.getBlockades().isEmpty()) {
                result.add(r.getID());
            }
        }
        return result;
    }

    private void updateInfo(ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = model.getEntity(next);
            if(entity instanceof Civilian) {
                Civilian civilian = (Civilian)entity;
                if(civilian.getBuriedness() > 0) {
                    manager.addSendMessage(new CivilianMessage(civilian));
                }
            }
            else if(entity instanceof Blockade) {
                this.blockadeSelector.add((Blockade)entity);
            }
            else if(entity instanceof Building) {
                Building b = (Building)entity;
                if(b.isOnFire()) {
                    manager.addSendMessage(new BuildingMessage(b));
                }
            }
        }
    }

    private Blockade getTargetBlockade() {
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
    }

    private Blockade getTargetBlockade(Area area, int maxDistance) {
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
    }

    private int findDistanceTo(Blockade b, int x, int y) {
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
    }
}
