package org.sw.toponimia.server.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.sw.toponimia.server.domain.entities.Location;

public class CustomCsvWriter {

    public void write(List<Location> locations, String outFileName) throws IOException {
        OutputStreamWriter outFileWriter = new OutputStreamWriter(new FileOutputStream(outFileName), StandardCharsets.UTF_8);
        CSVPrinter printer = new CSVPrinter(outFileWriter, CSVFormat.DEFAULT);
        addLine(printer, "Name", "Description", "X", "Y", "Latitude", "Longitude", "Altitude", "Page", "Index");
        for (Location location : locations) {
            addLocationLine(printer, location);
        }
        printer.close();
    }
    
    private void addLine(CSVPrinter printer, Object... objs) throws IOException {
        for (Object object : objs) {
            printer.print(object);
        }
        printer.println();
    }

    private void addLocationLine(CSVPrinter printer, Location location) throws IOException {
        printer.print(location.getName());
        printer.print(location.getDescription());
        printer.print(location.getX());
        printer.print(location.getY());
        printer.print(location.getLat());
        printer.print(location.getLon());
        printer.print(location.getAlt());
        printer.print(location.getPage());
        printer.println();
    }
}
