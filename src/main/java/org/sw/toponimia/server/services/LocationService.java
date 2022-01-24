package org.sw.toponimia.server.services;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.sw.toponimia.server.configs.MapConfigurations;
import org.sw.toponimia.server.domain.LocationFilter;
import org.sw.toponimia.server.domain.entities.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Lazy
public class LocationService {
    private List<Location> locations;
    
    public LocationService() throws IOException{
        log.info("Reading locations from {}", MapConfigurations.LOCATIONS_FILE_NAME);
        locations = new CustomCsvReader().read(MapConfigurations.LOCATIONS_FILE_NAME);
        log.info("Loaded {} locations", locations.size());
    }

    public List<Location> filterBy(List<LocationFilter> filters) {
        if (filters.size() == 0) return new ArrayList();
         return locations.stream().filter((Predicate<Location>) l -> {
            for (LocationFilter filter : filters) {
                if (!filter.filter(l)) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}
