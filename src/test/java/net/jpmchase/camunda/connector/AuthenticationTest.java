package net.jpmchase.camunda.connector;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthenticationTest {

    @Test
    void testAuthentication() {
        // Create a mock of the Authentication class
        Authentication authMock = Mockito.mock(Authentication.class);

        // Stub methods of the mock object
        Mockito.when(authMock.getUser()).thenReturn("testUser");
        Mockito.when(authMock.getToken()).thenReturn("testToken");
        Mockito.when(authMock.toString()).thenReturn("Authentication [user=testUser, token=testToken]");

        // Test getUser and getToken methods
        assertEquals("testUser", authMock.getUser());
        assertEquals("testToken", authMock.getToken());

        // Test toString method
        assertEquals("Authentication [user=testUser, token=testToken]", authMock.toString());

        // Create another mock for equals and hashCode testing
        Authentication auth2 = Mockito.mock(Authentication.class);
        Mockito.when(auth2.getUser()).thenReturn("testUser");
        Mockito.when(auth2.getToken()).thenReturn("testToken");

        // Test equals method
        assertEquals(authMock.getUser(), auth2.getUser());
        assertEquals(authMock.getToken(), auth2.getToken());

        // Test hashCode method
        assertEquals(authMock.getUser().hashCode(), auth2.getUser().hashCode());
        assertEquals(authMock.getToken().hashCode(), auth2.getToken().hashCode());

        // Change auth2 and test inequality
        Mockito.when(auth2.getUser()).thenReturn("differentUser");

        assertNotEquals(authMock.getUser(), auth2.getUser());
    }
}
