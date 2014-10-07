package comlib.adk.launcher;

import comlib.adk.team.Team;

import java.io.File;
import java.net.URLClassLoader;
import java.util.*;

public class TeamLoader {
    private Map<String, Team> teamMap;
    private List<String> nameList;
    private Random random;
    public TeamLoader(File dir) {
        this.teamMap = new HashMap<>();
        this.nameList = new ArrayList<>();
        this.random = new Random((new Date()).getTime());
        this.load(dir);
    }

    private void load(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
        URLClassLoader loader= (URLClassLoader)this.getClass().getClassLoader();

        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                this.load(file);
            }
            else if(file.getName().endsWith(".jar")) {
                this.loadTeam(file, loader);
            }
        }
    }

    private void loadTeam(File file, URLClassLoader loader) {

    }

    public Team get(String name) {
        return "random".equals(name) ? this.getTeam(name) : this.getRandomTeam();
    }

    public Team getTeam(String name) {
        Team team = this.teamMap.get(name);
        return team == null ? this.getRandomTeam() : team;
    }

    public Team getRandomTeam() {
        return this.teamMap.get(this.nameList.get(this.random.nextInt(this.nameList.size())));
    }
}
