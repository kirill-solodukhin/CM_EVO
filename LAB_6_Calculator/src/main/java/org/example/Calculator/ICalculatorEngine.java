package org.example.Calculator;

import org.example.Operation;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ICalculatorEngine
{
    double performOperation(Operation operation);
    void defineOperation(String sign, ThreeParamFunc func);
    void defineOperation(String sign, BiFunction<Double, Double, Double> func);
    void defineOperation(String sign, Function<Double, Double> func);
}
