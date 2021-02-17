package ru.itis;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import ru.itis.services.DataBaseConfiguration;
import ru.itis.services.DocumentService;
import ru.itis.services.DocumentServiceImpl;

import java.sql.Connection;
import java.util.*;


public class Main {


    public static Connection connection;

    @Getter
    @com.beust.jcommander.Parameters(separators = "=")
    static class Parameters {
        @Parameter(names = "--folder", required = true)
        private static String folder;

        @Parameter(names = "--type", required = true)
        private static String type;

        @Parameter(names = "--db.use", required = true)
        private static String dbUse;

    }

    public static void main(String[] args) {

        Parameters parameters = new Parameters();
        JCommander.newBuilder()
                .addObject(parameters)
                .build()
                .parse(args);


        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите url сайта или путь к файлу с разрешением .html");
        String address = scanner.nextLine();

        DocumentService documentService = new DocumentServiceImpl();

        documentService.downloadPage(address, Parameters.folder, Parameters.type);
        Map<String, Integer> map = documentService.statistic(address, Parameters.type);

        System.out.println(map);

        if(Parameters.dbUse.equals("true")) {
            System.out.println("type db user:");

            String dbUsername = scanner.nextLine();
            System.out.println("type db password:");

            String dbPassword = scanner.nextLine();
            System.out.println("type db url:");

            String dbUrl = scanner.nextLine();
            System.out.println("type db driver:");

            String dbDriver = scanner.nextLine();

            DataBaseConfiguration dbConfig =
                    new DataBaseConfiguration(dbUsername, dbPassword, dbUrl, dbDriver);
            connection = dbConfig.getConnection();
            documentService.save(map, address);
        }
    }
}
