package br.com.rqgr.infrastructure;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rafael
 */
@Slf4j
public class OrderedUUIDHelperTest {

    private static final String PATTERN = "[0-9a-fA-F]{32}";
    private static final String UUID_STR = "58e0a7d7-eebc-11d8-9669-0800200c9a66";
    private static final String ORDERED_UUID_STR = "11d8eebc58e0a7d796690800200c9a66".toUpperCase();

    @Test
    public void testRandomOrderedUUID() {
        log.info("randomOrderedUUID");
        final String result1 = OrderedUUIDHelper.randomOrderedUUID();
        final String result2 = OrderedUUIDHelper.randomOrderedUUID();
        assertTrue(StringUtils.isNotBlank(result1));
        assertTrue(StringUtils.isNotBlank(result2));
        assertTrue(result1.matches(PATTERN));
        assertTrue(result2.matches(PATTERN));
        assertNotEquals(result1, result2);
    }

    @Test
    public void testFromUUID() {
        log.info("fromUUID");
        final String expResult = ORDERED_UUID_STR;
        final UUID uuid = UUID.fromString(UUID_STR);
        final String result = OrderedUUIDHelper.fromUUID(uuid);
        assertEquals(expResult, result);
    }

    @Test
    public void testOrderedUUIDtoUUID() {
        log.info("orderedUUIDtoUUID");
        final UUID expResult = UUID.fromString(UUID_STR);
        final UUID result = OrderedUUIDHelper.orderedUUIDtoUUID(ORDERED_UUID_STR);
        assertEquals(expResult, result);
    }

}
