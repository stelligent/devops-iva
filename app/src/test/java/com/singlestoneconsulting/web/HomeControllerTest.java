package com.singlestoneconsulting.web;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HomeControllerTest {

    HomeController controller = new HomeController();

    @Test
    public void testHome() throws Exception {
        assertThat(controller.index(), is("home"));
    }
}
