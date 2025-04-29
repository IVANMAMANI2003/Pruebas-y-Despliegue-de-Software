package service;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ServiceATest {
    @Test
    public void testSumar(){
        int a = 2;
        int b = 2;
        ServicioA servicioA = new ServicioAImpl();
        assertEquals(4, servicioA.sumar(a,b));
    }
}
