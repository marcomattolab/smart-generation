package ${packageClass}.${srcRepositoryFolder};

import ${packageClass}.${srcDomainFolder}.${entityName};
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the ${entityName} entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ${className} extends JpaRepository<${entityName}, Long>, JpaSpecificationExecutor<${entityName}> {

}
