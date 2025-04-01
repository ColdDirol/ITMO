package human;

import org.junit.jupiter.api.Test;
import vladimir.human.Monster;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    void testMonsterCreationValidParameters() {
        assertDoesNotThrow(() -> new Monster("Гаргулья", 100));
    }

    @Test
    void testMonsterHasSecondHead() {
        Monster monster = new Monster("Гаргулья", 100);
        assertNotNull(monster.getSecondHead());
        assertEquals("Вторая голова", monster.getSecondHead().getName());
    }

    @Test
    void testMonsterCreationInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> new Monster("", 100));
    }

    @Test
    void testMonsterCreationInvalidAge() {
        assertThrows(IllegalArgumentException.class, () -> new Monster("Гаргулья", -1));
    }
}
