package de.jservice.kidsgard.data;

import java.time.LocalDate;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author AmrReda
 */
@Entity
@Table(name = "time")
@AttributeOverride(name = "id", column = @Column(name = "time_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class TimeAccount extends BaseEntityAudit{

    private int minutes;
    private int limits;
    private LocalDate useDay;
    
    public TimeAccount() {
    }
    
    public int getMinutes() {
         return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getLimits() {
        return limits;
    }

    public void setLimits(int limits) {
        this.limits = limits;
    }
    
     public LocalDate getUseDay() {
        return useDay;
    }

    public void setUseDay(LocalDate useDay) {
        this.useDay = useDay;
    }
    @Override
    public String toString() {
        return "minutes=" + minutes + ", limits=" + limits ;
    }

}
