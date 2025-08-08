import org.junit.jupiter.api.Test;
import project.service.DeckService;
import project.service.PersistenceService;
import project.service.RaceService;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Testing {

    @Test
    void raceCompletes() {
        DeckService d = new DeckService(123);
        PersistenceService p = new PersistenceService(Path.of("test-data"));
        RaceService r = new RaceService(d, p, 5);
        r.runRace();
        assertEquals(4, r.getFinishOrder().size());
    }

    @Test
    void drawReturnsCard() {
        DeckService d = new DeckService(42);
        assertNotNull(d.draw());
    }

}
