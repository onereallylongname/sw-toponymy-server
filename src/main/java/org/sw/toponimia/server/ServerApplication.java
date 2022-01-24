package org.sw.toponimia.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.sw.toponimia.server.domain.entities.Location;
import org.sw.toponimia.server.services.CoordsTransform;
import org.sw.toponimia.server.services.CustomCsvWriter;
import org.sw.toponimia.server.services.CustomNamesImporter;

@SpringBootApplication
public class ServerApplication {

/* @Autowired
CoordsTransform coordsTransform; */

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


    /* @PostConstruct
	public void load() {

		CustomNamesImporter customNamesImporter = new CustomNamesImporter(coordsTransform);
	
		CustomCsvWriter customCsvWriter = new CustomCsvWriter();

		String inFileName = "C:/Program Files/SW/topo/java/sw/server/data/Continente.xml";
		String outFileName = "C:/Program Files/SW/topo/java/sw/server/data/locations.csv";
		try {
			List<Location> out = customNamesImporter.load(inFileName);
			System.out.println(out);
			customCsvWriter.write(out, outFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
