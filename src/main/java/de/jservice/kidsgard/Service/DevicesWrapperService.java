package de.jservice.kidsgard.Service;

import de.jservice.kidsgard.data.DeviceWrapper;
import de.jservice.kidsgard.integration.DeviceWrapperRepository;
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
public class DevicesWrapperService extends CrudService<DeviceWrapper>{

private final DeviceWrapperRepository repo;
@Autowired
 public DevicesWrapperService(DeviceWrapperRepository repo) {
        this.repo = repo;
    }

    @Override
    protected DeviceWrapperRepository getRepository() {
        return repo;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<DeviceWrapper> findAnyMatching(Optional<String> filter, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
