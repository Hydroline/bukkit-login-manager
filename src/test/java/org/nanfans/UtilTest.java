package org.nanfans;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {
    
    @Test
    public void testIpHide() {
        String ip = "192.168.1.100";
        String hiddenIp = Util.ipHide(ip);
        assertEquals("192.**.**100", hiddenIp);
    }
    
    @Test
    public void testGetSec() {
        long sec = Util.getSec();
        assertTrue("getSec should return a positive value", sec > 0);
        assertTrue("getSec should return a reasonable value (less than 24 hours)", sec < 86400);
    }
}
