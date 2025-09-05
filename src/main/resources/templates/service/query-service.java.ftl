package ${packageName}.${servicePackage};

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.StringFilter;
import ${packageName}.${domainPackage}.${entityName};
import ${packageName}.${domainPackage}.*; // for static metamodels
import ${packageName}.${repositoryPackage}.${entityName}Repository;
import ${packageName}.${dtoPackage}.${entityName}Criteria;
import ${packageName}.${dtoPackage}.${entityName}DTO;
import ${packageName}.${mapperPackage}.${entityName}Mapper;
import ${packageName}.${securityPackage}.AuthoritiesConstants;
import ${packageName}.${securityPackage}.SecurityUtils;
import java.util.Optional;

/**
 * Service for executing complex queries for ${entityName} entities in the database.
 * The main input is a {@link ${entityName}Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ${entityName}DTO} or a {@link Page} of {@link ${entityName}DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ${className} extends QueryService<${entityName}> {
    private final Logger log = LoggerFactory.getLogger(${className}.class);
    private final ${entityName}Repository repository;
    private final ${entityName}Mapper mapper;
    private final boolean securityEnabled = true;

    public ${className}(${entityName}Repository repository, ${entityName}Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Return a {@link List} of {@link ${entityName}DTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<${entityName}DTO> findByCriteria(${entityName}Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<${entityName}> specification = createSpecification(criteria);
        return mapper.toDto(repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ${entityName}DTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<${entityName}DTO> findByCriteria(${entityName}Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<${entityName}> specification = createSpecification(criteria);
        return repository.findAll(specification, page)
            .map(mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(${entityName}Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<${entityName}> specification = createSpecification(criteria);
        return repository.count(specification);
    }

    /**
     * Function to convert ${entityName}Criteria to a {@link Specification}
     */
    private Specification<${entityName}> createSpecification(${entityName}Criteria criteria) {
        Specification<${entityName}> specification = Specification.where(null);
        if (criteria != null) {
<#list fields as field>
            if (criteria.get${field.capitalizedName}() != null) {
    <#if field.isString>
                specification = specification.and(buildStringSpecification(criteria.get${field.capitalizedName}(), ${entityName}_.${field.nameForSpec}));
    <#elseif field.isNumeric || field.isDate>
                specification = specification.and(buildRangeSpecification(criteria.get${field.capitalizedName}(), ${entityName}_.${field.nameForSpec}));
    <#else>
                specification = specification.and(buildSpecification(criteria.get${field.capitalizedName}(), ${entityName}_.${field.nameForSpec}));
    </#if>
            }
</#list>

<#list relations as rel>
            if (criteria.get${rel.capitalizedName}Id() != null) {
                specification = specification.and(buildSpecification(criteria.get${rel.capitalizedName}Id(),
                    root -> root.join(${entityName}_.${rel.joinField}, JoinType.LEFT).get(${rel.otherEntityName}_.id)));
            }
</#list>

<#if security.enabled>
            if (securityEnabled) {
                boolean isAutenticated = SecurityUtils.isAuthenticated();
<#list security.profiles as profile>
                boolean is${profile} = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.${profile});
</#list>

                Optional<String> loggedUser = SecurityUtils.getCurrentUserLogin();
                String userName = loggedUser.orElse(null);

                if (isAutenticated && isADMIN) {
                    log.debug("User '"+userName+"' with profile ADMIN is enabled to see all ${entityName} items.");
                } else if (isAutenticated && is${security.defaultUserProfile}) {
                    log.debug("User '"+userName+"' with profile ${security.defaultUserProfile} is enabled to see ONLY your own ${entityName} items.");
                    StringFilter currentUser = new StringFilter();
                    currentUser.setEquals(userName);
                    specification = specification.and(buildStringSpecification(currentUser, ${entityName}_.createdBy));
                } else {
                    log.debug("User is NOT Autenticated and NOT enabled to see NO ${entityName} items.");
                    StringFilter currentUser = new StringFilter();
                    currentUser.setEquals("-1");
                    specification = specification.and(buildStringSpecification(currentUser, ${entityName}_.createdBy));
                }
            }
</#if>
        }
        return specification;
    }
}
