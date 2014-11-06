package comlib.adk.team.tactics.straight;

import comlib.adk.team.tactics.PoliceForceTactics;
import comlib.adk.util.action.PoliceAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.target.BlockadeSelector;
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

import java.util.List;

public abstract class StraightPolice extends PoliceForceTactics{

    public BlockadeSelector blockadeSelector;

    public RouteSearcher routeSearcher;

    @Override
    public void preparation() {
        this.model.indexClass(StandardEntityURN.ROAD);
        this.routeSearcher = this.getRouteSearcher();
        this.blockadeSelector = this.getBlockadeSelector();
    }

    public abstract BlockadeSelector getBlockadeSelector();

    public abstract RouteSearcher getRouteSearcher();

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        this.updateInfo(changed, manager);

        if(this.target == null) {
            this.target = this.blockadeSelector.getTarget(time);
        }
        Blockade blockade = (Blockade)this.model.getEntity(this.target);
        if(blockade != null) {
            if(blockade.getPosition().equals(this.location.getID())) {
                List<Line2D> lines = GeometryTools2D.pointsToLines(GeometryTools2D.vertexArrayToPoints(blockade.getApexes()), true);
                double best = Double.MAX_VALUE;
                Point2D bestPoint = null;
                Point2D origin = new Point2D(this.me.getX(), this.me.getY());
                for (Line2D next : lines) {
                    Point2D closest = GeometryTools2D.getClosestPointOnSegment(next, origin);
                    double d = GeometryTools2D.getDistance(origin, closest);
                    if (d < best) {
                        best = d;
                        bestPoint = closest;
                    }
                }
                if(bestPoint != null) {
                    Vector2D v = bestPoint.minus(new Point2D(this.me.getX(), this.me.getY()));
                    v = v.normalised().scale(1000000);
                    return PoliceAction.clear(this, time, (int) (this.me.getX() + v.getX()), (int) (this.me.getY() + v.getY()));
                }
            }
            else {
                List<EntityID> path = this.routeSearcher.getPath(time, this.me, blockade);
                return path != null ? PoliceAction.move(this, time, path) : PoliceAction.move(this, time, this.routeSearcher.randomWalk());
            }
        }
        else {
            this.blockadeSelector.remove(this.target);
            this.target = null;
        }
        return PoliceAction.move(this, time, this.routeSearcher.randomWalk());
    }

    private void updateInfo(ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = this.model.getEntity(next);
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

}
