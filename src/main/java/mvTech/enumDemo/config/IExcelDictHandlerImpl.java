package mvTech.enumDemo.config;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import mvTech.enumDemo.enums.DataSourceEnum;
import mvTech.enumDemo.enums.ValueLabelEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IExcelDictHandlerImpl implements IExcelDictHandler {

    private Map<String, Class<? extends Enum<?>>> dictEnumMap;

    public IExcelDictHandlerImpl() {
        dictEnumMap = new HashMap<>();
        // 请根据实际情况替换枚举类和字典名
        dictEnumMap.put("dataSourceDict", DataSourceEnum.class);
    }

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        Class<?> enumClass = dictEnumMap.get(dict);
        if (enumClass != null) {
            for (Object enumConstant : enumClass.getEnumConstants()) {
                ValueLabelEnum vle = (ValueLabelEnum) enumConstant;
                if (vle.getValue().equals(value)) {
                    return (String) vle.getLabel();
                } else if (vle.getLabel().equals(value)) {
                    return (String) vle.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        Class<?> enumClass = dictEnumMap.get(dict);
        if (enumClass != null) {
            for (Object enumConstant : enumClass.getEnumConstants()) {
                ValueLabelEnum vle = (ValueLabelEnum) enumConstant;
                if (vle.getLabel().equals(value)) {
                    return (String) vle.getValue();
                }
            }
        }
        return null;
    }
}
