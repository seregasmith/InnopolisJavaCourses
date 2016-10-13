package tdd.calculator;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.innopolis.HelloWorldTest;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Smith on 13.10.2016.
 */
public class CalculatorTest {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CalculatorTest.class);
    Calculator calc = new Calculator();
    @Test
    public void testAdd(){
        // test double
        for(int i = 0; i < 10_000; i++){
            Double a = ThreadLocalRandom.current().nextDouble();
            Double b = ThreadLocalRandom.current().nextDouble();
            Double expected = a + b;
            Double res = calc.add(a, b);
            try {
                Assert.assertEquals(expected, res);
            }catch (ComparisonFailure e){
                logger.error("a={} b={} res={}, expected={}", a,b,expected,res);
                throw e;
            }
        }
    }

    @Test
    public void testSub(){
        // test double
        for(int i = 0; i < 10_000; i++){
            Double a = ThreadLocalRandom.current().nextDouble();
            Double b = ThreadLocalRandom.current().nextDouble();
            Double expected = a - b;
            Double res = calc.sub(a, b);
            try {
                Assert.assertEquals(expected, res);
            }catch (ComparisonFailure e){
                logger.error("a={} b={} res={}, expected={}", a,b,expected,res);
                throw e;
            }
        }
    }

    @Test
    public void testDiv(){
        // test double
        for(int i = 0; i < 10_000; i++){
            Double a = ThreadLocalRandom.current().nextDouble();
            Double b = ThreadLocalRandom.current().nextDouble();
            Double expected = a / b;
            Double res = calc.div(a, b);
            try {
                Assert.assertEquals(expected, res);
            }catch (ComparisonFailure e){
                logger.error("a={} b={} res={}, expected={}", a,b,expected,res);
                throw e;
            }
        }
    }

    @Test
    public void testMult(){
        // test double
        for(int i = 0; i < 10_000; i++){
            Double a = ThreadLocalRandom.current().nextDouble();
            Double b = ThreadLocalRandom.current().nextDouble();
            Double expected = a * b;
            Double res = calc.mult(a, b);
            try {
                Assert.assertEquals(expected, res);
            }catch (ComparisonFailure e){
                logger.error("a={} b={} res={}, expected={}", a,b,expected,res);
                throw e;
            }
        }
    }

    @Test
    public void testToNeg(){
        // test double
        for(int i = 0; i < 10_000; i++){
            Double a = ThreadLocalRandom.current().nextDouble();
            Double expected = a * -1;
            Double res = calc.toNeg(a);
            try {
                Assert.assertEquals(expected, res);
            }catch (ComparisonFailure e){
                logger.error("a={} res={}, expected={}", a,expected,res);
                throw e;
            }
        }
    }
}
