package org.jramirezdfernandez.ninja;

import lombok.Data;

import java.util.List;


@Data
public class NinjaDTO {
    private Long id;
    private String name;
    private String rank;
    private Integer atk;
    private Integer def;
    private Integer chakra;
    private String aldea;
    private List<String> jutsus;
}