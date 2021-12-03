package com.unicorns.challenge.CodingAssignment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Unicorn {
    @Id
    private String unicornId;
    private String name;
    private String hairColor;
    private int hornLength;
    private String hornColor;
    private String eyeColor;
    private int height;
    private String heightUnit;
    private int weight;
    private String weightUnit;
    private List<IdentifiableMark> identifiableMarks;
}

