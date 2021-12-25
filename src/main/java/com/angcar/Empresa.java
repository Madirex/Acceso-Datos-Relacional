package com.angcar;

import com.angcar.controller.*;
import com.angcar.database.DataBaseController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import com.angcar.dto.*;
import com.angcar.utils.UUID5;
import com.github.underscore.lodash.U;


public class Empresa {

    private static Empresa empresaInstance;

    private Empresa() {

    }

    public static Empresa getInstance() {
        if (empresaInstance == null) {
            empresaInstance = new Empresa();
        }
        return empresaInstance;
    }

    public void initData() {
        System.out.println("Iniciamos los datos");
        DataBaseController controller = DataBaseController.getInstance();
        String sqlFile = System.getProperty("user.dir") + File.separator + "sql" + File.separator + "empresa.sql";
        System.out.println(sqlFile);
        try {
            controller.open();
            controller.initData(sqlFile);
            controller.close();
        } catch (SQLException e) {
            System.err.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer el fichero de datos iniciales: " + e.getMessage());
            System.exit(1);
        }
    }

    public void checkServer() {
        System.out.println("Comprobamos la conexión al Servidor BD");
        DataBaseController controller = DataBaseController.getInstance();
        try {
            controller.open();
            Optional<ResultSet> rs = controller.select("SELECT 'Conexión con base de datos'");
            if (rs.isPresent()) {
                rs.get().first();
                controller.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.exit(1);
        }
    }

    public String searchData(){
        Optional<String> result = Optional.empty();

        do {
            //Selección de exporte
            int nSelected = -1;
            Scanner sc;

            do {
                sc = new Scanner(System.in);
                System.out.println("\n▶ ¿Qué datos quieres exportar?\n" +
                        "1 -> Operaciones CRUD\n2 -> Otras operaciones\n");

                try {
                    nSelected = sc.nextInt();

                    //Evitar que se introduzca un número menor que 1 o mayor que 2
                    if (nSelected < 1 || nSelected > 2) {
                        nSelected = -1;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.err.println("Error: no has introducido un número válido.");
                    nSelected = -1;
                }
            } while (nSelected == -1);

            //Operaciones CRUD
            if (nSelected == 1) {
                result = searchCRUDClass();
            }
            //Otras operaciones
            else if (nSelected == 2) {
                result = searchOtherOpt();
            }
        }while(result.isEmpty());

        return result.get();
    }

    private Optional<String> searchCRUDClass() {
        Optional<String> result;

        do {
            //Selección de exporte
            result = Optional.empty();
            int nSelected = -1;
            Scanner sc;

            do {
                sc = new Scanner(System.in);
                System.out.println("\n▶ ¿Sobre qué deseas realizar la acción?\n" +
                        "0 -> Volver atrás\n1 -> Commit\n2 -> Departamento\n3 -> Ficha\n4 -> Issue\n5 -> Programador\n" +
                        "6 -> Proyecto\n7 -> Repositorio\n8 -> Tarea\n9 -> Histórico de jefes\n");

                try {
                    nSelected = sc.nextInt();

                    //Evitar que se introduzca un número menor que 1 o mayor que 9
                    if (nSelected < 0 || nSelected > 9) {
                        nSelected = -1;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.err.println("Error: no has introducido un número válido.");
                    nSelected = -1;
                }
            } while (nSelected == -1);

            BaseController controller;
                switch(nSelected){
                    case 1:
                        //Commit
                        controller = CommitController.getInstance();
                        break;
                    case 2:
                        //Departamento
                        controller = DepartamentoController.getInstance();
                        break;
                    case 3:
                        //Ficha
                        controller = FichaController.getInstance();
                        break;
                    case 4:
                        //Issue
                        controller = IssueController.getInstance();
                        break;
                    case 5:
                        //Programador
                        controller = ProgramadorController.getInstance();
                        break;
                    case 6:
                        //Proyecto
                        controller = ProyectoController.getInstance();
                        break;
                    case 7:
                        //Repositorio
                        controller = RepositorioController.getInstance();
                        break;
                    case 8:
                        //Tarea
                        controller = TareaController.getInstance();
                        break;
                    case 9:
                        //Histórico de jefes
                        controller = HistoricoJefesController.getInstance();
                        break;
                    default:
                        //Volver atrás
                        return Optional.empty();
                }

            //Seleccionar ahora el tipo de CRUD
            result = selectCRUDType(controller);

        }while(result.isEmpty());

        return result;
    }

    private Optional<String> searchOtherOpt() {
        Optional<String> result;

        do {
            //Selección de exporte
            result = Optional.empty();
            int nSelected = -1;
            Scanner sc;

            do {
                sc = new Scanner(System.in);
                System.out.println("\n▶ ¿Sobre qué deseas obtener información?\n" +
                        "0 -> Volver atrás\n" +
                        "1 -> Obtener de un departamento, los proyectos y trabajadores asociados.\n" +
                        "2 -> Listado de issues abiertas por proyecto que no se han consolidado en commits.\n" +
                        "3 -> Programadores de un proyecto ordenados por número de commits.\n" +
                        "4 -> Programadores con su productividad completa: Proyectos en los que participa, " +
                        "commits e issues asignadas.\n" +
                        "5 -> Tres proyectos más caros en base a su presupuesto asignado y " +
                        "el salario de cada trabajador que participa.\n");

                try {
                    nSelected = sc.nextInt();

                    //Evitar que se introduzca un número menor que 1 o mayor que 5
                    if (nSelected < 0 || nSelected > 5) {
                        nSelected = -1;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.err.println("Error: no has introducido un número válido.");
                    nSelected = -1;
                }
            } while (nSelected == -1);

            Optional<String> id;

            switch(nSelected){
                case 1:
                    //Obtener de un departamento, los proyectos y trabajadores asociados.

                    id = Optional.empty();

                    do{
                        try{
                            System.out.println("Introduce la ID del proyecto:");
                            sc = new Scanner(System.in);
                            id = Optional.of(sc.next());

                        } catch (java.util.InputMismatchException e) {
                            System.err.println("Error: no has introducido una ID válida.");
                            id = Optional.empty();
                        }
                    }while(id.isEmpty());

                    DepartamentoController departamentoController = DepartamentoController.getInstance();
                    Optional<String> proyectos = departamentoController.getAllProyectosAsociados(id.get());
                    StringBuilder sb = new StringBuilder();
                    if (proyectos.isPresent()) {
                        sb.append("{\"proyecto\":");
                        String str = proyectos.get();
                        sb.append(str);
                        sb.append(",");
                    }

                    Optional<String> trabajadores = departamentoController.getAllTrabajadoresAsociados(id.get());
                    if (trabajadores.isPresent()) {
                        sb.append("\"trabajador\":");
                        String str = trabajadores.get();
                        sb.append(str);
                        sb.append("}");
                    }
                    result = Optional.of(sb.toString());
                    break;
                case 2:
                    //Listado de issues abiertas por proyecto que no se hayan consolidado en commits.
                    result = IssueController.getInstance().getIssuesOpenAndNotInCommit();
                    break;
                case 3:
                    //Programadores de un proyecto ordenados por número de commits.
                    id = Optional.empty();
                    do{
                        try{
                            System.out.println("Introduce la ID del proyecto:");
                        sc = new Scanner(System.in);
                        id = Optional.of(sc.next());

                        } catch (java.util.InputMismatchException e) {
                            System.err.println("Error: no has introducido una ID válida.");
                            id = Optional.empty();
                        }
                    }while(id.isEmpty());

                    result = ProyectoController.getInstance().getAllProgramadorCommitOrder(id.get());
                    break;
                case 4:
                    //Programadores con su productividad completa: Proyectos en los que participa,
                    // commits e issues asignadas.
                    result = ProgramadorController.getInstance().getAllProgramadorProductividad();
                    break;
                case 5:
                    //Tres proyectos más caros en base a su presupuesto asignado y el salario
                    // de cada trabajador que participa.
                    result = ProyectoController.getInstance().getThreeExpensiveProjects();
                    break;
                default:
                    //Volver atrás
                    return Optional.empty();
            }

        }while(result.isEmpty());

        return result;
    }

    private Optional<String> selectCRUDType(BaseController controller) {
            //Selección de exporte
            Optional<String> result = Optional.empty();
            Scanner sc;
            int nSelected;

            do {
                sc = new Scanner(System.in);
                System.out.println("\n▶ ¿Qué tipo de acción deseas realizar?\n" +
                        "0 -> Volver atrás\n1 -> Obtener todos\n2 -> Obtener por ID\n3 -> Insertar" +
                        "\n4 -> Actualizar\n5 -> Eliminar\n");

                try {
                    nSelected = sc.nextInt();

                    //Evitar que se introduzca un número menor que 1 o mayor que 5
                    if (nSelected < 0 || nSelected > 5) {
                        nSelected = -1;
                    }

                    switch(nSelected){
                        case 0:
                            //Volver atrás
                            result = Optional.empty();
                            break;
                        case 1:
                            //Obtener todos
                            result = controller.getAll();
                            break;
                        case 2:
                            //Obtener por ID
                            System.out.println("Introduce la ID para obtener la entidad:");
                            sc = new Scanner(System.in);

                            try{
                                String searchID = sc.next();
                                result = controller.getByID(searchID);

                                if (result.isEmpty()){
                                    System.err.println("No se ha encontrado la entidad.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.err.println("Error: no has introducido una ID válida.");
                                nSelected = -1;
                            }

                            break;
                        case 3:
                            //Insertar
                            Optional<BaseDTO> obj = createAndGetEntity(controller, false);
                            if (obj.isPresent()){
                                result = controller.insert(obj.get());
                            }else{
                                System.err.println("Error: Datos introducidos no válidos.");
                                nSelected = -1;
                            }
                            break;
                        case 4:
                            //Actualizar
                            Optional<BaseDTO> obj2 = createAndGetEntity(controller, true);
                            if (obj2.isPresent()){
                                result = controller.update(obj2.get());
                            }else{
                                System.err.println("Error: Datos introducidos no válidos.");
                                nSelected = -1;
                            }
                            break;
                        case 5:
                            //Eliminar
                            System.out.println("Introduce la ID de la entidad a eliminar:");
                            sc = new Scanner(System.in);

                            try{
                                String searchID = sc.next();
                                result = controller.delete(searchID);

                                if (result.isEmpty()){
                                    System.err.println("No se ha encontrado la entidad.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.err.println("Error: no has introducido una ID válida.");
                                nSelected = -1;
                            }
                            break;
                    }

                } catch (java.util.InputMismatchException e) {
                    System.err.println("Error: no has introducido un número válido.");
                    nSelected = -1;
                }
            } while (nSelected == -1);

        return result;
    }

    public Optional<BaseDTO> createAndGetEntity(BaseController controller, boolean search){
        Optional<BaseDTO> dto = Optional.empty();

        String id = UUID5.create();

        if (search){
            id = scannerString("Introduce la ID sobre la entidad a modificar");
        }

        if (controller instanceof CommitController){
            String title = scannerString("Introduce el título");
            String texto = scannerString("Introduce el texto");
            Date fecha = scannerDate("Introduce la fecha");
            String repositorio = scannerString("Introduce la UUID del repositorio");
            String proyecto = scannerString("Introduce la UUID del proyecto");
            String autorCommit = scannerString("Introduce la UUID del autor del commit");
            String issueProcedente = scannerString("Introduce la UUID del issue procedente");

            dto = Optional.of(CommitDTO.builder()
                    .uuid_commit(id)
                    .titulo(title)
                    .texto(texto)
                    .fecha(fecha)
                    .repositorio(repositorio)
                    .proyecto(proyecto)
                    .autor_commit(autorCommit)
                    .issue_procedente(issueProcedente)
                    .build());
        }else if (controller instanceof DepartamentoController){
            String nombre = scannerString("Introduce el nombre");
            String jefeDepartamento = scannerString("Introduce la UUID del jefe de departamento");
            double presupuesto = scannerDouble("Introduce el presupuesto");
            double presupuestoAnual = scannerDouble("Introduce el presupuesto anual");

            dto = Optional.of(DepartamentoDTO.builder()
                    .uuid_departamento(id)
                    .nombre(nombre)
                    .jefe_departamento(jefeDepartamento)
                    .presupuesto(presupuesto)
                    .presupuesto_anual(presupuestoAnual)
                    .build());
        }else if (controller instanceof FichaController){
            String programador = scannerString("Introduce la UUID del programador");
            String proyecto = scannerString("Introduce la UUID del proyecto");

            dto = Optional.of(FichaDTO.builder()
                    .uuid_ficha(id)
                    .uuid_programador(programador)
                    .uuid_proyecto(proyecto)
                    .build());
        }else if (controller instanceof HistoricoJefesController){
            String programador = scannerString("Introduce la UUID del programador");
            String departamento = scannerString("Introduce la UUID del departamento");
            Date fechaAlta = scannerDate("Introduce la fecha de alta");
            Date fechaBaja = scannerDate("Introduce la fecha de baja");

            dto = Optional.of(HistoricoJefesDTO.builder()
                    .uuid_historico(id)
                    .uuid_programador(programador)
                    .uuid_departamento(departamento)
                    .fecha_alta(fechaAlta)
                    .fecha_baja(fechaBaja)
                    .build());
        }else if (controller instanceof IssueController){

            String title = scannerString("Introduce el título");
            String texto = scannerString("Introduce el texto");
            Date fecha = scannerDate("Introduce la fecha");
            String proyecto = scannerString("Introduce la UUID del proyecto");
            String repositorioAsignado = scannerString("Introduce la UUID del repositorio asignado");
            boolean acabado = scannerBoolean("¿Issue cerrada?");

            dto = Optional.of(IssueDTO.builder()
                    .uuid_issue(id)
                    .titulo(title)
                    .texto(texto)
                    .fecha(fecha)
                    .proyecto(proyecto)
                    .repositorio_asignado(repositorioAsignado)
                    .es_acabado(acabado)
                    .build());
        }else if (controller instanceof ProgramadorController){
            String nombre = scannerString("Introduce el nombre");
            Date fechaAlta = scannerDate("Introduce la fecha de alta");;
            String departamento = scannerString("Introduce la UUID del departamento");
            String techDomain = scannerString("Introduce las tecnologías dominadas");
            double salario = scannerDouble("Introduce el salario");
            boolean jefeDepartamento = scannerBoolean("¿Es jefe de departamento?");
            boolean jefeProyecto = scannerBoolean("¿Es jefe de proyecto?");
            boolean jefeActivo = scannerBoolean("¿Es jefe activo?");
            String password = scannerString("Introduce la contraseña");

            dto = Optional.of(ProgramadorDTO.builder()
                    .uuid_programador(id)
                    .nombre(nombre)
                    .fecha_alta(fechaAlta)
                    .departamento(departamento)
                    .tecnologias_dominadas(techDomain)
                    .salario(salario)
                    .es_jefe_departamento(jefeDepartamento)
                    .es_jefe_proyecto(jefeProyecto)
                    .es_jefe_activo(jefeActivo)
                    .password(password)
                    .build());
        }else if (controller instanceof ProyectoController){
            String jefeProyecto = scannerString("Introduce la UUID del jefe de proyecto");
            String nombre = scannerString("Introduce el nombre");
            double presupuesto = scannerDouble("Introduce el presupuesto");
            Date fechaInicio = scannerDate("Introduce la fecha de inicio");
            Date fechaFin = scannerDate("Introduce la fecha de fin");
            String technologies = scannerString("Introduce las tecnologías usadas");
            String repositorio = scannerString("Introduce la UUID del repositorio");
            boolean acabado = scannerBoolean("¿Poner proyecto como acabado?");
            String departamento = scannerString("Introduce la UUID del departamento");

            dto = Optional.of(ProyectoDTO.builder()
                    .uuid_proyecto(id)
                    .jefe_proyecto(jefeProyecto)
                    .nombre(nombre)
                    .presupuesto(presupuesto)
                    .fecha_inicio(fechaInicio)
                    .fecha_fin(fechaFin)
                    .tecnologias_usadas(technologies)
                    .repositorio(repositorio)
                    .es_acabado(acabado)
                    .uuid_departamento(departamento)
                    .build());
        }else if (controller instanceof RepositorioController){
            String nombre = scannerString("Introduce el nombre");
            Date fechaCreation = scannerDate("Introduce la fecha de creación");
            String proyecto = scannerString("Introduce la UUID del proyecto");

            dto = Optional.of(RepositorioDTO.builder()
                    .uuid_repositorio(id)
                    .nombre(nombre)
                    .fecha_creacion(fechaCreation)
                    .proyecto(proyecto)
                    .build());
        }else if (controller instanceof TareaController){
            String uuidProgramador = scannerString("Introduce la UUID del programador");
            String uuidIssue = scannerString("Introduce la UUID del issue");

            dto = Optional.of(TareaDTO.builder()
                    .uuid_tarea(id)
                    .uuid_programador(uuidProgramador)
                    .uuid_issue(uuidIssue)
                    .build());
        }

        return dto;
    }

    public Date scannerDate(String msg){
        int year = 1;
        int month = 1;
        int day = 1;
        String fechaStr;
        boolean exitYear = false;
        boolean exitMonth = false;
        boolean exitDay = false;

        System.out.println(msg + ":");

        do{
            //AÑO
            System.out.println("Introduce el año:");
            Scanner sc = new Scanner(System.in);
            try{
                year = sc.nextInt();

                if (year > 0){
                    exitYear = true;
                }

            } catch (java.util.InputMismatchException e) {
                System.err.println("Error: no has introducido un año válido.");
                exitYear = false;
            }}while(!exitYear);

        do{
            //MES
            System.out.println("Introduce el mes:");
            Scanner sc = new Scanner(System.in);
            try{
                month = sc.nextInt();

                if (month > 0 && month < 13){
                    exitMonth = true;
                }

            } catch (java.util.InputMismatchException e) {
                System.err.println("Error: no has introducido un mes válido.");
                exitMonth = false;
            }}while(!exitMonth);

        do{
            //DÍA
            System.out.println("Introduce el día:");
            Scanner sc = new Scanner(System.in);
            try{
                day = sc.nextInt();

                if (day > 0 && day < 32){
                    exitDay = true;
                }

            } catch (java.util.InputMismatchException e) {
                System.err.println("Error: no has introducido un día válido.");
                exitDay = false;
            }}while(!exitDay);

        fechaStr = year + "-" + String.format("%02d" , month) + "-" + String.format("%02d" , day);
        System.out.println(fechaStr);
        Date date = Date.valueOf(fechaStr);

        return date;
    }

    public double scannerDouble(String msg){
        double result = 0.0;
        boolean exit = false;

        do{
            Scanner sc = new Scanner(System.in);
            System.out.println(msg + ":");

            try{
                result = sc.nextDouble();
                exit = true;
            } catch (java.util.InputMismatchException e) {
                System.err.println("Error: no has introducido un dato double válido.");
                exit = false;
            }}while(!exit);

        return result;
    }

    public String scannerString(String msg){
        String result = "";
        boolean exit = false;

        do{
        Scanner sc = new Scanner(System.in);
        System.out.println(msg + ":");

        try{
            result = sc.next();
            exit = true;
        } catch (java.util.InputMismatchException e) {
            System.err.println("Error: no has introducido un dato String válido.");
            exit = false;
        }}while(!exit);

        return result;
    }

    public boolean scannerBoolean(String msg){
        boolean result = false;
        int resultn = -1;
        boolean exit = false;

        do{
            Scanner sc = new Scanner(System.in);
            System.out.println(msg + ":\n1 -> " + "Falso" + "\n2 -> " + "Verdadero" + "\n");

            try{
                resultn = sc.nextInt();
                if (resultn > 0 && resultn < 3){
                    if (resultn == 2){
                        result = true;
                    }
                    exit = true;
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Error: no has introducido un dato válido (1 o 2).");
                exit = false;
            }}while(!exit);

        return result;
    }

    public void exportData(boolean toJSON, boolean toXML, String path, String export) {
        if (toJSON) {
            saveFile(path, export, "json");
        }

        if (toXML){
            saveFile(path, export, "xml");
        }
    }

    private void saveFile(String path, String export, String fileType) {
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }

        Scanner sc;
            boolean savedToFile = false;
            boolean archivoExistente = false;

            do {
                Optional<String> nombreArchivo = Optional.empty();
                do {
                    System.out.println("Introduce el nombre del archivo a exportar como " + fileType + ":");
                    sc = new Scanner(System.in);

                    try{
                        nombreArchivo = Optional.of(sc.next());
                    }catch (InputMismatchException e) {
                        System.err.println("Error: no has introducido un nombre válido.");
                        nombreArchivo = Optional.empty();
                    }

                }while(nombreArchivo.isEmpty());

                if (Path.of(dir.getPath(), nombreArchivo.get() + "." + fileType).toFile().exists()) {
                    archivoExistente = true;
                }

                if (archivoExistente) {
                    int selection = -1;
                    do {
                        System.out.println("El archivo con el nombre " + nombreArchivo.get() + " para el " +
                                "fichero " + fileType + " ya existe.\n" +
                                "1 -> Reemplazar archivo\n2 -> Guardar archivo con otro nombre.");
                        sc = new Scanner(System.in);

                        try {
                            selection = sc.nextInt();
                            if (selection < 0 || selection > 2) {
                                selection = -1;
                            } else if (selection == 1) {
                                savedToFile = saveAs(fileType, dir, export, nombreArchivo.get());
                            } else if (selection == 2) {
                                savedToFile = false;
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Error: no has introducido un número válido.");
                        }
                    } while (selection == -1);

                }else{
                    //Si no existe, crear
                    savedToFile = saveAs(fileType, dir, export, nombreArchivo.get());
                }
            }while(!savedToFile);
    }

    public boolean saveAs(String fileType, File dir, String export, String fileName){
        boolean saved = false;

        try {
            //Convertir a XML
            String finalString = export;

            if (fileType == "xml"){
                finalString = U.jsonToXml(export);
            }

            Files.writeString(Path.of(dir.getPath(), fileName + "." + fileType), finalString);
            saved = true;
        } catch (IOException e) {
            System.out.println("Error al escribir archivo en el directorio: " + e.getMessage());
        }
        if (saved){
            System.out.println("Archivo " + fileType + " creado correctamente.");
        }

        return saved;
    }

    public void exportUserSelect(String path, String result) {
        //Selección de exporte
        int nSelected = -1;
        boolean toJSON = false;
        boolean toXML = false;
        Scanner sc;

        do{
            sc = new Scanner(System.in);
            System.out.println("\n▶ ¿En qué formato quieres exportar los resultados de las consultas?\n" +
                    "1 -> JSON\t2 -> XML\t3 -> JSON y XML\n");
            toJSON = false;
            toXML = false;

            try{
                nSelected = sc.nextInt();

                //Evitar que se introduzca un número menor que 1 o mayor que 3
                if (nSelected < 1 || nSelected > 3){
                    nSelected = -1;
                }
            }
            catch (java.util.InputMismatchException e)
            {
                System.err.println("Error: no has introducido un número válido.");
                nSelected = -1;
            }
        }while(nSelected == -1);


        if (nSelected == 1){
            toJSON = true;
            System.out.println("\nSelección de exportación: JSON\n");
        }else if (nSelected == 2){
            toXML = true;
            System.out.println("\nSelección de exportación: XML\n");
        } else if (nSelected == 3){
            toJSON = true;
            toXML = true;
            System.out.println("\nSelección de exportación: JSON y XML\n");
        }

        this.exportData(toJSON, toXML, path, result);
    }
}
