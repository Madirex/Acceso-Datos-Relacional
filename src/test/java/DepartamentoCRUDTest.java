import com.angcar.database.DataBaseController;
import com.angcar.model.Departamento;
import com.angcar.repository.DepartamentoRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Departamento")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartamentoCRUDTest {

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
    @DisplayName("Get All Departamentos")
    void getAllTest() throws SQLException {
        DepartamentoRepository departamento = new DepartamentoRepository();
        Optional<List<Departamento>> departamentos = departamento.findAll();

        assumeTrue(departamentos.isPresent());

            Departamento departamento1 = departamentos.get().get(1);
            Assertions.assertAll("comprobaciones test getAllTest",
                    () -> Assertions.assertNotNull(departamentos),
                    () -> Assertions.assertEquals(
                            "86e0f53e-04d5-49fb-8ffc-34c6e2c7e455", departamento1.getUuid_departamento()),
                    () -> Assertions.assertEquals("Desarrollo de sistema", departamento1.getNombre()),
                    () -> Assertions.assertEquals("1c311b93-b9f7-4c91-94bc-6938d3bc3499",
                            departamento1.getJefe_departamento()),
                    () -> Assertions.assertEquals(50000.00, departamento1.getPresupuesto()),
                    () -> Assertions.assertEquals(90000.00, departamento1.getPresupuesto_anual()));
    }

    @Test
    @Order(2)
    @DisplayName("Find Departamento by ID")
    void findByIdTest() throws SQLException {
        DepartamentoRepository departamento = new DepartamentoRepository();

        assumeTrue(departamento.getById("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455").isPresent());

            Departamento departamentoId = departamento.getById("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455").get();
            Assertions.assertAll("comprobaciones test findById",
                    () -> Assertions.assertNotNull(departamentoId),
                    () -> Assertions.assertEquals("86e0f53e-04d5-49fb-8ffc-34c6e2c7e455",
                            departamentoId.getUuid_departamento()),
                    () -> Assertions.assertEquals("Desarrollo de sistema", departamentoId.getNombre()),
                    () -> Assertions.assertEquals("1c311b93-b9f7-4c91-94bc-6938d3bc3499",
                            departamentoId.getJefe_departamento()),
                    () -> Assertions.assertEquals(50000.00, departamentoId.getPresupuesto()),
                    () -> Assertions.assertEquals(90000.00, departamentoId.getPresupuesto_anual()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Departamento")
    final void saveTest() throws SQLException {
        Departamento department = new Departamento("86e0f53e-04d5-49fb-8ffc-34c6e2c7e512",
                "Desarrollo software", "1c311b93-b9f7-4c91-94bc-6938d3bc3124",
                1000.00, 1000.00);
        DepartamentoRepository departamento = new DepartamentoRepository();

        Optional<Departamento> opt = departamento.insert(department);
        assumeTrue(opt.isPresent());

            Departamento insert = opt.get();
            Assertions.assertAll("comprobaciones test save",
                    () -> Assertions.assertNotNull(insert),
                    () -> Assertions.assertEquals(insert, department));
    }

    @Test
    @Order(4)
    @DisplayName("Update Departamento")
    final void updateTest() throws SQLException {
        Departamento department = new Departamento("86e0f53e-04d5-49fb-8ffc-34c6e2c7e512",
                "Desarrollo app", "1c311b93-b9f7-4c91-94bc-6938d3bc3543",
                2000.00, 2000.00);

        DepartamentoRepository departamento = new DepartamentoRepository();

        assumeTrue(departamento.update(department).isPresent());

        Departamento update = departamento.update(department).get();
        Assertions.assertAll("comprobaciones test update",
                () -> Assertions.assertNotNull(update),
                () -> Assertions.assertEquals(update, department));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Departamento")
    final void deleteTest() throws SQLException {
        DepartamentoRepository repository = new DepartamentoRepository();

        String id = "86e0f53e-04d5-49fb-8ffc-34c6e2c7e455";
        Optional<Departamento> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_departamento());
    }
}
