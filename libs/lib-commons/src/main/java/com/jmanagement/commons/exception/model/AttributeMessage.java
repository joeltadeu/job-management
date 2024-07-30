package com.jmanagement.commons.exception.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String attribute;
    private List<String> errors = new ArrayList<>();

    public AttributeMessage(String attribute, String error) {
        this.attribute = attribute;
        addError(error);
    }

    public void addError(String error) {
        this.errors.add(error);
    }
}
