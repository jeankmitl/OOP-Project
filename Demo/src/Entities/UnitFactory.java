
package Entities;
import Entities.Units.Unit;
import java.lang.reflect.Constructor;

public class UnitFactory {
    public static Unit createUnit(Class<? extends Unit> unitClass, Object... params) {
        try {
            Class<?>[] paramTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = (params[i] instanceof Integer) ? int.class : params[i].getClass();
            }

            Constructor<? extends Unit> constructor = unitClass.getDeclaredConstructor(paramTypes);
            return constructor.newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
