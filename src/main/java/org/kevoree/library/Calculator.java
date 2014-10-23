package org.kevoree.library;

import org.kevoree.annotation.Input;
import org.kevoree.annotation.ComponentType;

@ComponentType
public class Calculator {

    @Input
    public double sum(double a, double b) {
        return a + b;
    }

    @Input
    public boolean isPositive(int val) {
        return val > 0;
    }
}
