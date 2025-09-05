package ${packageName}.${servicePackage};

import ${packageName}.${dtoPackage}.${entityName}DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface for managing ${entityName}.
 */
public interface ${className} {

    /**
     * Save a ${entityFieldName}.
     *
     * @param ${entityFieldName}DTO the entity to save
     * @return the persisted entity
     */
    ${entityName}DTO save(${entityName}DTO ${entityFieldName}DTO);

    /**
     * Get all the ${entityFieldName}s.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<${entityName}DTO> findAll(Pageable pageable);

    /**
     * Get the "id" ${entityFieldName}.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<${entityName}DTO> findOne(Long id);

    /**
     * Delete the "id" ${entityFieldName}.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

}
