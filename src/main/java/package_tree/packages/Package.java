package package_tree.packages;

import java.util.*;

public class Package {
    private final String name;
    private final Set<String> dependencies;
    private int dependsOnMe;

    Package(String name) {
        this.name = name;
        this.dependencies = new HashSet<>();
        this.dependsOnMe = 0;
    }

    public Package(String name, String[] dependencies) {
        this.name = name;
        List<String> depList = new ArrayList(Arrays.asList(dependencies));
        this.dependencies = new HashSet<>(depList);
        this.dependsOnMe = 0;
    }

    public boolean isDependentOn(String dependency) {
        return dependencies.contains(dependency);
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public String getName() {
        return name;
    }

    public int getDependsOnMe() {
        return dependsOnMe;
    }

    public void updateDependsOnMe(int value) {
        dependsOnMe = dependsOnMe + value;
    }
}
