
package Entities;
import Entities.Units.Unit;
import java.lang.reflect.Constructor;

public class UnitFactory {
    public static Entity createEntity(Class<? extends Entity> unitClass, Object... params) {
        try {
            Class<?>[] paramTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Integer) {
                    paramTypes[i] = int.class;
                } else if (params[i] instanceof Double) {
                    paramTypes[i] = double.class;
                } else {
                    paramTypes[i] = params[i].getClass();
                }
            }

            Constructor<? extends Entity> constructor = unitClass.getDeclaredConstructor(paramTypes);
            return constructor.newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
