package services;

import java.util.HashSet;

public class PathService {
    private HashSet<String> pathsSet = new HashSet<String>();
    public void initPaths() {
        pathsSet.add("/controller");
        pathsSet.add("/check");
        pathsSet.add("/session");
    }

    public Boolean checkPath(String path) {
        if(pathsSet.contains(path)) return false;
        else return true;
    }
}
