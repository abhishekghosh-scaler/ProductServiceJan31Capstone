package com.scaler.productservicejan31capstone.calculator;

import org.springframework.stereotype.Controller;

@Controller
public class CalculatorController
{
    public CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService)
    {
        this.calculatorService = calculatorService;
    }


    public int add(int a, int b)
    {
        System.out.println("Controller: Some logic here");
        System.out.println("Controller: Some logic before add");
        int result = calculatorService.addInService(a,b);
        System.out.println("Controller: Some logic after add");

        return result;
    }
}
