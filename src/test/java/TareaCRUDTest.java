import com.angcar.database.DataBaseController;
import com.angcar.model.Tarea;
import com.angcar.repository.TareaRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Tarea")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TareaCRUDTest {

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
    @DisplayName("Get All Tarea")
    void getAllTest() throws SQLException {
        TareaRepository tarea = new TareaRepository();
        Optional<List<Tarea>> tareas = tarea.findAll();

        assumeTrue(tareas.isPresent());

            Tarea tarea1 = tareas.get().get(1);
            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(tareas),
                    () -> Assertions.assertEquals("a7d89c0a-41b0-4f0f-93ab-89954e795449",
                            tarea1.getUuid_tarea()),
                    () -> Assertions.assertEquals("c0b705bc-8f83-42e6-aae9-b115f6035eff",
                            tarea1.getUuid_programador()),
                    () -> Assertions.assertEquals("0e2616b5-d804-4677-8345-8f093a943c0d",
                            tarea1.getUuid_issue()));
    }

    @Test
    @Order(2)
    @DisplayName("Find Tarea by ID")
    void findByIdTest() throws SQLException {
        TareaRepository tarea = new TareaRepository();

        assumeTrue(tarea.getById("fa55e2e3-6195-434a-9fb2-1cd8f3bd2a43").isPresent());

            Tarea tareaId = tarea.getById("fa55e2e3-6195-434a-9fb2-1cd8f3bd2a43").get();
            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertNotNull(tareaId),
                    () -> Assertions.assertEquals("fa55e2e3-6195-434a-9fb2-1cd8f3bd2a43",
                            tareaId.getUuid_tarea()),
                    () -> Assertions.assertEquals("ab05d17f-7e4b-4d6b-872d-ea04b9111041",
                            tareaId.getUuid_programador()),
                    () -> Assertions.assertEquals("05601a0f-e9d0-4119-859b-235cf28b33a8",
                            tareaId.getUuid_issue()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Tarea")
    final void saveTest() throws SQLException {
        Tarea tar = new Tarea("4ddcd5bb-6a62-4e56-b9b3-b88553330911",
                "0b3e2cac-6849-4e2a-98f2-f610b182f251", "d9a4095d-0e59-449d-929e-f37f996cc22w");
        TareaRepository tarea = new TareaRepository();

        Optional<Tarea> opt = tarea.insert(tar);
        assumeTrue(opt.isPresent());

        Tarea insert = opt.get();
        Assertions.assertAll("comprobaciones test save",
                () -> Assertions.assertNotNull(insert),
                () -> Assertions.assertEquals(insert, tar));
    }

    @Test
    @Order(4)
    @DisplayName("Update Tarea")
    final void updateTest() throws SQLException {
        Tarea tar = new Tarea("4ddcd5bb-6a62-4e56-b9b3-b88553330921",
                "ab05d17f-7e4b-4d6b-872d-ea04b9111041", "d9a4095d-0e59-449d-929e-f37f996cc156");
        TareaRepository tarea = new TareaRepository();

        assumeTrue(tarea.update(tar).isPresent());

            Tarea update = tarea.update(tar).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, tar));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Tarea")
    final void deleteTest() throws SQLException {
        TareaRepository repository = new TareaRepository();

        String id = "fa55e2e3-6195-434a-9fb2-1cd8f3bd2a43";
        Optional<Tarea> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_tarea());
    }
}