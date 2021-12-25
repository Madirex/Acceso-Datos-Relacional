import com.angcar.database.DataBaseController;
import com.angcar.model.Proyecto;
import com.angcar.repository.ProyectoRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Proyecto")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProyectoCRUDTest {

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
    @DisplayName("Get All Proyectos")
    void getAllTest() throws SQLException {
        ProyectoRepository proyecto = new ProyectoRepository();
        Optional<List<Proyecto>> proyectos = proyecto.findAll();
        Date dateInit = Date.valueOf("2021-02-01");
        Date dateEnd = Date.valueOf("2022-01-12");

        assumeTrue(proyectos.isPresent());

            Proyecto proyecto1 = proyectos.get().get(2);

            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(proyectos),
                    () -> Assertions.assertEquals(
                            "4e253552-73a3-4a74-bf1d-6c45db815a91", proyecto1.getUuid_proyecto()),
                    () -> Assertions.assertEquals("ab05d17f-7e4b-4d6b-872d-ea04b9111041", proyecto1.getJefe_proyecto()),
                    () -> Assertions.assertEquals("Videojuego", proyecto1.getNombre()),
                    () -> Assertions.assertEquals(50000.00, proyecto1.getPresupuesto()),
                    () -> Assertions.assertEquals(dateInit, proyecto1.getFecha_inicio()),
                    () -> Assertions.assertEquals(dateEnd, proyecto1.getFecha_fin()),
                    () -> Assertions.assertEquals("C#", proyecto1.getTecnologias_usadas()),
                    () -> Assertions.assertEquals("28377ad9-31d5-4206-9a65-f47828308a35", proyecto1.getRepositorio()),
                    () -> Assertions.assertTrue(proyecto1.isEs_acabado()),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455", proyecto1.getUuid_departamento()));
    }

    @Test
    @Order(2)
    @DisplayName("Find Proyecto by ID")
    void findByIdTest() throws SQLException {
        ProyectoRepository proyecto = new ProyectoRepository();
        Date dateInit = Date.valueOf("2021-02-01");
        Date dateEnd = Date.valueOf("2022-01-12");

        assumeTrue(proyecto.getById("4e253552-73a3-4a74-bf1d-6c45db815a91").isPresent());

            Proyecto proyectoId = proyecto.getById("4e253552-73a3-4a74-bf1d-6c45db815a91").get();
            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertNotNull(proyecto),
                    () -> Assertions.assertEquals(
                            "4e253552-73a3-4a74-bf1d-6c45db815a91", proyectoId.getUuid_proyecto()),
                    () -> Assertions.assertEquals("ab05d17f-7e4b-4d6b-872d-ea04b9111041",
                            proyectoId.getJefe_proyecto()),
                    () -> Assertions.assertEquals("Videojuego", proyectoId.getNombre()),
                    () -> Assertions.assertEquals(50000.00, proyectoId.getPresupuesto()),
                    () -> Assertions.assertEquals(dateInit, proyectoId.getFecha_inicio()),
                    () -> Assertions.assertEquals(dateEnd, proyectoId.getFecha_fin()),
                    () -> Assertions.assertEquals("C#", proyectoId.getTecnologias_usadas()),
                    () -> Assertions.assertEquals("28377ad9-31d5-4206-9a65-f47828308a35",
                            proyectoId.getRepositorio()),
                    () -> Assertions.assertTrue(proyectoId.isEs_acabado()),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455",
                            proyectoId.getUuid_departamento()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Proyecto")
    final void saveTest() throws SQLException {
        Date dateInit = Date.valueOf("2021-02-01");
        Date dateEnd = Date.valueOf("2022-01-12");
        Proyecto proyecto = new Proyecto("86e0f53e-04d5-49fb-8ffc-34c6e2c7e532",
                "ab05d17f-7e4b-4d6b-872d-ea04b9111123",
                "Inteligencia artificial", 100000.00,
                dateInit, dateEnd, "Kotlin", "28377ad9-31d5-4206-9a65-f47828308312"
                , true,"86e0f53e-04d5-49fb-8ffc-34c6e2c7e455");
        ProyectoRepository proyectoRepository = new ProyectoRepository();

        Optional<Proyecto> opt = proyectoRepository.insert(proyecto);
        assumeTrue(opt.isPresent());

            Proyecto insert = opt.get();
            Assertions.assertAll("comprobaciones test save",
                    () -> Assertions.assertNotNull(insert),
                    () -> Assertions.assertEquals(insert, proyecto));
    }


    @Test
    @Order(4)
    @DisplayName("Update Proyecto")
    final void updateTest() throws SQLException {
        Date dateInit = Date.valueOf("2021-02-05");
        Date dateEnd = Date.valueOf("2022-01-01");
        Proyecto project = new Proyecto("86e0f53e-04d5-49fb-8ffc-34c6e2c7e532",
                "ab05d17f-7e4b-4d6b-872d-ea04b9111123",
                "Inteligencia artificial", 100000.00,
                dateInit, dateEnd, "Kotlin", "28377ad9-31d5-4206-9a65-f47828308312"
                , true,"86e0f53e-04d5-49fb-8ffc-34c6e2c7e455");
        ProyectoRepository proyectoRepository = new ProyectoRepository();

        assumeTrue(proyectoRepository.update(project).isPresent());

            Proyecto update = proyectoRepository.update(project).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, project));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Proyecto")
    final void deleteTest() throws SQLException {
        ProyectoRepository repository = new ProyectoRepository();

        String id = "4e253552-73a3-4a74-bf1d-6c45db815a91";
        Optional<Proyecto> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_proyecto());
    }
}
