package br.com.rqgr.infrastructure;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/**
 * Store UUID in an optimized way.
 *
 * @see https://www.percona.com/blog/2014/12/19/store-uuid-optimized-way/
 * @see https://en.wikipedia.org/wiki/Universally_unique_identifier
 *
 * With Dash 000000000011111111112222222222333333
 * 012345678901234567890123456789012345 1xxxxxxx-2xxx-3xxx-4xxx-5xxxxxxxxxxx
 *
 * Without Dash 00000000001111111111222222222233
 * 01234567890123456789012345678901 1xxxxxxx2xxx3xxx4xxx5xxxxxxxxxxx
 *
 * Ordered Without Dash 00000000001111111111222222222233
 * 01234567890123456789012345678901 3xxx2xxx1xxxxxxx4xxx5xxxxxxxxxxx
 */
public final class OrderedUUIDHelper {

    private static final int LENGTH_TIME_LOW = 8;
    private static final int LENGTH_TIME_MID = 4;
    private static final int LENGTH_TIME_HI_AND_VERSION = 4;
    private static final int LENGTH_CLOCK_SEQ_AND_RES = 4;
    //private static final int LENGTH_NODE = 12;

    private static final int BEGIN_TIME_LOW = 0;
    private static final int BEGIN_TIME_MID = BEGIN_TIME_LOW + LENGTH_TIME_LOW + 1;
    private static final int BEGIN_TIME_HI_AND_VERSION = BEGIN_TIME_MID + LENGTH_TIME_MID + 1;
    private static final int BEGIN_CLOCK_SEQ_AND_RES = BEGIN_TIME_HI_AND_VERSION + LENGTH_TIME_HI_AND_VERSION + 1;
    //private static final int BEGIN_NODE = BEGIN_CLOCK_SEQ_AND_RES + LENGTH_CLOCK_SEQ_AND_RES + 1;

    private static final int END_TIME_LOW = BEGIN_TIME_LOW + LENGTH_TIME_LOW;
    private static final int END_TIME_MID = BEGIN_TIME_MID + LENGTH_TIME_MID;
    private static final int END_TIME_HI_AND_VERSION = BEGIN_TIME_HI_AND_VERSION + LENGTH_TIME_HI_AND_VERSION;
    //private static final int END_CLOCK_SEQ_AND_RES = BEGIN_CLOCK_SEQ_AND_RES + LENGTH_CLOCK_SEQ_AND_RES;
    //private static final int END_NODE = BEGIN_NODE + LENGTH_NODE;

    private static final int ORDERED_BEGIN_TIME_HI_AND_VERSION = 0;
    private static final int ORDERED_BEGIN_TIME_MID = ORDERED_BEGIN_TIME_HI_AND_VERSION + LENGTH_TIME_HI_AND_VERSION;
    private static final int ORDERED_BEGIN_TIME_LOW = ORDERED_BEGIN_TIME_MID + LENGTH_TIME_MID;
    private static final int ORDERED_BEGIN_CLOCK_SEQ_AND_RES = ORDERED_BEGIN_TIME_LOW + LENGTH_TIME_LOW;
    private static final int ORDERED_BEGIN_NODE = ORDERED_BEGIN_CLOCK_SEQ_AND_RES + LENGTH_CLOCK_SEQ_AND_RES;

    private static final int ORDERED_END_TIME_HI_AND_VERSION = ORDERED_BEGIN_TIME_HI_AND_VERSION + LENGTH_TIME_HI_AND_VERSION;
    private static final int ORDERED_END_TIME_MID = ORDERED_BEGIN_TIME_MID + LENGTH_TIME_MID;
    private static final int ORDERED_END_TIME_LOW = ORDERED_BEGIN_TIME_LOW + LENGTH_TIME_LOW;
    private static final int ORDERED_END_CLOCK_SEQ_AND_RES = ORDERED_BEGIN_CLOCK_SEQ_AND_RES + LENGTH_CLOCK_SEQ_AND_RES;
    //private static final int ORDERED_END_NODE = ORDERED_BEGIN_NODE + LENGTH_NODE;

    private OrderedUUIDHelper() {
    }

    public static String randomOrderedUUID() {
        UUID uuid = UUID.randomUUID();
        return fromUUID(uuid);
    }

    public static String fromUUID(UUID uuid) {
        String uuidStr = uuid.toString();
        StringBuilder builder = new StringBuilder(uuidStr.length());
        builder.append(uuidStr.substring(BEGIN_TIME_HI_AND_VERSION, END_TIME_HI_AND_VERSION));
        builder.append(uuidStr.substring(BEGIN_TIME_MID, END_TIME_MID));
        builder.append(uuidStr.substring(BEGIN_TIME_LOW, END_TIME_LOW));
        builder.append(uuidStr.substring(BEGIN_CLOCK_SEQ_AND_RES).replaceAll("-", ""));
        return StringUtils.upperCase(builder.toString());
    }

    public static UUID orderedUUIDtoUUID(String orderedUUID) {
        StringBuilder builder = new StringBuilder(orderedUUID.length());
        builder.append(orderedUUID.substring(ORDERED_BEGIN_TIME_LOW, ORDERED_END_TIME_LOW));
        builder.append('-');
        builder.append(orderedUUID.substring(ORDERED_BEGIN_TIME_MID, ORDERED_END_TIME_MID));
        builder.append('-');
        builder.append(orderedUUID.substring(ORDERED_BEGIN_TIME_HI_AND_VERSION, ORDERED_END_TIME_HI_AND_VERSION));
        builder.append('-');
        builder.append(orderedUUID.substring(ORDERED_BEGIN_CLOCK_SEQ_AND_RES, ORDERED_END_CLOCK_SEQ_AND_RES));
        builder.append('-');
        builder.append(orderedUUID.substring(ORDERED_BEGIN_NODE));
        return UUID.fromString(builder.toString());
    }

}
