import com.angcar.database.DataBaseController;
import com.angcar.model.Programador;
import com.angcar.repository.ProgramadorRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Programador")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProgramadorCRUDTest {

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
    @DisplayName("Get All Programador")
    void getAllTest() throws SQLException {
        ProgramadorRepository programador = new ProgramadorRepository();
        Optional<List<Programador>> programadores = programador.findAll();
        Date fechaAlta = Date.valueOf("2021-10-11");

        assumeTrue(programadores.isPresent());

            Programador programador1 = programadores.get().get(1);
            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(programadores),
                    () -> Assertions.assertEquals(
                            "1c311b93-b9f7-4c91-94bc-6938d3bc3499", programador1.getUuid_programador()),
                    () -> Assertions.assertEquals("Adrian", programador1.getNombre()),
                    () -> Assertions.assertEquals(fechaAlta, programador1.getFecha_alta()),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455", programador1.getDepartamento()),
                    () -> Assertions.assertEquals("java, python", programador1.getTecnologias_dominadas()),
                    () -> Assertions.assertEquals(7000.00, programador1.getSalario()),
                    () -> Assertions.assertTrue(programador1.isEs_jefe_departamento()),
                    () -> Assertions.assertFalse(programador1.isEs_jefe_proyecto()),
                    () -> Assertions.assertTrue(programador1.isEs_jefe_activo()),
                    () -> Assertions.assertEquals("40bd001563085fc35165329ea", programador1.getPassword()));

    }

    @Test
    @Order(2)
    @DisplayName("Find Programador by ID")
    void findByIdTest() throws SQLException {
        ProgramadorRepository programador = new ProgramadorRepository();
        Date fechaAlta = Date.valueOf("2021-10-11");

        assumeTrue(programador.getById("1c311b93-b9f7-4c91-94bc-6938d3bc3499").isPresent());

            Programador programadorId = programador.getById("1c311b93-b9f7-4c91-94bc-6938d3bc3499").get();
            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertNotNull(programadorId),
                    () -> Assertions.assertEquals(
                            "1c311b93-b9f7-4c91-94bc-6938d3bc3499", programadorId.getUuid_programador()),
                    () -> Assertions.assertEquals("Adrian", programadorId.getNombre()),
                    () -> Assertions.assertEquals(fechaAlta, programadorId.getFecha_alta()),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455", programadorId.getDepartamento()),
                    () -> Assertions.assertEquals("java, python", programadorId.getTecnologias_dominadas()),
                    () -> Assertions.assertEquals(7000.00, programadorId.getSalario()),
                    () -> Assertions.assertTrue(programadorId.isEs_jefe_departamento()),
                    () -> Assertions.assertFalse(programadorId.isEs_jefe_proyecto()),
                    () -> Assertions.assertTrue(programadorId.isEs_jefe_activo()),
                    () -> Assertions.assertEquals("40bd001563085fc35165329ea", programadorId.getPassword()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Programador")
    final void saveTest() throws SQLException {
        Date fechaAlta = Date.valueOf("2021-10-11");
        Programador programmer = new Programador("1c311b93-b9f7-4c91-94bc-6938d3bc3491",
                "Emilio", fechaAlta,
                "","java"
                , 8000.00, false, false, false,
                "40bd001563085fc35165329ea");
        ProgramadorRepository programadorRepository = new ProgramadorRepository();

        Optional<Programador> opt = programadorRepository.insert(programmer);
        assumeTrue(opt.isPresent());

            Programador insert = opt.get();
            Assertions.assertAll("comprobaciones test save",
                    () -> Assertions.assertNotNull(insert),
                    () -> Assertions.assertEquals(insert, programmer));
    }

    @Test
    @Order(4)
    @DisplayName("Update Programador")
    final void updateTest() throws SQLException {
        Date fechaAlta = Date.valueOf("2021-10-11");
        Programador programmer = new Programador("1c311b93-b9f7-4c91-94bc-6938d3bc3112",
                "Cristina", fechaAlta,
                "","Kotlin"
                , 9000.00, false, false, false,
                "40bd001563085fc35165329ea");
        ProgramadorRepository programadorRepository = new ProgramadorRepository();

        assumeTrue(programadorRepository.update(programmer).isPresent());

            Programador update = programadorRepository.update(programmer).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, programmer));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Programador")
    final void deleteTest() throws SQLException {
        ProgramadorRepository repository = new ProgramadorRepository();

        String id = "1c311b93-b9f7-4c91-94bc-6938d3bc3499";
        Optional<Programador> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_programador());
    }
}
