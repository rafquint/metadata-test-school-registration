/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.com.rqgr.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rafael
 */
@Slf4j
public class AbstractObjectTest {

    private static final String ID_1 = OrderedUUIDHelper.randomOrderedUUID();
    private static final String ID_2 = OrderedUUIDHelper.randomOrderedUUID();
    private static final long CREATED_1 = System.currentTimeMillis();
    private static final long CREATED_2 = System.currentTimeMillis() + 100;
    private static final String JSON_1 = "{\n"
            + "    \"id\": \"" + ID_1 + "\",\n"
            + "    \"created\": " + CREATED_1 + "\n"
            + "}";
    private static final String JSON_2 = "{\n"
            + "    \"id\": \"" + ID_2 + "\",\n"
            + "    \"created\": " + CREATED_2 + ",\n"
            + "    \"innerObject\":\n"
            + "    {\n"
            + "        \"id\": \"" + ID_1 + "\",\n"
            + "        \"created\": " + CREATED_1 + "\n"
            + "    }\n"
            + "}";

    public AbstractObjectTest() {
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AbstractObjectImpl extends AbstractObject {

        @Builder.Default
        private String id = ID_1;
        @Builder.Default
        private long created = CREATED_1;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AbstractComposedObjectImpl extends AbstractObject {

        @Builder.Default
        private String id = ID_2;
        @Builder.Default
        private long created = CREATED_2;
        @Builder.Default
        private AbstractObject innerObject = AbstractObjectImpl.builder().build();
    }

    @Test
    public void testToString() throws JsonProcessingException {
        log.info("toString");
        ObjectMapper mapper = new ObjectMapper();
        AbstractObject instance_1 = new AbstractObjectImpl();
        String result_1 = instance_1.toString();
        assertEquals(mapper.readTree(JSON_1), mapper.readTree(result_1));
        
        AbstractObject instance_2 = new AbstractComposedObjectImpl();
        String result_2 = instance_2.toString();
        assertEquals(mapper.readTree(JSON_2), mapper.readTree(result_2));
    }

    @Test
    public void testEquals() {
        log.info("equals");
        Object simpleThat = AbstractObjectImpl.builder().build();
        AbstractObject simpleThis = new AbstractObjectImpl();
        boolean simpleResult = simpleThis.equals(simpleThat);
        assertTrue(simpleResult);
        
        Object complexThat = AbstractComposedObjectImpl.builder().build();
        AbstractObject complexThis = new AbstractComposedObjectImpl();
        boolean complexResult = complexThis.equals(complexThat);
        assertTrue(complexResult);
        
        assertFalse(simpleThis.equals(complexThat));
        assertFalse(simpleThat.equals(complexThis));
        assertFalse(complexThat.equals(simpleThis));
        assertFalse(complexThis.equals(simpleThat));
    }

    @Test
    public void testHashCode() {
        log.info("hashCode");
        AbstractObject simpleInstance = new AbstractObjectImpl();
        AbstractObject complexInstance = new AbstractComposedObjectImpl();
        int simpleResult = simpleInstance.hashCode();
        int complexResult = complexInstance.hashCode();
        assertNotEquals(simpleResult, complexResult);
        assertEquals(AbstractObjectImpl.builder().build().hashCode(), simpleResult);
        assertEquals(AbstractComposedObjectImpl.builder().build().hashCode(), complexResult);
    }

}
