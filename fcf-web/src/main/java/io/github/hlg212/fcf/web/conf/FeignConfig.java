/**
 * 
 */
package  io.github.hlg212.fcf.web.conf;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import  io.github.hlg212.fcf.Constants;
import  io.github.hlg212.fcf.model.ActionResult;
import  io.github.hlg212.fcf.web.feign.FeignClientResponseEntityDecoder;
import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import  io.github.hlg212.fcf.web.feign.LongRunningResExtLoadBalancerFeignClient;
import feign.*;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 *
 *
 * @author  huangligui
 * @date 2018年9月14日
 */
@Configuration(Constants.FRAME_BEAN_PREFIX + "FeignConfig")
public class FeignConfig {

	@Autowired
	private ObjectFactory<HttpMessageConverters> objectFactory;

	@Autowired(required = false)
	private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();

	@Autowired(required = false)
	private List<FeignFormatterRegistrar> feignFormatterRegistrars = new ArrayList<>();
	
	@Bean
	@ConditionalOnProperty(prefix = "fcf.api.client",name = "longRunningResEnable")
	public Client feignClient(CachingSpringLoadBalancerFactory cachingFactory,
							  SpringClientFactory clientFactory) {
		return new LongRunningResExtLoadBalancerFeignClient(new Client.Default(null, null),
				cachingFactory, clientFactory);
	}

	@Bean
	public Decoder feignDecoder() {
		return new FeignClientResponseEntityDecoder(new SpringDecoder(objectFactory));
	}

	@Bean
	public Contract feignContract(ConversionService feignConversionService) {
		return new OsisContract(this.parameterProcessors, feignConversionService);
	}

	@Bean
	public FormattingConversionService feignConversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		Iterator var2 = this.feignFormatterRegistrars.iterator();

		while(var2.hasNext()) {
			FeignFormatterRegistrar feignFormatterRegistrar = (FeignFormatterRegistrar)var2.next();
			feignFormatterRegistrar.registerFormatters(conversionService);
		}

		return conversionService;
	}


	
	public class OsisContract extends SpringMvcContract {
		private ResourceLoader resourceLoader;
		private Map requestMapping = new HashMap();

		public OsisContract(List<AnnotatedParameterProcessor> parameterProcessors, ConversionService feignConversionService) {
			super(parameterProcessors,feignConversionService);
		}


		@Override
		public void setResourceLoader(ResourceLoader resourceLoader) {
			super.setResourceLoader(resourceLoader);
			this.resourceLoader = resourceLoader;
		}

		@Override
		public List<MethodMetadata> parseAndValidatateMetadata(Class<?> targetType) {
      /*  Util.checkState(targetType.getTypeParameters().length == 0, "Parameterized types unsupported: %s", new Object[]{targetType.getSimpleName()});
        Util.checkState(targetType.getInterfaces().length <= 1, "Only single inheritance supported: %s", new Object[]{targetType.getSimpleName()});
        if (targetType.getInterfaces().length == 1) {
            Util.checkState(targetType.getInterfaces()[0].getInterfaces().length == 0, "Only single-level inheritance supported: %s", new Object[]{targetType.getSimpleName()});
        }*/

			Map<String, MethodMetadata> result = new LinkedHashMap();
			Method[] var3 = targetType.getMethods();
			int var4 = var3.length;

			for(int var5 = 0; var5 < var4; ++var5) {
				Method method = var3[var5];
				//method.getGenericReturnType()
				if( method.getAnnotations().length == 0 ) {
					continue;
				}
				if (method.getDeclaringClass() != Object.class && (method.getModifiers() & 8) == 0 && !Util.isDefault(method)) {

					Method  dm = null;
					try {
						dm = targetType.getDeclaredMethod(method.getName(),method.getParameterTypes());
					} catch (NoSuchMethodException e) {
						//e.printStackTrace();
					}
					if( dm != null && !dm.equals(method) )
					{
						continue;
					}
//					if( dm == null && returnType.isInterface() )
//					{
//						Type type = method.getGenericReturnType();
//
//						if( !(type instanceof TypeVariable) ) {
//							continue;
//						}
//						else
//						{
//							Util.checkState( false, "%s api return 不能使用接口! ", new Object[]{method.toString()});
//						}
//					}

					requestMapping.put(targetType,findRequestMapping(targetType));
					MethodMetadata metadata = this.parseAndValidateMetadata(targetType, method);
					// 当返回值为void 时,不会进入到decode方法，导致异常无法处理
					// 给所有的空值返回设置默认类型
					if (Void.TYPE == metadata.returnType()) {
						metadata.returnType( ActionResult.class);
					}
					//如果是方法级泛型，使用类定义的类型
					// feign方法无法使用方法级泛型
					Type returnType = metadata.returnType();
					boolean isParameterizedType = false;

					if( returnType instanceof  ParameterizedType )
					{
						isParameterizedType = true;
						returnType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
					}

					if( returnType instanceof TypeVariable )
					{
						TypeVariable tvar = (TypeVariable)returnType;
						if( tvar.getGenericDeclaration() instanceof Method )
						{
							returnType = getMehodTypeVariable(targetType,targetType,tvar);
							if( returnType != null )
							{
								if( isParameterizedType )
								{
									returnType = new ParameterizedTypeImpl(new Type[]{returnType},null,((ParameterizedType)metadata.returnType()).getRawType());
								}
								metadata.returnType(returnType);
							}
						}
					}

					// 不知道为什么不允许覆盖方法
					Util.checkState(!result.containsKey(metadata.configKey()), "Overrides unsupported: %s", new Object[]{metadata.configKey()});
					result.put(metadata.configKey(), metadata);
				}
			}

			return new ArrayList(result.values());
		}
		private Type getMehodTypeVariable(Type context, Class<?> rawType, TypeVariable  typeVariable)
		{
			for( Type type : typeVariable.getBounds() )
			{
				if( type instanceof TypeVariable )
				{
					TypeVariable ttvar = (TypeVariable)type;

					if( ttvar.getGenericDeclaration() instanceof Class)
					{
						Class<?> declaredByRaw  = (Class)ttvar.getGenericDeclaration();
						Type declaredBy = getGenericSupertype(context, rawType, declaredByRaw);
						if (declaredBy instanceof ParameterizedType) {
							int index = indexOf(declaredByRaw.getTypeParameters(), ttvar);
							return ((ParameterizedType) declaredBy).getActualTypeArguments()[index];
						}
					}
				}
			}
			return null;
		}


		private Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
			if (toResolve == rawType) {
				return context;
			}

			// We skip searching through interfaces if unknown is an interface.
			if (toResolve.isInterface()) {
				Class<?>[] interfaces = rawType.getInterfaces();
				for (int i = 0, length = interfaces.length; i < length; i++) {
					if (interfaces[i] == toResolve) {
						return rawType.getGenericInterfaces()[i];
					} else if (toResolve.isAssignableFrom(interfaces[i])) {
						Type[]  gifs =  rawType.getGenericInterfaces();
						if( gifs[i] instanceof ParameterizedType)
						{
							return gifs[i];
						}
						return getGenericSupertype(gifs[i], interfaces[i], toResolve);
					}
				}
			}
			return null;
		}

		private int indexOf(Object[] array, Object toFind) {
			for (int i = 0; i < array.length; i++) {
				if (toFind.equals(array[i])) {
					return i;
				}
			}
			throw new NoSuchElementException();
		}

		@Override
		protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
			//if (clz.getInterfaces().length == 0) {

			RequestMapping classAnnotation = (RequestMapping) requestMapping.get(clz);
			if (classAnnotation != null && classAnnotation.value().length > 0) {
				String pathValue = Util.emptyToNull(classAnnotation.value()[0]);
				pathValue = this.resolve(pathValue);
				if (!pathValue.startsWith("/")) {
					pathValue = "/" + pathValue;
				}
				data.template().uri(pathValue);
				//data.template().insert(0, pathValue);
			}

			// }

		}
		private RequestMapping findRequestMapping(Class<?> clz)
		{
			RequestMapping m = null;

			m = (RequestMapping)AnnotatedElementUtils.findMergedAnnotation(clz, RequestMapping.class);
			if( m != null ) {
				return m;
			}
			Class<?> ifs[] = clz.getInterfaces();
			if( ifs.length > 0) {
				for (int i = ifs.length - 1; i > 0; i++) {
					m = findRequestMapping(ifs[i]);
					if (m != null) {
						return m;
					}
				}
			}
			return null;
		}

		private String resolve(String value) {
			return StringUtils.hasText(value) && resourceLoader instanceof ConfigurableApplicationContext ? ((ConfigurableApplicationContext)this.resourceLoader).getEnvironment().resolvePlaceholders(value) : value;
		}



		private class ParameterizedTypeImpl implements ParameterizedType {

			private final Type[] actualTypeArguments;
			private final Type   ownerType;
			private final Type   rawType;

			public ParameterizedTypeImpl(Type[] actualTypeArguments, Type ownerType, Type rawType){
				this.actualTypeArguments = actualTypeArguments;
				this.ownerType = ownerType;
				this.rawType = rawType;
			}

			public Type[] getActualTypeArguments() {
				return actualTypeArguments;
			}

			public Type getOwnerType() {
				return ownerType;
			}

			public Type getRawType() {
				return rawType;
			}


			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;

				ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;

				// Probably incorrect - comparing Object[] arrays with Arrays.equals
				if (!Arrays.equals(actualTypeArguments, that.actualTypeArguments)) return false;
				if (ownerType != null ? !ownerType.equals(that.ownerType) : that.ownerType != null) return false;
				return rawType != null ? rawType.equals(that.rawType) : that.rawType == null;

			}

			@Override
			public int hashCode() {
				int result = actualTypeArguments != null ? Arrays.hashCode(actualTypeArguments) : 0;
				result = 31 * result + (ownerType != null ? ownerType.hashCode() : 0);
				result = 31 * result + (rawType != null ? rawType.hashCode() : 0);
				return result;
			}
		}
	}
}
