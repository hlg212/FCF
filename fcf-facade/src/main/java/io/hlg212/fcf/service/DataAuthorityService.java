package io.hlg212.fcf.service;

import java.util.List;

public interface DataAuthorityService<E> {


    List<E> getAuthoritys(String uid, String param);
}
