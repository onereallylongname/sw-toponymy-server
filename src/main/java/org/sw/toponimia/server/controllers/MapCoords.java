package org.sw.toponimia.server.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sw.toponimia.server.domain.LocationFilter;
import org.sw.toponimia.server.domain.entities.Location;
import org.sw.toponimia.server.services.CoordsTransform;
import org.sw.toponimia.server.services.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MapCoords {

    private final CoordsTransform coordsTransform;
    private final LocationService locationService;

    @GetMapping("/map")
    public Map<String, Double> getList(@RequestParam(required = true) int x,
            @RequestParam(required = true) int y,
            @RequestParam(defaultValue = "epsg:20790", required = false) String epsg_s,
            @RequestParam(defaultValue = "epsg:4326", required = false) String epsg_t) {
        log.info("Recived request for mapping x={} and y={} from={} to={}", x, y, epsg_s, epsg_t);
        ProjCoordinate result = coordsTransform.transform(x, y, epsg_s, epsg_t);
        HashMap<String, Double> out = new HashMap<>(2);
        out.put("x", result.x);
        out.put("y", result.y);
        log.info("Coords {}", out);
        return out;
    }

    @GetMapping("/filter")
    public List<Location> filterLocations(@RequestParam(required = false) String nameContains,
                                            @RequestParam(required = false) Double maxDist,
                                            @RequestParam(required = false) Double x,
                                            @RequestParam(required = false) Double y
                                        ) {

        List<LocationFilter> locationFilters = new ArrayList<>(2);
        
        log.info("nameContains: {}, maxDist: {}, x: {}, y: {}", nameContains, maxDist, x, y);

        if(nameContains != null) {
            locationFilters.add( new LocationFilter() {
                @Override
                public boolean filter(Location location) {
                    return location.getName().contains(nameContains.toUpperCase());
                }
            });
        }

        if(maxDist != null && x != null && y != null) {
            locationFilters.add(new LocationFilter() {
                @Override
                public boolean filter(Location location) {
                    double distance = Math.sqrt( Math.pow(x - location.getX(), 2) + Math.pow(y - location.getY(), 2));

                    return distance < maxDist;
                }
            });
        }

        return locationService.filterBy(locationFilters);
    }
}
