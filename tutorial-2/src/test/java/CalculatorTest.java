import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calc = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calc.add(2, 3));
    }

    // first Junit test written
    @Test
    public void testSubstract() {
        assertEquals(5, calc.subtract(10, 5));
    }

    // second Junit test written
    @Test
    public void testDivide() {
        assertEquals(5, calc.divide(25, 5));
    }

    // third Junit test written
    @Test
    public void testMult() {
        assertEquals(25, calc.mult(5, 5));
    }

}