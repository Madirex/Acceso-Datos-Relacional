import com.angcar.database.DataBaseController;
import com.angcar.model.Issue;
import com.angcar.repository.IssueRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("JUnit 5 Test CRUD Issue")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IssueCRUDTest {

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
    @DisplayName("Get All Issue")
    void getAllTest() throws SQLException {
        IssueRepository issue = new IssueRepository();
        Optional<List<Issue>> issues = issue.findAll();
        Date fecha = Date.valueOf("2021-10-01");

        assumeTrue(issues.isPresent());

            Issue issue1 = issues.get().get(0);
            Assertions.assertAll("comprobaciones test getAll",
                    () -> Assertions.assertNotNull(issues),
                    () -> Assertions.assertEquals(
                            "05601a0f-e9d0-4119-859b-235cf28b33a8", issue1.getUuid_issue()),
                    () -> Assertions.assertEquals("Estudio", issue1.getTitulo()),
                    () -> Assertions.assertEquals("Se hará un estudio profundo del proyecto",
                            issue1.getTexto()),
                    () -> Assertions.assertEquals(fecha, issue1.getFecha()),
                    () -> Assertions.assertEquals("fdebd291-46b9-4fc5-a4aa-afdfcd434933",
                            issue1.getProyecto()),
                    () -> Assertions.assertEquals("97a53b2b-dd63-4fc4-9126-4ea3947b940a",
                            issue1.getRepositorio_asignado()),
                    () -> Assertions.assertFalse(issue1.isEsAcabado()));
    }

    @Test
    @Order(2)
    @DisplayName("Find Issue by ID")
    void findByIdTest() throws SQLException {
        IssueRepository issue = new IssueRepository();
        Date fecha = Date.valueOf("2021-10-01");

        assumeTrue(issue.getById("05601a0f-e9d0-4119-859b-235cf28b33a8").isPresent());

            Issue issueId = issue.getById("05601a0f-e9d0-4119-859b-235cf28b33a8").get();
            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertNotNull(issueId),
                    () -> Assertions.assertEquals("05601a0f-e9d0-4119-859b-235cf28b33a8",
                            issueId.getUuid_issue()),
                    () -> Assertions.assertEquals("Estudio", issueId.getTitulo()),
                    () -> Assertions.assertEquals("Se hará un estudio profundo del proyecto",
                            issueId.getTexto()),
                    () -> Assertions.assertEquals(fecha, issueId.getFecha()),
                    () -> Assertions.assertEquals("fdebd291-46b9-4fc5-a4aa-afdfcd434933",
                            issueId.getProyecto()),
                    () -> Assertions.assertEquals("97a53b2b-dd63-4fc4-9126-4ea3947b940a",
                            issueId.getRepositorio_asignado()),
                    () -> Assertions.assertFalse(issueId.isEsAcabado()));
    }

    @Test
    @Order(3)
    @DisplayName("Save Issue")
    final void saveTest() throws SQLException {
        Date fecha = Date.valueOf("2021-10-01");
        Issue issue = new Issue("05601a0f-e9d0-4119-859b-235cf28b33a1",
                "Análisis", "Se hará un análisis profundo del proyecto",
                fecha, "fa55e2e3-6195-434a-9fb2-1cd8f3bd2a42", "97a53b2b-dd63-4fc4-9126-4ea3947b9401"
                , false);
        IssueRepository issueRepository = new IssueRepository();

        Optional<Issue> opt = issueRepository.insert(issue);
        assumeTrue(opt.isPresent());

            Issue insert = opt.get();
            Assertions.assertAll("comprobaciones test save",
                    () -> Assertions.assertNotNull(insert),
                    () -> Assertions.assertEquals(insert, issue));
    }

    @Test
    @Order(4)
    @DisplayName("Update Issue")
    final void updateTest() throws SQLException {
        Date fecha = Date.valueOf("2021-10-01");
        Issue issue = new Issue("05601a0f-e9d0-4119-859b-235cf28b33a1",
                "Análisis", "Se hará un análisis profundo del proyecto",
                fecha, "fa55e2e3-6195-434a-9fb2-1cd8f3bd2a42",
                "97a53b2b-dd63-4fc4-9126-4ea3947b9401", false);
        IssueRepository issueRepository = new IssueRepository();

        assumeTrue(issueRepository.update(issue).isPresent());

            Issue update = issueRepository.update(issue).get();
            Assertions.assertAll("comprobaciones test update",
                    () -> Assertions.assertNotNull(update),
                    () -> Assertions.assertEquals(update, issue));
    }

    @Test
    @Order(5)
    @DisplayName("Delete Issue")
    final void deleteTest() throws SQLException {
        IssueRepository repository = new IssueRepository();

        String id = "05601a0f-e9d0-4119-859b-235cf28b33a8";
        Optional<Issue> delete = repository.delete(id);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(id,delete.get().getUuid_issue());
    }
}
