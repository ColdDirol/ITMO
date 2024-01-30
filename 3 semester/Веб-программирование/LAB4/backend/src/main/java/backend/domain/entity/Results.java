package backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "results")
public class Results {
    @Id
    @GeneratedValue // AUTO - default
    private Integer id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
