//package org.example;
//
//import com.example.wallet.model.Wallet;
//import com.example.wallet.service.WalletService;
//
//import com.example.wallet.servlet.WalletServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class WalletServletTest {
//
//    private WalletServlet servlet;
//    private WalletService walletService;
//
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//
//    private StringWriter responseWriter;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        walletService = mock(WalletService.class);
//        servlet = new WalletServlet();
//
//        var field = WalletServlet.class.getDeclaredField("service");
//        field.setAccessible(true);
//        field.set(servlet, walletService);
//
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//
//        responseWriter = new StringWriter();
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//    }
//
//
//    @Test
//    void doGet_success() throws Exception {
//        Wallet wallet = new Wallet("W1", "USER1", 100.0, "ACTIVE");
//
//        when(request.getPathInfo()).thenReturn("/W1");
//        when(walletService.get("W1")).thenReturn(wallet);
//
//        servlet.doGet(request, response);
//
//        assertTrue(responseWriter.toString().contains("W1"));
//        assertTrue(responseWriter.toString().contains("100.0"));
//    }
//
//    @Test
//    void doPost_success() throws Exception {
//        Wallet wallet = new Wallet("W1", "USER1", 0.0, "ACTIVE");
//
//        when(request.getParameter("userId")).thenReturn("USER1");
//        when(walletService.create("USER1")).thenReturn(wallet);
//
//        servlet.doPost(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_CREATED);
//        assertEquals("W1", responseWriter.toString());
//    }
//
//    @Test
//    void doPut_success() throws Exception {
//        when(request.getPathInfo()).thenReturn("/W1");
//        when(request.getParameter("amount")).thenReturn("50");
//
//        servlet.doPut(request, response);
//
//        verify(walletService).credit("W1", 50.0);
//        assertEquals("Credited", responseWriter.toString());
//    }
//
//    @Test
//    void doDelete_success() throws Exception {
//        when(request.getPathInfo()).thenReturn("/W1");
//
//        servlet.doDelete(request, response);
//
//        verify(walletService).delete("W1");
//        assertEquals("Deleted", responseWriter.toString());
//    }
//
//
//    @Test
//    void doGet_shouldFail_whenWalletNotFound() {
//        when(request.getPathInfo()).thenReturn("/W1");
//        when(walletService.get("W1"))
//                .thenThrow(new RuntimeException("Wallet not found"));
//
//        assertThrows(RuntimeException.class,
//                () -> servlet.doGet(request, response));
//    }
//
//    @Test
//    void doPut_shouldFail_whenAmountIsInvalid() {
//        when(request.getPathInfo()).thenReturn("/W1");
//        when(request.getParameter("amount")).thenReturn("abc");
//
//        assertThrows(NumberFormatException.class,
//                () -> servlet.doPut(request, response));
//    }
//
//    @Test
//    void doPost_shouldFail_whenUserIdIsNull() {
//        when(request.getParameter("userId")).thenReturn(null);
//        when(walletService.create(null))
//                .thenThrow(new RuntimeException("UserId is mandatory"));
//
//        assertThrows(RuntimeException.class,
//                () -> servlet.doPost(request, response));
//    }
//}
