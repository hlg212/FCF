package  io.github.hlg212.fcf.model.dam;

public interface IDataAuthorityPropertyCondition {


    public static final String TYPE_DYNAMIC = "dynamic";
    public static final String TYPE_STATIC = "static";

    public String getCode();

    public String getPropertyName();
    public String getOperation();
    public IDataAuthorityPropertyConditionValue getValue();
    public String getType();

    public Boolean getIsQuery();
    public Boolean getIsDelete();
    public Boolean getIsUpdate();
    public Boolean getIsAdd();

}
