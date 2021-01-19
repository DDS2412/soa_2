package soa.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity()
@Table(name = "starships")
@AllArgsConstructor
@NoArgsConstructor
public class Starship {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "starship_generator")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "starship", targetEntity = SpaceMarine.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpaceMarine> spaceMarines;
}
