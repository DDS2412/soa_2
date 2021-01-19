package soa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import soa.enums.AstartesCategory;
import soa.enums.MeleeWeapon;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "space_marines")
@NoArgsConstructor
public class SpaceMarine {
    public SpaceMarine(
            String name,
            Coordinates coordinates,
            Integer height,
            AstartesCategory category,
            long health,
            MeleeWeapon meleeWeapon,
            Chapter chapter){
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.category = category;
        this.health = health;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(
            String name,
            Coordinates coordinates,
            Integer height,
            AstartesCategory category,
            MeleeWeapon meleeWeapon,
            Chapter chapter){
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.category = category;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "space_marine_generator")
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull
    @NotEmpty
    @Column(name = "space_marine_name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(0)
    private long health; //Значение поля должно быть больше 0

    private Integer height; //Поле может быть null

    @NotNull
    private AstartesCategory category; //Поле не может быть null

    @NotNull
    private MeleeWeapon meleeWeapon; //Поле не может быть null

    private Chapter chapter; //Поле может быть null

    @ManyToOne
    @JoinColumn(name = "starship_id", referencedColumnName = "id")
    @JsonIgnore
    private Starship starship;
}
