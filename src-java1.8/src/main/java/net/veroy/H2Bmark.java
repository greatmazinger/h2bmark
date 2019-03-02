/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package net.veroy.research;

import com.opencsv.CSVReaderHeaderAware;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import org.h2.tools.DeleteDbFiles;


/**
 * A simple H2 benchmark.
 * Author: Raoul Veroy
 */
public class H2Bmark {

    /**
     *
     * @param args ignored
     */
    public static void main(String... args) throws Exception {
        // TODO: Hardcoded link. Should make this a command-line argument:
        // delete the database named 'tripdata' in the user home directory
        String dbpath = "/data/rveroy/pulsrc/BMARKS/";
        DeleteDbFiles.execute(dbpath, "tripdata", true);
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:" + dbpath + "tripdata");
        Statement stat = conn.createStatement();

        // this line would initialize the database
        // from the SQL script file 'init.sql'
        // stat.execute("runscript from 'init.sql'");

        stat.execute("create table tripdata(id int primary key," +
                     "                      duration_sec int," +
                     "                      start_station_id int," +
                     "                      end_station_id int," + 
                     "                      bike_id int," +
                     "                      user_type varchar(255)," +
                     "                      member_birth_year int," +
                     "                      member_gender varchar(25))");

        // TODO: Hardcoded link. Should make this a command-line argument:
        String csvFile = "/data/rveroy/pulsrc/BMARKS/201901-fordgobike-tripdata.csv";

        Map<String, String> values = new CSVReaderHeaderAware(new FileReader(csvFile)).readMap();

        // stat.execute("insert into tripdata values(1, 'Hello')");
        ResultSet rs;
        // rs = stat.executeQuery("select * from tripdata");
        // while (rs.next()) {
        //     System.out.println(rs.getString("name"));
        // }
        stat.close();
        conn.close();
    }

}
