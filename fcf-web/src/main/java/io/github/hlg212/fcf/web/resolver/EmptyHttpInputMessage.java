package io.github.hlg212.fcf.web.resolver;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

/**
 * @author: Administrator
 * @date: 2022/3/29 23:42
 */
public class EmptyHttpInputMessage implements HttpInputMessage {
    private InputStream in = new ByteArrayInputStream("{\"qco\":{}}".getBytes());

    @Override
    public InputStream getBody() throws IOException {
        return in;
    }

    @Override
    public HttpHeaders getHeaders() {
        return null;
    }
}
