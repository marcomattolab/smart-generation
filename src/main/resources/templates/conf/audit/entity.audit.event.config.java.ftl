package ${packageClass}.${srcConfigAuditFolder};

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ${className} implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        EntityAuditEventListener.setBeanFactory(beanFactory);
    }

}
