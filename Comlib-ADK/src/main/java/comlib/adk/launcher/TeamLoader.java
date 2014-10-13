package comlib.adk.launcher;

import comlib.adk.sample.SampleTeam;
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
                //System.out.println("load jar : " + file.getName());
                this.loadTeam(file, loader, config);
            }
        }
    }

    //Teamが入っていないライブラリの読み込みどうするか
    private void loadTeam(File file, URLClassLoader loader, Config config) {
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
    }

    /*private void loadTeam(File file, URLClassLoader loader, Config config) {
        try {
            System.out.println("add url");
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Method m = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            m.setAccessible(true);
            m.invoke(loader, new Object[]{file.toURI().toURL()});
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("load Team");
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            JarFile jar = new JarFile(file);
            Manifest manifest = jar.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String teamClass = attributes.getValue("Team-Class");
            if (teamClass != null) {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("Target Class : " + teamClass);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                loader = (URLClassLoader) getClass().getClassLoader();//URLClassLoaderを受け取る
                Method m1 = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});//addURLメソッドのMethodインスタンスを取得
                m1.setAccessible(true);//アクセスできるようにする
                m1.invoke(loader, new Object[]{file.toURI().toURL()});//fileのURLを引数に渡してaddURLメソッドを実行

                loader = (URLClassLoader) getClass().getClassLoader();
                URL url = file.getCanonicalFile().toURI().toURL();
                loader = new URLClassLoader( new URL[] { url });
                //Class clazz=loader.load(name);
                Class<?> plugin = loader.loadClass(teamClass);
                Object obj = null;
                obj = plugin.newInstance();
                if(obj instanceof Team){
                    ////////////////////////////////////////////////////////////////////////////////////////////////////
                    System.out.println("init Team");
                    Team team = (Team) obj;
                    String name = team.getTeamName();
                    this.nameList.add(name);
                    this.teamMap.put(name, team);
                    //loadedPluginFiles.add(file.getName());
                    System.out.println("load Team : " + team.getTeamName());
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

    public Team get(String name) {
        return "random".equals(name) ? this.getRandomTeam() : this.getTeam(name);
    }

    public Team getTeam(String name) {
        Team team = this.teamMap.get(name);
        //if(team != null)
            //System.out.println("load teamMap : " + team.getTeamName());
        return team == null ? this.getRandomTeam() : team;
    }

    public Team getRandomTeam() {
        return this.teamMap.get(this.nameList.get(this.random.nextInt(this.nameList.size())));
    }
}
