package kevoree;

import org.kevoree.annotation.*;

@ComponentType
public class NoveltyTester {

    @KevoreeInject
    org.kevoree.api.Context context;

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {}

}



