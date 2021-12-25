import com.angcar.database.DataBaseController;
import com.angcar.model.Commit;
import com.angcar.repository.CommitRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@DisplayName("JUnit 5 Test CRUD Commit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommitCRUDTest {

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
    @DisplayName("Get all Commit")
    void getAllTest() throws SQLException {
        CommitRepository commitRepository = new CommitRepository();
        Optional<List<Commit>> commits = commitRepository.findAll();

        assumeTrue(commits.isPresent());

        Assertions.assertAll("comprobaciones test getAll",
                () -> Assertions.assertEquals(3, commits.get().size()),
                () -> Assertions.assertEquals(
                        "07b2f0cb-aedf-4f1c-8314-1b72d5727cf4", commits.get().get(0).getUuid_commit()),
                () -> Assertions.assertEquals(
                        "fbba0a2b-11dc-46c2-949d-96457277f837", commits.get().get(2).getUuid_commit()),
                () -> Assertions.assertEquals(
                        "d37eb266-278b-4f56-b18a-c94ad09c89aa", commits.get().get(1).getUuid_commit())
                );
    }

    @Test
    @Order(2)
    @DisplayName("Find Commit By ID")
    void getByIdTest() throws SQLException {
        CommitRepository commitRepository = new CommitRepository();
        Optional<Commit> commit = commitRepository.getById("fbba0a2b-11dc-46c2-949d-96457277f837");
        Date date = Date.valueOf("2021-10-02");

        assumeTrue(commit.isPresent());

            Assertions.assertAll("comprobaciones test getById",
                    () -> Assertions.assertEquals("fbba0a2b-11dc-46c2-949d-96457277f837",
                    commit.get().getUuid_commit()),
                    () -> Assertions.assertEquals("Parte realizada", commit.get().getTitulo()),
                    () -> Assertions.assertEquals("Se ha realizado una parte del proyect",
                            commit.get().getTexto()),
                    () -> Assertions.assertEquals(date, commit.get().getFecha()),
                    () -> Assertions.assertEquals("fbba0a2b-11dc-46c2-949d-96457277f837",
                            commit.get().getRepositorio()),
                    () -> Assertions.assertEquals("97a53b2b-dd63-4fc4-9126-4ea3947b940a",
                            commit.get().getProyecto()),
                    () -> Assertions.assertEquals("c0b705bc-8f83-42e6-aae9-b115f6035eff",
                            commit.get().getAutor_commit()),
                    () -> Assertions.assertEquals("82c102f4-e063-4116-9b39-1d7ac1b6ea11",
                            commit.get().getIssue_procedente())
                    );
    }

    @Test
    @Order(3)
    @DisplayName("Save Commit")
    void saveTest() throws SQLException {
        CommitRepository commitRepository = new CommitRepository();
        Date date = Date.valueOf("2021-10-02");

        Commit commit = new Commit("07b2p0cb-aedf-4f1c-8314-1b72d5727cf4","Probando","Texto",
                date, "fbba0a2b-11dc-46c2-949d-96457277f837","4e253552-73a3-4a74-bf1d-6c45db815a91",
                "1c311b93-b9f7-4c91-94bc-6938d3bc3499","05601a0f-e9d0-4119-859b-235cf28b33a8");

        Optional<Commit> insert = commitRepository.insert(commit);

        assumeTrue(insert.isPresent());
        Assertions.assertEquals(commit,insert.get());
    }

    @Test
    @Order(4)
    @DisplayName("Update Commit")
    void updateTest() throws SQLException {
        CommitRepository commitRepository = new CommitRepository();
        Date date = Date.valueOf("2021-10-02");
        Commit commit = new Commit("07b2p0cb-aedf-4f1c-8314-1b72d5727cf4","Probando","Texto",
                date, "fbba0a2b-11dc-46c2-949d-96457277f837","4e253552-73a3-4a74-bf1d-6c45db815a91",
                "1c311b93-b9f7-4c91-94bc-6938d3bc3499","05601a0f-e9d0-4119-859b-235cf28b33a8");

        Optional<Commit> update = commitRepository.update(commit);
        assumeTrue(update.isPresent());
        Assertions.assertEquals(commit,update.get());
    }

    @Test
    @Order(5)
    @DisplayName("Delete Commit")
    void deleteTest() throws SQLException {
        CommitRepository commitRepository = new CommitRepository();

        String commit = "fbba0a2b-11dc-46c2-949d-96457277f837";
        Optional<Commit> delete = commitRepository.delete(commit);

        assumeTrue(delete.isPresent());
        Assertions.assertEquals(commit,delete.get().getUuid_commit());
    }

}
