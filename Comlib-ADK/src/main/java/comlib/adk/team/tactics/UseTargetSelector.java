package comlib.adk.team.tactics;

import comlib.adk.util.target.TargetSelector;

public interface UseTargetSelector<T extends TargetSelector> {

    //public void initTargetSelector();

    public T getTargetSelector();
}
