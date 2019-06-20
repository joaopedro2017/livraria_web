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
public class graficoEmprestimoAssuntoBeanTest {

    public graficoEmprestimoAssuntoBeanTest() {
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
     * Test of init method, of class graficoEmprestimoAssuntoBean.
     */
    @Test
    public void testInit() {
        try {
            System.out.println("init");
            graficoEmprestimoAssuntoBean instance = new graficoEmprestimoAssuntoBean();
            instance.init();
        } catch (Exception e) {
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of getLivrosModel method, of class graficoEmprestimoAssuntoBean.
     */
    @Test
    public void testGetLivrosModel() {
        try {
            System.out.println("getLivrosModel");
            graficoEmprestimoAssuntoBean instance = new graficoEmprestimoAssuntoBean();
            BarChartModel expResult = null;
            BarChartModel result = instance.getLivrosModel();
            assertEquals(expResult, result);
        } catch (Exception e) {
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        }
    }

}
