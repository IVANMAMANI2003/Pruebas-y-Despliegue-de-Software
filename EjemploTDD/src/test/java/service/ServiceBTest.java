package service;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;

public class ServiceBTest {
    @Test
    public void testMultiplicar(){
        ServicioB servicioB = new ServicioBImpl();
        assertEquals(6, servicioB.multiplicar(2,3));
    }
    @Test
    public void testMultiplicarSumar(){
        ServicioA servicioA = new ServicioAImpl();
        ServicioB servicioB = new ServicioBImpl();
        servicioB.setServicioA(servicioA);
        Assert.assertEquals(10, servicioB.multiplicarSumar(2,3,2));
    }
    @Test
    public void testMultiplicarSumarMockito(){
        ServicioA servicioA = Mockito.mock(ServicioA.class);
        Mockito.when(servicioA.sumar(2,3)).thenReturn(5);
        ServicioB servicioB=Mockito.mock(ServicioB.class);
        servicioB.setServicioA(servicioA);
        Mockito.when(servicioB.multiplicarSumar(2, 3, 2)).thenReturn(10);
        int resultado = servicioB.multiplicarSumar(2, 3, 2);
        Assert.assertEquals(10, resultado);
    }
    @Test
    public void testSumarMockitoConServicioB() {
        ServicioA servicioA = Mockito.mock(ServicioA.class);
        Mockito.when(servicioA.sumar(3, 3)).thenReturn(6);
        ServicioB servicioB = new ServicioBImpl(); // No es mock
        servicioB.setServicioA(servicioA);
        int resultado = servicioB.getServicioA().sumar(3, 3);
        assertEquals(6, resultado);
    }
}
