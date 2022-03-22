package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

public interface IFile extends ISerializable {
    public String getId();
    public void setId(String id);

    public String getAppId();
    public void setAppId(String appId);

    public String getPath();
    public void setPath(String path);

    public String getBcode();
    public void setBcode(String bcode);

    public String getFileName();
    public void setFileName(String fileName);

    public String getContentType();
    public void setContentType(String contentType);

    public Long getSize();
    public void setSize(Long size);

    public byte[] getContent();
    public void setContent(byte[] content);

}
