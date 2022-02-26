package me.cumhax.chipshack.setting;

import me.cumhax.chipshack.module.Module;

import java.util.List;

public class Setting {
    private final String name;
    private final Module module;
    private final SettingType type;
    private boolean booleanValue;
    private boolean boolValue;
    private int integerValue;
    private int minIntegerValue;
    private int maxIntegerValue;
    private int IntValue;
    private String enumValue;
    private List<String> enumValues;

    public Setting(String name, Module module, int intValue, int intMinValue, int intMaxValue) {
        this.name = name;
        this.module = module;
        this.integerValue = intValue;
        this.IntValue = intValue;
        this.minIntegerValue = intMinValue;
        this.maxIntegerValue = intMaxValue;
        this.type = SettingType.INTEGER;
    }

    public Setting(String name, Module module, boolean boolValue) {
        this.name = name;
        this.module = module;
        this.booleanValue = boolValue;
        this.type = SettingType.BOOLEAN;
    }

    public Setting(String name, Module module, List<String> enumValues) {
        this.name = name;
        this.module = module;
        this.enumValue = enumValues.get(0);
        this.enumValues = enumValues;
        this.type = SettingType.ENUM;
    }

    public String getName() {
        return this.name;
    }

    public Module getModule() {
        return this.module;
    }

    public SettingType getType() {
        return this.type;
    }

    public boolean getBooleanValue() {
        return this.booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public int getIntegerValue() {
        return this.integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public int getMinIntegerValue() {
        return this.minIntegerValue;
    }

    public int getIntValue() {
        return this.IntValue;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public int getMaxIntegerValue() {
        return this.maxIntegerValue;
    }

    public String getEnumValue() {
        return this.enumValue;
    }

    public Object getValue() {
        return null;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = this.enumValues.contains(enumValue) ? enumValue : this.enumValue;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }
}
