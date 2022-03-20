package com.hlg.fcf.util;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.cache.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.Method;
import java.util.*;

public class CacheNameHelper {

    private static CahceNameScanner cahceNameScanner;

    private static CahceNameScanner getCahceNameScanner() {
        if( cahceNameScanner == null ) {
            cahceNameScanner = new CahceNameScanner();
        }
        return cahceNameScanner;
    }

    public static Set getCahceNames()
    {
        return getCahceNameScanner().getCacheNames();
    }

    public static Map<String, Set> getCacheTypeMap(){
        return getCahceNameScanner().getCacheTypeMap();
    }


    @Slf4j
    private static class CahceNameScanner extends ClassPathScanningCandidateComponentProvider {

        public Set cacheNameSet = null;

        public Map<String, Set> cacheMap = null;

        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            AnnotationMetadata metadata = beanDefinition.getMetadata();
            try {
                Class<?> c = Class.forName(metadata.getClassName());
                Map cacheConfigAttr = metadata.getAnnotationAttributes("org.springframework.cache.annotation.CacheConfig");
                if( cacheConfigAttr != null && !cacheConfigAttr.isEmpty() )
                {
                    String cacheType = cacheConfigAttr.get("cacheManager") + "";
                    if(!Constants.CacheManager.ThreadLocalCacheManager.equals(cacheType)){
                        if(StringUtils.isBlank(cacheType)){
                            cacheType = "defCacheManager";
                        }
                        Set cacheSet = new LinkedHashSet();

                        String[] cacheNames = (String[])cacheConfigAttr.get("cacheNames");
                        cacheNameSet.addAll(getCacheNams(cacheNames,cacheNames));
                        cacheSet.addAll(getCacheNams(cacheNames,cacheNames));
                        Method[] ms = c.getDeclaredMethods();
                        for( Method m : ms )
                        {
                            Cacheable cacheables[] =  m.getAnnotationsByType( Cacheable.class  );
                            cacheNameSet.addAll(getCacheNams(cacheNames,cacheables));
                            cacheSet.addAll(getCacheNams(cacheNames,cacheables));

                            CacheEvict cacheEvicts[] = m.getAnnotationsByType( CacheEvict.class  );
                            cacheNameSet.addAll(getCacheNams(cacheNames,cacheEvicts));
                            cacheSet.addAll(getCacheNams(cacheNames,cacheEvicts));

                            CachePut cachePuts[] =  m.getAnnotationsByType( CachePut.class  );
                            cacheNameSet.addAll(getCacheNams(cacheNames,cachePuts));
                            cacheSet.addAll(getCacheNams(cacheNames,cachePuts));
                        }

                        if(cacheMap.containsKey(cacheType)){
                            cacheMap.get(cacheType).addAll(cacheSet);
                        }else{
                            cacheMap.put(cacheType, cacheSet);
                        }
                    }

                }

            } catch (ClassNotFoundException e) {

            }
            return false;
        }

        private Set<String> getCacheNams(String configCacheNames[],Cacheable cacheables[])
        {
            Set<String> set = new HashSet<>();
            for( Cacheable cacheb : cacheables )
            {
                if( !(cacheb instanceof CacheableReadOnly) )
                {
                    String cns[] = cacheb.cacheNames();
                    set.addAll(getCacheNams(configCacheNames,cns));
                }
            }
            return set;
        }

        private Set<String> getCacheNams(String configCacheNames[],CacheEvict cacheables[])
        {
            Set<String> set = new HashSet<>();
            for( CacheEvict cacheb : cacheables )
            {
                String cns[] = cacheb.cacheNames();
                set.addAll(getCacheNams(configCacheNames,cns));
            }
            return set;
        }

        private Set<String> getCacheNams(String configCacheNames[],CachePut cacheables[])
        {
            Set<String> set = new HashSet<>();
            for( CachePut cacheb : cacheables )
            {
                String cns[] = cacheb.cacheNames();
                set.addAll(getCacheNams(configCacheNames,cns));
            }
            return set;
        }
        private Set<String> getCacheNams(String configCacheNames[],String cacheNames[])
        {
            Set<String> set = new HashSet<>();
            if( cacheNames != null && cacheNames.length > 0 )
            {
                set.addAll(arrToSet(cacheNames));
            }
            else
            {
                set.addAll(arrToSet(configCacheNames));
            }
            return set;
        }

        private Set arrToSet(String cacheNames[])
        {
            Set<String> set = new HashSet<>();
            for( String cacheName : cacheNames)
            {
                set.add(cacheName);
            }
            return set;
        }


        private void init()
        {
            cacheNameSet = new LinkedHashSet();
            cacheMap = new HashMap<>();
            this.addIncludeFilter( new AnnotationTypeFilter(CacheConfig.class));
            this.findCandidateComponents("com.hlg");
        }

        public Set getCacheNames()
        {
            if(cacheNameSet == null )
            {
                init();
            }
            return cacheNameSet;
        }

        public Map<String, Set> getCacheTypeMap()
        {
            if(cacheMap == null )
            {
                init();
            }
            return cacheMap;
        }


    }

}
