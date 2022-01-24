package org.sw.toponimia.server.services;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

@Service
public class CoordsTransform {

    private CRSFactory crsFactory = new CRSFactory();

    public ProjCoordinate transform(int x, int y, String epsg_s, String epsg_t) {
        CoordinateReferenceSystem wgs84 = crsFactory.createFromName(epsg_t);
		CoordinateReferenceSystem lisbon = crsFactory.createFromName(epsg_s);
		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		CoordinateTransform wgsToUtm = ctFactory.createTransform(lisbon, wgs84);
		// `result` is an output parameter to `transform()`
		ProjCoordinate result = new ProjCoordinate();
		wgsToUtm.transform(new ProjCoordinate(x, y), result);
        return result;
    }
}
