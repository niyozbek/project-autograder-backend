package uk.ac.swansea.autograder.general.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleType name;

    public enum RoleType {
        ADMIN("ADMIN"),
        LECTURER("LECTURER"),
        STUDENT("STUDENT");

        private final String value;

        RoleType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    // remaining getters and setters
}
