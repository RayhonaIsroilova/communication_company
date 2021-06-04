package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/serviceCategory")
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {
}
