package com.scaler.productservicejan31capstone.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CalculatorControllerTest
{
//    CalculatorService calculatorService
//            = Mockito.mock(CalculatorService.class);
//
//    CalculatorController calculatorController
//            = new CalculatorController(calculatorService);

    @MockitoBean
    CalculatorService calculatorService;

    @Autowired
    CalculatorController calculatorController;


    @Test
    public void testAddWhenTwoIntegersArePassedReturnsInteger()
    {
        when(calculatorService.addInService(5, 10))
                .thenReturn(15);

        when(calculatorService.addInService(6, 10))
                .thenReturn(16);

        //Arrange
        int a = 6;
        int b = 10;
        int expectedResult = 16;

        //Act
        int actualResult = calculatorController.add(a, b);

        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddWhenAnyTwoIntegersArePassedReturnsInteger()
    {
        when(calculatorService.addInService(anyInt(), anyInt()))
                .thenReturn(15);

        //Arrange
        int a = 6000;
        int b = 100000;
        int expectedResult = 15;

        //Act
        int actualResult = calculatorController.add(a, b);

        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
