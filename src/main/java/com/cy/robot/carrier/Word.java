package com.cy.robot.carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private String word;

    private String nature;

    private int offset;
}
