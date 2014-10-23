package fr.inria.diverse.noveltytesting;

import fr.inria.diverse.noveltytesting.model.Interface;
import fr.inria.diverse.noveltytesting.visitor.InputOutputVisitor;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.api.Callback;
import org.kevoree.api.Context;
import org.kevoree.api.ModelService;
import org.kevoree.api.Port;

@ComponentType
public class NoveltyTester {

    private NoveltyGeneration generator = new NoveltyGenerationImpl();

    @KevoreeInject
    private Context context;

    @KevoreeInject
    private ModelService modelService;

    @Output
    private Port remoteService;

    @Start
    public void start() {
        NoveltyGeneration gen = new NoveltyGenerationImpl();
        try {
            Class<?> clazz = Class.forName("org.kevoree.library.Calculator");
            Interface i = gen.generateModel(clazz);
            gen.generateData(i);
            i.accept(new InputOutputVisitor());

            i.getMethods().forEach(m -> {
                for (Object o : m.getParametersValue()) {
                    System.out.println("param value= "+o);
                    System.out.println("param type= "+o.getClass().getTypeName());
                }

                remoteService.call(m.getParametersValue(), new Callback() {
                    @Override
                    public void onSuccess(Object o) {
                        m.setReturnVal(o);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //m.setThrownException(throwable);
                    }
                });
            });

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}