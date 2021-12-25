import com.angcar.database.DataBaseController;
import com.angcar.model.Repositorio;
import com.angcar.repository.RepositorioRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Repositorio")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryCRUDTest {

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
    @DisplayName("Get All Repositorio")
    void getAllTest() throws SQLException {
        RepositorioRepository repository = new RepositorioRepository();
        Optional<List<Repositorio>> repositorios = repository.findAll();
        Date creationDate = Date.valueOf("2021-10-02");

        assumeTrue(repositorios.isPresent());

            Repositorio repositorio = repositorios.get().get(2);
            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(repositorios),
                    () -> Assertions.assertEquals(
                            "fbba0a2b-11dc-46c2-949d-96457277f837", repositorio.getUuid_repositorio()),
                    () -> Assertions.assertEquals("Proyecto videojuego", repositorio.getNombre()),
                    () -> Assertions.assertEquals(creationDate, repositorio.getFecha_creacion()),
                    () -> Assertions.assertEquals("97a53b2b-dd63-4fc4-9126-4ea3947b940a",
                            repositorio.getProyecto()));
    }

    @Test
    @Order(2)
    @DisplayName("Find Repositorio by ID")
    void findByIdTest() throws SQLException {
        RepositorioRepository repository = new RepositorioRepository();
        Date creationDate = Date.valueOf("2021-10-02");

        assumeTrue(repository.getById("fbba0a2b-11dc-46c2-949d-96457277f837").isPresent());

            Repositorio repositorioId = repository.getById("fbba0a2b-11dc-46c2-949d-96457277f837").get();
            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertNotNull(repositorioId),
                    () -> Assertions.assertEquals(
                            "fbba0a2b-11dc-46c2-949d-96457277f837", repositorioId.getUuid_repositorio()),
                    () -> Assertions.assertEquals("Proyecto videojuego", repositorioId.getNombre()),
                    () -> Assertions.assertEquals(creationDate, repositorioId.getFecha_creacion()),
                    () -> Assertions.assertEquals("97a53b2b-dd63-4fc4-9126-4ea3947b940a",
                            repositorioId.getProyecto()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Repositorio")
    final void saveTest() throws SQLException {
        Date creationDate = Date.valueOf("2021-10-02");
        Repositorio repository = new Repositorio("fbba0a2b-11dc-46c2-949d-96457277f831",
                "Proyecto software", creationDate,
                "97a53b2b-dd63-4fc4-9126-4ea3947b9401");
        RepositorioRepository repositorio = new RepositorioRepository();


        Optional<Repositorio> opt = repositorio.insert(repository);
        assumeTrue(opt.isPresent());

            Repositorio insert = opt.get();
            Assertions.assertAll("comprobaciones test save",
                    () -> Assertions.assertNotNull(insert),
                    () -> Assertions.assertEquals(insert, repository));
    }

    @Test
    @Order(4)
    @DisplayName("Update Repositorio")
    final void updateTest() throws SQLException {
        Date creationDate = Date.valueOf("2021-10-02");
        Repositorio repository = new Repositorio("fbba0a2b-11dc-46c2-949d-96457277f831",
                "Proyecto software", creationDate,
                "97a53b2b-dd63-4fc4-9126-4ea3947b9401");
        RepositorioRepository repositorio = new RepositorioRepository();

        assumeTrue(repositorio.update(repository).isPresent());

            Repositorio update = repositorio.update(repository).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, repository));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Repositorio")
    final void deleteTest() throws SQLException {
        RepositorioRepository repository = new RepositorioRepository();

        String id = "fbba0a2b-11dc-46c2-949d-96457277f837";
        Optional<Repositorio> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_repositorio());
    }
}
