package org.sw.toponimia.server.domain;

import org.sw.toponimia.server.domain.entities.Location;

public interface LocationFilter {
    public boolean filter(Location location);
}
