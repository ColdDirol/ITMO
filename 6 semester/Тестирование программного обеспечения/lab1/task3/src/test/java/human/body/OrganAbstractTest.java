package human.body;

import org.junit.jupiter.api.Test;
import vladimir.human.body.OrganAbstract;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganAbstractTest {

    @Test
    void testOrganAbstractCreationValidParameters() {
        assertDoesNotThrow(() -> new OrganAbstract("Орган", "Часть тела", "Владелец") {});
    }

    @Test
    void testOrganAbstractName() {
        OrganAbstract organ = new OrganAbstract("Орган", "Часть тела", "Владелец") {};
        assertEquals("Орган", organ.getName());
    }
}
