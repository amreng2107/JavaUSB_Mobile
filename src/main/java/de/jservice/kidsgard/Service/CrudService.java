package de.jservice.kidsgard.Service;

import de.jservice.kidsgard.data.BaseEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
/**
 *
 * @author AmrReda
 * @param <T>
 */
public abstract class CrudService<T extends BaseEntity> {

	protected abstract CrudRepository<T, Long> getRepository();

	
        public T save(T entity) {
		return getRepository().save(entity);
	}
        
        public void delete(long id) {
		getRepository().delete(id);
	}

	public T load(long id) {
		return getRepository().findOne(id);
	}

	public abstract long countAnyMatching(Optional<String> filter);

	public abstract Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

}
