package soa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    public Chapter(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Min(0)
    @Max(1000)
    private int marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
}
