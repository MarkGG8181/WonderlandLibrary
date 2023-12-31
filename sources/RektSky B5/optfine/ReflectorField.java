/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.lang.reflect.Field;
import optfine.Config;
import optfine.Reflector;
import optfine.ReflectorClass;

public class ReflectorField {
    private ReflectorClass reflectorClass = null;
    private String targetFieldName = null;
    private boolean checked = false;
    private Field targetField = null;

    public ReflectorField(ReflectorClass p_i58_1_, String p_i58_2_) {
        this.reflectorClass = p_i58_1_;
        this.targetFieldName = p_i58_2_;
        Field field = this.getTargetField();
    }

    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }
        this.checked = true;
        Class oclass = this.reflectorClass.getTargetClass();
        if (oclass == null) {
            return null;
        }
        try {
            this.targetField = oclass.getDeclaredField(this.targetFieldName);
            if (!this.targetField.isAccessible()) {
                this.targetField.setAccessible(true);
            }
        }
        catch (SecurityException securityexception) {
            securityexception.printStackTrace();
        }
        catch (NoSuchFieldException var4) {
            Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
        }
        return this.targetField;
    }

    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }

    public void setValue(Object p_setValue_1_) {
        Reflector.setFieldValue(null, this, p_setValue_1_);
    }

    public boolean exists() {
        return this.checked ? this.targetField != null : this.getTargetField() != null;
    }
}

