package ${packageName}.${webRestPackage};

import ${packageName}.${domainPackage}.EntityAuditEvent;
import ${packageName}.${repositoryPackage}.EntityAuditEventRepository;
import ${packageName}.${securityPackage}.AuthoritiesConstants;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for getting the audit events for entity
 */
@RestController
@RequestMapping("/api/audits/entity")
@PreAuthorize("hasRole(\\"" + AuthoritiesConstants.ADMIN + "\\")")
public class EntityAuditResource {
    private final Logger log = LoggerFactory.getLogger(EntityAuditResource.class);
    private final EntityAuditEventRepository entityAuditEventRepository;

    public EntityAuditResource(EntityAuditEventRepository entityAuditEventRepository) {
        this.entityAuditEventRepository = entityAuditEventRepository;
    }

    /**
     * fetches all the audited entity types
     *
     * @return
     */
    @RequestMapping(value = "/types",
        method = RequestMethod.GET,
        produces = "application/json")
    public List<String> getAuditedEntities() {
        return entityAuditEventRepository.findAllEntityTypes();
    }

    /**
     * fetches the last 100 change list for an entity class, if limit is passed fetches that many changes
     *
     * @return
     */
    @RequestMapping(value = "/changes",
        method = RequestMethod.GET,
        produces = "application/json")
    public ResponseEntity<List<EntityAuditEvent>> getChanges(@RequestParam(value = "entityType") String entityType,
                                                             @RequestParam(value = "limit") int limit,
                                                             Pageable pageable) {
        log.debug("REST request to get a page of EntityAuditEvents");
        Page<EntityAuditEvent> page = entityAuditEventRepository.findAllByEntityType(entityType, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + page.getTotalElements());
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * fetches a previous version of the entity identified by entityId and commitVersion
     *
     * @param entityType
     * @param entityId
     * @param commitVersion
     * @return
     */
    @RequestMapping(value = "/previous",
        method = RequestMethod.GET,
        produces = "application/json")
    public ResponseEntity<EntityAuditEvent> getPrevVersion(@RequestParam(value = "entityType") String entityType,
                                                             @RequestParam(value = "entityId") Long entityId,
                                                             @RequestParam(value = "commitVersion") Integer commitVersion) {
        EntityAuditEvent prev = entityAuditEventRepository.findOneByEntityTypeAndEntityIdAndCommitVersion(entityType, entityId, commitVersion);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prev));
    }

}
