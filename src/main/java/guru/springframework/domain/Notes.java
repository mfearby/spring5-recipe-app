package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    // JPA defaults to varchar(255), so @Lob ("large object") will do a varchar(max) or equivalent for DB in use
    @Lob
    private String recipeNotes;

}
