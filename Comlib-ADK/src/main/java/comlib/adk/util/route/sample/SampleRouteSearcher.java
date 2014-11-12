package comlib.adk.util.route.sample;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteSearcher;
import rescuecore2.misc.collections.LazyMap;
import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.Entity;
import rescuecore2.worldmodel.EntityID;

import java.util.*;

public class SampleRouteSearcher extends RouteSearcher {

    private static final int RANDOM_WALK_LENGTH = 50;

    private Router router;

    private Map<EntityID, Set<EntityID>> neighbours;

    public SampleRouteSearcher(Tactics user) {
        super(user);
        this.router = new Router(user.model);
        this.initRandomWalk();
    }

    private void initRandomWalk()
    {
        //RandomWalk向け
        this.neighbours = new LazyMap<EntityID, Set<EntityID>>() {
            @Override
            public Set<EntityID> createValue() {
                return new HashSet<>();
            }
        };
        for (Entity next : this.tactics.model) {
            if (next instanceof Area) {
            //if(next instanceof Road) {
                Collection<EntityID> areaNeighbours = ((Area)next).getNeighbours();
                this.neighbours.get(next.getID()).addAll(areaNeighbours);
            }
        }
    }

    @Override
    public List<EntityID> noTargetWalk() {
        List<EntityID> result = new ArrayList<>(RANDOM_WALK_LENGTH);
        Set<EntityID> seen = new HashSet<>();
        EntityID current = ((Human)this.tactics.me()).getPosition();
        for (int i = 0; i < RANDOM_WALK_LENGTH; ++i) {
            result.add(current);
            seen.add(current);
            List<EntityID> possible = new ArrayList<>(this.neighbours.get(current));
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
        EntityID current = ((Human)this.tactics.me()).getPosition();
        for (int i = 0; i < RANDOM_WALK_LENGTH; ++i) {
            result.add(current);
            seen.add(current);
            List<EntityID> possible = new ArrayList<>(this.neighbours.get(current));
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

    @Override
    public List<EntityID> getPath(int time, EntityID from, EntityID to) {
        return this.router.getPath(from, to, time);
    }
}
