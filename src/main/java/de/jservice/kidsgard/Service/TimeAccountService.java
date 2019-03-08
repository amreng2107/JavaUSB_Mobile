package de.jservice.kidsgard.Service;

import de.jservice.kidsgard.data.TimeAccount;
import de.jservice.kidsgard.integration.TimeAccountRepository;
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
public class TimeAccountService extends CrudService<TimeAccount>{

  
    private final TimeAccountRepository repo;

    @Autowired
    public TimeAccountService(TimeAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    protected TimeAccountRepository getRepository() {
        return repo;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<TimeAccount> findAnyMatching(Optional<String> filter, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteTimeAccount(TimeAccount t){
    getRepository().delete(t);
    }
}
