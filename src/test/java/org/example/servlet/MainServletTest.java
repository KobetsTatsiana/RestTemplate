package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class MainServletTest {

    @Test
    public void testDoGet() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);


        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        MainServlet servlet = new MainServlet();
        servlet.doGet(request, response);

        writer.flush();
        assert (stringWriter.toString().equals("Hello"));
    }
}
