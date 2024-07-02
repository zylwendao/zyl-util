package mvTech.enumDemo.enums;

public enum DataSourceEnum implements ValueLabelEnum<String, String> {
    MOBILE_DPI("1", "移动网统一DPI"),
    CITY_DPI("2", "城域网统一DPI"),
    WORM_SYSTEM("3", "僵木蠕系统"),
    MALWARE_SYSTEM("4", "恶意程序系统单选或多选");

    private final String value;
    private final String label;

    DataSourceEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }
}