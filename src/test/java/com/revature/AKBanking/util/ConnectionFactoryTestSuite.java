package com.revature.AKBanking.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.revature.AKBanking.util.ConnectionFactory;

import java.sql.Connection;

public class ConnectionFactoryTestSuite {

    @Test
    public void testValidConnection(){
        Connection connection = assertDoesNotThrow(() -> ConnectionFactory.getConnectionFactory().getConnection());
        assertNotNull(connection);
    }
}
