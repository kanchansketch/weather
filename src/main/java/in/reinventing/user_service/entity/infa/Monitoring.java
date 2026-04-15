package in.reinventing.user_service.entity.infa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "map_type")
public class Monitoring {

    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "component",nullable = false)
    private String component;

    @Column(name = "start_time",nullable = false)
    private Instant startTime;

    @Column(name = "end_time",nullable = false)
    private Instant endTime;

    @Column(name = "load_time")
    private Long loadTime;

    @PostLoad
    public void updateLoadTime(){
        loadTime = endTime.getEpochSecond() - startTime.getEpochSecond();
    }
}
