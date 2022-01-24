package org.sw.toponimia.server.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Location {
    private String name;
    private String description;
    private int x;
    private int y;
    private double lat;
    private double lon;
    private double alt; 
    private String page;
}
