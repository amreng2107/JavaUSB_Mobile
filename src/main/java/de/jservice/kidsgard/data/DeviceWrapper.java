package de.jservice.kidsgard.data;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author AmrReda
 */
@Entity
@Table(name = "internaldevice")
@AttributeOverride(name = "id", column = @Column(name = "indevice_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class DeviceWrapper extends BaseEntityAudit{

    private String vendorId;
    private String productId;

    public DeviceWrapper() {
    }
   
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
     @Override
    public String toString() {
        return "DeviceWrapper{" +  " vendorId=" + vendorId + ", productId=" + productId + '}';
    }
}
