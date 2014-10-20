package comlib.adk.util.move;

import java.util.*;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.*;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.misc.collections.LazyMap;

public class MoveTool {
    
    private static final int RANDOM_WALK_LENGTH = 50;
    
    //private StandardWorldModel model;
    
    private Tactics tactics;
    
    private Router router;
    
    //randomWalk向け
    private Map<EntityID, Set<EntityID>> neighbours;
    
    public MoveTool(Tactics t) {
        this.tactics = t;
        this.router = new Router(t.model);
        this.initRandomWalk();
    }
    
    private void initRandomWalk()
    {
        //RandomWalk向け
        this.neighbours = new LazyMap<EntityID, Set<EntityID>>() {
            @Override
            public Set<EntityID> createValue() {
                return new HashSet<EntityID>();
            }
        };
        for (Entity next : this.tactics.model) {
            //if (next instanceof Area)
            if(next instanceof Road) {
                Collection<EntityID> areaNeighbours = ((Area)next).getNeighbours();
                this.neighbours.get(next.getID()).addAll(areaNeighbours);
            }
        }
    }
    
    ///////////////////////////////////////////////////
    
    public List<EntityID> randomWalk() {
        List<EntityID> result = new ArrayList<>(RANDOM_WALK_LENGTH);
        Set<EntityID> seen = new HashSet<>();
        EntityID current = ((Human)this.tactics.me).getPosition();
        for (int i = 0; i < RANDOM_WALK_LENGTH; ++i) {
            result.add(current);
            seen.add(current);
            List<EntityID> possible = new ArrayList<EntityID>(this.neighbours.get(current));
            Collections.shuffle(possible, this.tactics.random);
            boolean found = false;
            for (EntityID next : possible) {
                if (seen.contains(next)) {
                    continue;
                }
                current = next;
                found = true;
                break;
            }
            if (!found) {
                break;
            }
        }
        return result;
    }
    
    public List<EntityID> randomWalkWithoutBuilding() {
        List<EntityID> result = new ArrayList<>(RANDOM_WALK_LENGTH);
        Set<EntityID> seen = new HashSet<>();
        EntityID current = ((Human)this.tactics.me).getPosition();
        for (int i = 0; i < RANDOM_WALK_LENGTH; ++i) {
            result.add(current);
            seen.add(current);
            List<EntityID> possible = new ArrayList<EntityID>(this.neighbours.get(current));
            for (EntityID next : possible) {
                if (this.tactics.model.getEntity(next) instanceof Building) {
                    possible.remove(next);
                }
            }
            Collections.shuffle(possible, this.tactics.random);
            boolean found = false;
            for (EntityID next : possible) {
                if (seen.contains(next)) {
                    continue;
                }
                current = next;
                found = true;
                break;
            }
            if (!found) {
                break;
            }
        }
        return result;
    }
    
    public List<EntityID> getPath(int time, EntityID from, EntityID to) {
        return this.router.getPath(from, to, time);
    }
    
    public List<EntityID> getPath(int time, Human from, Area to) {
        return this.getPath(time, from.getPosition(), to.getID());
    }
    
    public List<EntityID> getPath(int time, Human from, Blockade blockade) {
        return this.getPath(time, from.getPosition(), blockade.getPosition());
    }
    
    public List<EntityID> getPath(int time, EntityID from, List<EntityID> to) {
        List<EntityID> path = new ArrayList<EntityID>();
        List<EntityID> result = new ArrayList<EntityID>();
        for (EntityID e : to) {
            path = this.getPath(time, from, e);
            if(result.isEmpty()) {
                result = path;
            }
            else {
                result = this.getDistance(path) < this.getDistance(result) ? path : result;
            }
        }
        return result;
    }
    
    public int getDistance(List<EntityID> path) {
        int distance = 0;
        for (int i = 1; i < path.size(); i++) {
            distance += this.router.getRoute(path.get(i - 1)).getDistance(this.router.getRoute(path.get(i)));
        }
        return distance;
    }
}
