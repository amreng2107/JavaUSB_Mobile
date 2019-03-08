package de.jservice.kidsgard.data;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author AmrReda
 */
@Entity
@Table(name = "device")
@AttributeOverride(name = "id", column = @Column(name = "device_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Devices extends BaseEntityAudit{

    private String name;
    
    @OneToOne
    private DeviceWrapper device;
    
    @OneToOne
    private TimeAccount account;
    
    private Status status;
 
    @OneToOne
    private  Warning warningList;

    public Devices() {
        status = Status.ONLINE;
    }
    
    public DeviceWrapper getDevice() {
        return device;
    }

    public void setDevice(DeviceWrapper device) {
        this.device = device;
    }
  
    public TimeAccount getAccount() {
        return account;
    }

    public void setAccount(TimeAccount account) {
        this.account = account;
    }
    
     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Warning getWarningList() {
        return warningList;
    }

    public void setWarningList(Warning warningList) {
        this.warningList = warningList;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Devices{" + "name=" + name + ", device=" + device + ", account=" + account + ", status=" + status + ", warningList=" + warningList + '}';
    }
}
