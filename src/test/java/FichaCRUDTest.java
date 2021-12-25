import com.angcar.database.DataBaseController;
import com.angcar.model.Ficha;
import com.angcar.repository.FichaRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Ficha")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FichaCRUDTest {

    @BeforeAll
    public static void beforeAll() {
        String sqlFile = System.getProperty("user.dir") + File.separator + "sql" + File.separator + "empresa.sql";
        System.out.println(sqlFile);

        try {
            DataBaseController dataBaseController = DataBaseController.getInstance();
            dataBaseController.open();
            dataBaseController.initData(sqlFile);
        } catch (SQLException var3) {
            System.err.println("Error al conectar al servidor de Base de Datos: " + var3.getMessage());
            System.exit(1);
        } catch (FileNotFoundException var4) {
            System.err.println("Error al leer el fichero de datos iniciales: " + var4.getMessage());
            System.exit(1);
        }
    }

    @Test
    @Order(1)
    @DisplayName("Get All Ficha")
    void getAllTest() throws SQLException {
        FichaRepository ficha = new FichaRepository();
        Optional<List<Ficha>> fichas = ficha.findAll();

        assumeTrue(fichas.isPresent());

        Ficha ficha1 = fichas.get().get(0);

            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(fichas),
                    () -> Assertions.assertEquals("06362a0b-6927-4190-9c85-df9e8ed2f933",
                            ficha1.getUuid_ficha()),
                    () -> Assertions.assertEquals("1c311b93-b9f7-4c91-94bc-6938d3bc3499",
                            ficha1.getUuid_programador()),
                    () -> Assertions.assertEquals("4e253552-73a3-4a74-bf1d-6c45db815a91",
                            ficha1.getUuid_proyecto()));
    }

    @Test
    @Order(2)
    @DisplayName("Find Ficha by ID")
    void findByIdTest() throws SQLException {
        FichaRepository ficha = new FichaRepository();

        assumeTrue(ficha.getById("06362a0b-6927-4190-9c85-df9e8ed2f933").isPresent());

            Ficha fichaId = ficha.getById("06362a0b-6927-4190-9c85-df9e8ed2f933").get();
            Assertions.assertAll("comprobaciones test findById",
                    () -> Assertions.assertNotNull(fichaId),
                    () -> Assertions.assertEquals("06362a0b-6927-4190-9c85-df9e8ed2f933",
                            fichaId.getUuid_ficha()),
                    () -> Assertions.assertEquals("1c311b93-b9f7-4c91-94bc-6938d3bc3499",
                            fichaId.getUuid_programador()),
                    () -> Assertions.assertEquals("4e253552-73a3-4a74-bf1d-6c45db815a91",
                            fichaId.getUuid_proyecto()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Ficha")
    final void saveTest() throws SQLException {
        Ficha ficha = new Ficha("06362a0b-6927-4190-9c85-df9e8ed2f921",
                "ab05d17f-7e4b-4d6b-872d-ea04b9111051",
                "012984ee-1fe2-4983-ac23-8b16bd99ba12");
        FichaRepository fichaRepository = new FichaRepository();

        Optional<Ficha> opt = fichaRepository.insert(ficha);
        assumeTrue(opt.isPresent());

            Ficha insert = opt.get();
            Assertions.assertAll("comprobaciones test save",
                    () -> Assertions.assertNotNull(insert),
                    () -> Assertions.assertEquals(insert, ficha));
    }

    @Test
    @Order(4)
    @DisplayName("Update Ficha")
    final void updateTest() throws SQLException {
        Ficha ficha = new Ficha("06362a0b-6927-4190-9c85-df9e8ed2f933",
                "ab05d17f-7e4b-4d6b-872d-ea04b9111041",
                "012984ee-1fe2-4983-ac23-8b16bd99bab9");
        FichaRepository fichaRepository = new FichaRepository();

        assumeTrue(fichaRepository.update(ficha).isPresent());

            Ficha update = fichaRepository.update(ficha).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, ficha));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Ficha")
    final void deleteTest() throws SQLException {
        FichaRepository repository = new FichaRepository();

        String id = "06362a0b-6927-4190-9c85-df9e8ed2f933";
        Optional<Ficha> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_ficha());
    }
}