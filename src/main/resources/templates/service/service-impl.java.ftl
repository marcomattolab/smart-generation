package ${packageName}.${serviceImplPackage};

import ${packageName}.${servicePackage}.${serviceClassName};
import ${packageName}.${domainPackage}.${entityName};
import ${packageName}.${repositoryPackage}.${repositoryClassName};
import ${packageName}.${dtoPackage}.${dtoClassName};
import ${packageName}.${mapperPackage}.${mapperClassName};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing ${entityName}.
 */
@Service
@Transactional
public class ${className} implements ${serviceClassName} {
    private final Logger log = LoggerFactory.getLogger(${className}.class);
    private final ${repositoryClassName} ${repositoryVarName};
    private final ${mapperClassName} ${mapperVarName};

    public ${className}(${repositoryClassName} ${repositoryVarName}, ${mapperClassName} ${mapperVarName}) {
        this.${repositoryVarName} = ${repositoryVarName};
        this.${mapperVarName} = ${mapperVarName};
    }

    /**
     * Save a ${entityFieldName}.
     *
     * @param ${dtoVarName} the entity to save
     * @return the persisted entity
     */
    @Override
    public ${dtoClassName} save(${dtoClassName} ${dtoVarName}) {
        log.debug("Request to save ${entityName} : {}", ${dtoVarName});
        ${entityName} ${entityVarName} = ${mapperVarName}.toEntity(${dtoVarName});
        ${entityVarName} = ${repositoryVarName}.save(${entityVarName});
        return ${mapperVarName}.toDto(${entityVarName});
    }

    /**
     * Get all the ${entityFieldName}s.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<${dtoClassName}> findAll(Pageable pageable) {
        log.debug("Request to get all ${entityName}s");
        return ${repositoryVarName}.findAll(pageable)
            .map(${mapperVarName}::toDto);
    }

    /**
     * Get one ${entityFieldName} by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<${dtoClassName}> findOne(Long id) {
        log.debug("Request to get ${entityName} : {}", id);
        return ${repositoryVarName}.findById(id)
            .map(${mapperVarName}::toDto);
    }

    /**
     * Delete the ${entityFieldName} by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ${entityName} : {}", id);
        ${repositoryVarName}.deleteById(id);
    }

}
