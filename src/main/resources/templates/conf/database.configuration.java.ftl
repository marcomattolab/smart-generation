package ${packageClass}.${srcConfigFolder};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.jhipster.config.JHipsterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("${packageClass}.${srcRepositoryFolder}")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class ${className} {

    private final Logger log = LoggerFactory.getLogger(${className}.class);
}
