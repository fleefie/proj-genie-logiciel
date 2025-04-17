package fr.cytech.projetgenielogiciel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MainTest {
    private Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void testTest() {
        logger.info("Testing a test that always tests - I mean, passes");
        assertTrue(true);
    }
}
