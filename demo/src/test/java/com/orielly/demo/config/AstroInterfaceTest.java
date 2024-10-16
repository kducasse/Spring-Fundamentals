package com.orielly.demo.config;

import com.orielly.demo.json.AstroResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AstroInterfaceTest {

    @Autowired
    private AstroInterface astroInterface;

    @Test
    void getResponse() {
        AstroResponse response = astroInterface.getResponse();
        assertEquals("success", response.message());
        assertTrue(response.number() >= 0);
        assertEquals(response.people().size(), response.number());
        System.out.println(response);
    }
}