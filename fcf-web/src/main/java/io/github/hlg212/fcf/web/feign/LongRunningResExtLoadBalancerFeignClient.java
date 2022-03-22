package  io.github.hlg212.fcf.web.feign;

import  io.github.hlg212.fcf.service.LongRunningResService;
import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

import java.io.IOException;

/**
 * ribbon 长耗时自定义超时
 * @author huangligui
 * @date 2020年4月28日
 */
@Slf4j
public class LongRunningResExtLoadBalancerFeignClient extends LoadBalancerFeignClient {

	@Autowired
	private LongRunningResService longRunningResService;

	public LongRunningResExtLoadBalancerFeignClient(Client delegate, CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory) {
		super(delegate, lbClientFactory, clientFactory);
	}

	@Override
	public Response execute(Request request, Options options) throws IOException {
		String url = request.url();
		url = url.replace("http://","").replace("https://","");
		String uri = url.substring(url.indexOf("/"));
		Integer timeout = longRunningResService.getUriTimeout(uri);
		if( timeout != null && timeout > 0 )
		{
			//Options(int connectTimeoutMillis, int readTimeoutMillis) {
			options = new Options(options.connectTimeoutMillis(),timeout,options.isFollowRedirects());

		}
		return super.execute(request, options);
	}


}
