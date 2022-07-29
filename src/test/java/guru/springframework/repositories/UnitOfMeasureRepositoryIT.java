package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest // <-- this is a "slice" of the JPA stuff from Spring Boot. Testing ACTUAL components, not mocking them!
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    // First test takes c. 185ms (199ms after adding @DirtiesContext)
    @Test
    @DirtiesContext
    public void findByDescription() {
        Optional<UnitOfMeasure> actual = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", actual.get().getDescription());
    }

    // Second test takes only 3ms because the SAME Spring context is used between tests, unless @DirtiesContext
    // is added to a test (this test went to 6ms after adding @DirtiesContext above)
    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> actual = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", actual.get().getDescription());
    }
}