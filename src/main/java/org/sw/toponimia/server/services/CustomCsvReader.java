package org.sw.toponimia.server.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.sw.toponimia.server.domain.entities.Location;

public class CustomCsvReader {

    public List<Location> read(String path) throws IOException {
       InputStreamReader in = new InputStreamReader(new FileInputStream(path), "UTF-8");
      /* FileReader in = new FileReader(path, Charset.forName("UTF-8")); */
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
          .withFirstRecordAsHeader()
          .parse(in);
        List<Location> locations = new ArrayList<>();
        for (CSVRecord record : records) {
           locations.add(Location.builder()
           .name(record.get(0))
           .description(record.get(1))
           .x(Integer.valueOf(record.get(2)))
           .y(Integer.valueOf(record.get(3)))
           .lat(Double.valueOf(record.get(4)))
           .lon(Double.valueOf(record.get(5)))
           .alt(Double.valueOf(record.get(6)))
           .page(record.get(7))
           .build());
        }  
        return locations; 
    }
}
