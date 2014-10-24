package fr.inria.diverse.noveltytesting;

import fr.inria.diverse.noveltytesting.model.Interface;
import fr.inria.diverse.noveltytesting.model.MethodOutput;
import fr.inria.diverse.noveltytesting.modelgeneration.ModelGeneration;
import fr.inria.diverse.noveltytesting.modelgeneration.ModelGenerationImpl;
import fr.inria.diverse.noveltytesting.visitor.InputOutputVisitor;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.api.Callback;
import org.kevoree.api.Port;

@ComponentType
public class NoveltyTester {

    @Output
    private Port remoteService;

    @Start
    public void start() {
        ModelGeneration gen = new ModelGenerationImpl();
        try {
            Class<?> clazz = Class.forName("org.kevoree.library.Calculator");
            Interface i = gen.generateModel(clazz);
            gen.generateData(i);
            i.accept(new InputOutputVisitor());

            i.getMethods().forEach(m -> remoteService.call(m.getParametersValue(), new Callback() {
                @Override
                public void onSuccess(Object o) {
                    MethodOutput mo = new MethodOutput();
                    mo.setReturnVal(o);
                    m.addMethodOutput("foo", mo);
                }

                @Override
                public void onError(Throwable throwable) {
                    //m.setThrownException(throwable);
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}