package com.b.r.loteriab.r.Validation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 13/05/2019.
 */

@Data
public class Result {
    public Object value;

    public List<ValidationResult> lista ;

    public boolean isValid()
    {
        if (lista == null)
        {
            return true;
        }

        return lista.size() == 0;
    }


    public Result()
    {
        lista = new ArrayList<>();
        value = null;
    }

    public Result(Object value)
    {
        this();
        value = value;
    }

    public Result(List<IValidation> validations)
    {
        this();
        if (validations != null)
        {
            for ( IValidation validation : validations)
            {
                add(validation.validate());
            }
        }
    }

    public Result add(Result result)
    {
        if (result != null)
        {
            lista.addAll(result.lista);
        }
        return this;
    }

    public Result add(ValidationResult validation)
    {
        lista.add(validation);
        return this;
    }

    public Result add(List<ValidationResult> validations)
    {
        lista.addAll(validations);
        return this;
    }

    public Result add(String message)
    {
        lista.add(new ValidationResult(message));
        return this;
    }

    public Result add(String message, String field)
    {
        lista.add(new ValidationResult(message, field));
        return this;
    }

    public Result add(String message, List<String> fields)
    {
        lista.add(new ValidationResult(message, fields));
        return this;
    }
}

