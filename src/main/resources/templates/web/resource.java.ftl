package ${packageName}.${webRestPackage};

import ${packageName}.${errorsPackage}.BadRequestAlertException;
import ${packageName}.${utilPackage}.HeaderUtil;
import ${packageName}.${utilPackage}.PaginationUtil;
import ${packageName}.${dtoPackage}.${dtoClassName};
import ${packageName}.${dtoPackage}.${criteriaClassName};
import ${packageName}.${servicePackage}.${serviceClassName};
import ${packageName}.${servicePackage}.${queryServiceClassName};
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ${entityName}.
 */
@RestController
@RequestMapping("/api")
public class ${className} {
    private final Logger log = LoggerFactory.getLogger(${className}.class);
    private static final String ENTITY_NAME = "${entityFieldName}";
    private final ${serviceClassName} ${serviceVarName};
    private final ${queryServiceClassName} ${queryServiceVarName};

    public ${className}(${serviceClassName} ${serviceVarName}, ${queryServiceClassName} ${queryServiceVarName}) {
        this.${serviceVarName} = ${serviceVarName};
        this.${queryServiceVarName} = ${queryServiceVarName};
    }

    /**
     * POST  /${restName} : Create a new ${entityFieldName}.
     *
     * @param ${dtoVarName} the ${dtoVarName} to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ${dtoVarName}, or with status 400 (Bad Request) if the ${entityFieldName} has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/${restName}")
    public ResponseEntity<${dtoClassName}> create${entityName}(@Valid @RequestBody ${dtoClassName} ${dtoVarName}) throws URISyntaxException {
        log.debug("REST request to save ${entityName} : {}", ${dtoVarName});
        if (${dtoVarName}.getId() != null) {
            throw new BadRequestAlertException("A new ${entityFieldName} cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ${dtoClassName} result = ${serviceVarName}.save(${dtoVarName});
        return ResponseEntity.created(new URI("/api/${restName}/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /${restName} : Updates an existing ${entityFieldName}.
     *
     * @param ${dtoVarName} the ${dtoVarName} to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ${dtoVarName},
     * or with status 400 (Bad Request) if the ${dtoVarName} is not valid,
     * or with status 500 (Internal Server Error) if the ${dtoVarName} couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/${restName}")
    public ResponseEntity<${dtoClassName}> update${entityName}(@Valid @RequestBody ${dtoClassName} ${dtoVarName}) throws URISyntaxException {
        log.debug("REST request to update ${entityName} : {}", ${dtoVarName});
        if (${dtoVarName}.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ${dtoClassName} result = ${serviceVarName}.save(${dtoVarName});
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ${dtoVarName}.getId().toString()))
            .body(result);
    }

    /**
     * GET  /${restName} : get all the ${entityFieldName}s.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ${entityFieldName}s in body
     */
    @GetMapping("/${restName}")
    public ResponseEntity<List<${dtoClassName}>> getAll${entityName}s(${criteriaClassName} criteria, Pageable pageable) {
        log.debug("REST request to get ${entityName}s by criteria: {}", criteria);
        Page<${dtoClassName}> page = ${queryServiceVarName}.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/${restName}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /${restName}/count : count all the ${entityFieldName}s.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/${restName}/count")
    public ResponseEntity<Long> count${entityName}s(${criteriaClassName} criteria) {
        log.debug("REST request to count ${entityName}s by criteria: {}", criteria);
        return ResponseEntity.ok().body(${queryServiceVarName}.countByCriteria(criteria));
    }

    /**
     * GET  /${restName}/{id} : get the "id" ${entityFieldName}.
     *
     * @param id the id of the ${dtoVarName} to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ${dtoVarName}, or with status 404 (Not Found)
     */
    @GetMapping("/${restName}/{id}")
    public ResponseEntity<${dtoClassName}> get${entityName}(@PathVariable Long id) {
        log.debug("REST request to get ${entityName} : {}", id);
        Optional<${dtoClassName}> ${dtoVarName} = ${serviceVarName}.findOne(id);
        return ResponseUtil.wrapOrNotFound(${dtoVarName});
    }

    /**
     * DELETE  /${restName}/{id} : delete the "id" ${entityFieldName}.
     *
     * @param id the id of the ${dtoVarName} to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/${restName}/{id}")
    public ResponseEntity<Void> delete${entityName}(@PathVariable Long id) {
        log.debug("REST request to delete ${entityName} : {}", id);
        ${serviceVarName}.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
