package br.com.rcc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class SqlUtilTests {

    @Test
    public void simpleTest() {
        SqlUtil util = new SqlUtil( new String[]{ "sql.xml" } );
        String sql = util.get("search1");
        assertNotNull( sql );
    }


}