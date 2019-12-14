/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.primefaces.model.chart.BarChartModel;

/**
 *
 * @author John Peter
 */
public class graficoReservaAssuntoBeanTest {

    public graficoReservaAssuntoBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class graficoReservaAssuntoBean.
     */
    @Test
    public void testInit() {
        try {
            System.out.println("init");
            graficoReservaAssuntoBean instance = new graficoReservaAssuntoBean();
            instance.init();
        } catch (Exception e) {
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of getLivrosModel method, of class graficoReservaAssuntoBean.
     */
    @Test
    public void testGetLivrosModel() {
        try {
            System.out.println("getLivrosModel");
            graficoReservaAssuntoBean instance = new graficoReservaAssuntoBean();
            BarChartModel expResult = null;
            BarChartModel result = instance.getLivrosModel();
            assertEquals(expResult, result);
        } catch (Exception e) {
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        }
    }

}
