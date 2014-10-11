package comlib.adk.launcher;

import comlib.adk.team.Team;
import rescuecore2.config.Config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class TeamLoader {
    private Map<String, Team> teamMap;
    private List<String> nameList;
    private Random random;
    
    public TeamLoader(File dir, Config config) {
        this.teamMap = new HashMap<>();
        this.nameList = new ArrayList<>();
        this.random = new Random((new Date()).getTime());
        this.load(dir, config);
    }

    private void load(File dir, Config config) {
        if (!dir.exists()) {
            dir.mkdir();
        }
        URLClassLoader loader= (URLClassLoader)this.getClass().getClassLoader();

        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                this.load(file, config);
            }
            else if (file.getName().endsWith(".jar")) {
                this.loadTeam(file, loader, config);
            }
        }
    }

    private void loadTeam(File file, URLClassLoader loader, Config config) {
        try {
            //add url
            Method m = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            m.setAccessible(true);
            m.invoke(loader, new Object[]{file.toURI().toURL()});
            //Team load
            JarFile jar = new JarFile(file);
            Manifest manifest = jar.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String teamClass = attributes.getValue("Team-Class");
            if (teamClass != null) {
                Class<?> plugin = null;
                plugin = loader.loadClass(teamClass);
                Object obj = null;
                obj = plugin.newInstance();
                if(obj instanceof Team){
                    Team team = (Team) obj;
                    String name = team.getTeamName();
                    this.nameList.add(name);
                    this.teamMap.put(name, team);
                    //loadedPluginFiles.add(file.getName());
                    team.readConfig(config);
                }
            }
        } catch (IOException e) { //FileOpen
            e.printStackTrace();
        } catch (NoSuchMethodException e) { //reflection
            e.printStackTrace();
        } catch (IllegalAccessException e) { //reflection
            e.printStackTrace();
        } catch (InvocationTargetException e) { //reflection
            e.printStackTrace();
        } catch (ClassNotFoundException e) { //load class
            e.printStackTrace();
        } catch (InstantiationException e) { //load class
            e.printStackTrace();
        }
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
