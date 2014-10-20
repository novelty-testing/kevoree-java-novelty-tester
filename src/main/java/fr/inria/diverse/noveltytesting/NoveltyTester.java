package fr.inria.diverse.noveltytesting;

import org.kevoree.annotation.*;
import org.kevoree.api.ModelService;
import org.kevoree.api.handler.ModelListenerAdapter;

@ComponentType
public class NoveltyTester extends ModelListenerAdapter {

    @KevoreeInject
    private ModelService modelService;

    @Start
    public void start() {
        modelService.registerModelListener(this);
    }

    @Stop
    public void stop() {
        modelService.unregisterModelListener(this);
    }

    @Override
    public void modelUpdated() {
        // TODO
        // retrieve every component classes that run on this node platform
        // using the current Kevoree model
        // and do the novelty-testing on them
    }
}