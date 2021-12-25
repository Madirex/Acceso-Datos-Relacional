import com.angcar.database.DataBaseController;
import com.angcar.model.HistoricoJefes;
import com.angcar.repository.HistoricoJefesRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD HistoricoJefes")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HistoricoJefesCRUDTest {

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
    @DisplayName("Get All HistoricoJefes")
    void getAllTest() throws SQLException {
        HistoricoJefesRepository historicoJefes = new HistoricoJefesRepository();
        Optional<List<HistoricoJefes>> historicoJefess = historicoJefes.findAll();

        assumeTrue(historicoJefess.isPresent());

        Date date = Date.valueOf("2021-10-13");

            HistoricoJefes historicoJefes1 = historicoJefess.get().get(1);
            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(historicoJefess),
                    () -> Assertions.assertEquals("66cb775d-badc-4348-a436-d4f9f23aff0a",
                            historicoJefes1.getUuid_historico()),
                    () -> Assertions.assertEquals("46ca0fd4-6daa-421a-b3f8-ce2874dbc715",
                            historicoJefes1.getUuid_programador()),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455",
                            historicoJefes1.getUuid_departamento()),
                    () -> Assertions.assertEquals(date,
                            historicoJefes1.getFecha_alta()),
                    () -> Assertions.assertEquals(null,
                            historicoJefes1.getFecha_baja()));
    }

    @Test
    @Order(2)
    @DisplayName("Find HistoricoJefes by ID")
    void findByIdTest() throws SQLException {
        HistoricoJefesRepository historicoJefes = new HistoricoJefesRepository();

        assumeTrue(historicoJefes.getById("66cb775d-badc-4348-a436-d4f9f23aff0a").isPresent());

        Date date = Date.valueOf("2021-10-13");

            HistoricoJefes historicoJefes1 = historicoJefes.getById("66cb775d-badc-4348-a436-d4f9f23aff0a").get();
            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertNotNull(historicoJefes1),
                    () -> Assertions.assertEquals("66cb775d-badc-4348-a436-d4f9f23aff0a",
                            historicoJefes1.getUuid_historico()),
                    () -> Assertions.assertEquals("46ca0fd4-6daa-421a-b3f8-ce2874dbc715",
                            historicoJefes1.getUuid_programador()),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455",
                            historicoJefes1.getUuid_departamento()),
                    () -> Assertions.assertEquals(date,
                            historicoJefes1.getFecha_alta()),
                    () -> Assertions.assertEquals(null,
                            historicoJefes1.getFecha_baja()));
    }

    @Test
    @Order(3)
    @DisplayName("Save HistoricoJefes")
    final void saveTest() throws SQLException {
        Date date = Date.valueOf("2021-12-12");
        Date date2 = Date.valueOf("2022-02-03");

        HistoricoJefes tar = new HistoricoJefes("4ddcd56b-6a62-4e56-b9b3-b88553330911",
                "46ca0fd4-6daa-421a-b3f8-ce2874dbc715",
                "6ca0fd4-6daa-421a-b3f8-ce2874dbc7152", date, date2);

        HistoricoJefesRepository historicoJefes = new HistoricoJefesRepository();

        Optional<HistoricoJefes> opt = historicoJefes.insert(tar);
        assumeTrue(opt.isPresent());

        HistoricoJefes insert = opt.get();
        Assertions.assertAll("comprobaciones test save",
                () -> Assertions.assertNotNull(insert),
                () -> Assertions.assertEquals(insert, tar));
    }

    @Test
    @Order(4)
    @DisplayName("Update HistoricoJefes")
    final void updateTest() throws SQLException {
        Date date = Date.valueOf("2021-12-12");
        Date date2 = Date.valueOf("2022-02-03");

        HistoricoJefes tar = new HistoricoJefes("66cb775d-badc-4348-a436-d4f9f23aff0a",
                "46ca0fd4-6daa-421a-b3f8-ce2874dbc715",
                "6ca0fd4-6daa-421a-b3f8-ce2874dbc7152", date, date2);
        HistoricoJefesRepository historicoJefes = new HistoricoJefesRepository();

        assumeTrue(historicoJefes.update(tar).isPresent());

            HistoricoJefes update = historicoJefes.update(tar).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, tar));
    }

    @Test
    @Order(5)
    @DisplayName("Delete HistoricoJefes")
    final void deleteTest() throws SQLException {
        HistoricoJefesRepository repository = new HistoricoJefesRepository();

        String id = "66cb775d-badc-4348-a436-d4f9f23aff0a";
        Optional<HistoricoJefes> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_historico());
    }
}