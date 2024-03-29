package  io.github.hlg212.fcf.annotation;


import io.github.hlg212.fcf.Constants;
import  io.github.hlg212.fcf.env.DefaultPackagePropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j

class CloudApplicationRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        String basePackage = null;
        String cname = annotationMetadata.getClassName();
        basePackage = cname.substring(0, cname.lastIndexOf(".") );
        if( !basePackage.equalsIgnoreCase(Constants.FRAME_BASE_PACKAGE) )
        {
            DefaultPackagePropertySource.add(basePackage);
        }

    }

}
