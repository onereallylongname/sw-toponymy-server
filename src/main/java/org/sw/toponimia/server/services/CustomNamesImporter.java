package org.sw.toponimia.server.services;

import org.sw.toponimia.server.configs.MapConfigurations;
import org.sw.toponimia.server.domain.entities.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.w3c.dom.Node;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomNamesImporter {

    private static final String TAG_NAME = "Continente";

    private final CoordsTransform coordsTransform;

    public List<Location> load(String fileName) throws FileNotFoundException {
        try {
            // creating a constructor of file class and parsing an XML file
            File file = new File(fileName);
            // an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(TAG_NAME);

            log.info("Found {} locations", nodeList.getLength());

            ArrayList<Location> locations = new ArrayList<>(nodeList.getLength());

            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String name = node.getAttributes().getNamedItem("Nome").getTextContent();

                    log.debug("Processing {} {}", itr, name);

                    int x = Integer.valueOf(node.getAttributes().getNamedItem("X").getTextContent()) * 100;
                    int y = Integer.valueOf(node.getAttributes().getNamedItem("Y").getTextContent()) * 100;
                    String desc = node.getAttributes().getNamedItem("Descricao").getTextContent();
                    String page = node.getAttributes().getNamedItem("Folha").getTextContent();
                    
                    // Multiply x and y by 100 to get meters
                    ProjCoordinate result = coordsTransform.transform(x, y, "epsg:20790", "epsg:4326");

                    locations.add(
                        Location.builder()
                        .name(name)
                        .x(x)
                        .y(y)
                        .description(desc)
                        .page(page)
                        .lat(result.y)
                        .lon(result.x)
                        .alt(result.z)
                        .build()
                    );
                    
                }
            }
            return locations;
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
