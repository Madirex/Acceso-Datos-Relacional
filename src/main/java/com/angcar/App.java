package com.angcar;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        if (Arrays.stream(args).count() == 1){
            Empresa empresa = Empresa.getInstance();
            empresa.checkServer();
            empresa.initData();
            String result = empresa.searchData();
            empresa.exportUserSelect(args[0], result);
        }else{
            System.err.println("Error: Número de argumentos no válidos" +
                    "\nUtiliza el siguiente formato:" +
                    "\njava -jar accesdata.jar <directorio>\n");
            System.exit(1);
        }
    }
}
