package comlib.adk.launcher;

import comlib.adk.sample.DummyTeam;
import comlib.adk.team.Team;
import rescuecore2.config.Config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
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

    public Team get(String name) {
        return "random".equals(name) ? this.getRandomTeam() : this.getTeam(name);
    }

    public Team getTeam(String name) {
        Team team = this.teamMap.get(name);
        return team == null ? this.getRandomTeam() : team;
    }

    public Team getRandomTeam() {
        return this.teamMap.get(this.nameList.get(this.random.nextInt(this.nameList.size())));
    }

    private void load(File file, Config config) {
        this.addDummyTeam();
        if (!file.exists()) {
            file.mkdir();
            return;
        }

        URLClassLoader loader = (URLClassLoader) this.getClass().getClassLoader();
        List<String> list = new ArrayList<>();
        this.loadJar(file, loader, list);
        this.loadTeam(loader, list, config);
    }

    private void addDummyTeam() {
        Team team = new DummyTeam();
        String name = team.getTeamName();
        this.nameList.add(name);
        this.teamMap.put(name, team);
    }

    private void loadJar(File file, URLClassLoader loader, List<String> list) {
        if(file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                this.loadJar(file1, loader, list);
            }
        }
        else if (file.getName().endsWith(".jar")) {
            System.out.println("Found Jar : " + file.getName());
            try {
                //add url
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
                method.setAccessible(true);
                method.invoke(loader, new Object[]{file.toURI().toURL()});
                //load target class name
                JarFile jar = new JarFile(file);
                Manifest manifest = jar.getManifest();
                Attributes attributes = manifest.getMainAttributes();
                String target = attributes.getValue("Team-Class");
                if(target != null) {
                    System.out.println("Found Target Class : " + target);
                    list.add(target);
                }
            } catch (NoSuchMethodException e) { //URLClassLoader
                e.printStackTrace();
            } catch (InvocationTargetException e) { //File
                e.printStackTrace();
            } catch (MalformedURLException e) { //File
                e.printStackTrace();
            } catch (IllegalAccessException e) { //File
                e.printStackTrace();
            } catch (IOException e) { //File
                e.printStackTrace();
            }
        }
    }

    private void loadTeam(URLClassLoader loader, List<String> list, Config config) {
        for (String target : list) {
            try {
                Class teamClass = loader.loadClass(target);
                Object obj = teamClass.newInstance();
                if (obj instanceof Team) {
                    Team team = (Team) obj;
                    String name = team.getTeamName();
                    System.out.println("Load Success : " + name);
                    this.nameList.add(name);
                    this.teamMap.put(name, team);
                    team.readConfig(config);
                }
            } catch (ClassNotFoundException e) { //loadClass
                e.printStackTrace();
            } catch (InstantiationException e) { //instance
                e.printStackTrace();
            } catch (IllegalAccessException e) { //instance
                e.printStackTrace();
            }
        }
    }

    /*private void load(File dir, Config config) {
        Team team = new SampleTeam();
        String name = team.getTeamName();
        this.nameList.add(name);
        this.teamMap.put(name, team);

        if (!dir.exists()) {
            if(!dir.mkdir()) {
                return;
            }
        }

        URLClassLoader loader= (URLClassLoader)this.getClass().getClassLoader();

        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                this.load(file, config);
            }
            else if (file.getName().endsWith(".jar")) {
                System.out.println("Found Jar : " + file.getName());
                this.loadTeam(file, loader, config);
            }
        }
    }*/

    /*private void loadTeam(File file, URLClassLoader loader, Config config) {
        try {
            //System.out.println("add url");
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Method m = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            m.setAccessible(true);
            m.invoke(loader, new Object[]{file.toURI().toURL()});
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //System.out.println("load manifest");
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            JarFile jar = new JarFile(file);
            Manifest manifest = jar.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String target = attributes.getValue("Team-Class");
            if (target != null) {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                //System.out.println("target class : " + targetClass);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                //loader = (URLClassLoader) getClass().getClassLoader();
                Class<?> teamClass = loader.loadClass(target);
                Object obj = teamClass.newInstance();
                if(obj instanceof Team) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////////
                    //System.out.println("init Team");
                    ////////////////////////////////////////////////////////////////////////////////////////////////////
                    Team team = (Team) obj;
                    String name = team.getTeamName();
                    System.out.println("Load Success : " + name);
                    this.nameList.add(name);
                    this.teamMap.put(name, team);
                    //System.out.println("load Team : " + team.getTeamName());
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
    }*/
}
