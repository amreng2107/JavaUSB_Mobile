
package de.jservice.kidsgard.Service;

import de.jservice.kidsgard.data.Devices;
import de.jservice.kidsgard.integration.DeviceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author AmrReda
 */
@Service
public class DevicesServices extends CrudService<Devices>{

    private final DeviceRepository repo;
    
    @Autowired
    public DevicesServices(DeviceRepository repo) {
    this.repo = repo;
    }
 
    @Override
    protected DeviceRepository getRepository() {
        return repo;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Devices> findAnyMatching(Optional<String> filter, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public long count(){
        System.out.print("getRepository().count()"+getRepository().count());
    return getRepository().count();
    }
    
    public List<Devices> findAll(){
        System.out.print("getRepository().findAll()"+getRepository().findAll());
    return getRepository().findAll();
    }
    
}
