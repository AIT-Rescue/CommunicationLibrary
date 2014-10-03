package comlib.util;

import java.util.HashMap;
import java.util.Map;

public class IntegerDataHelper
{
    private Map<String, int[]> dataMap;

    private int useBitFlag;

    private Boolean canRegister;

    //instance////////////////////////////////////////////////////////////////////////////

    public IntegerDataHelper() {
        this.dataMap = new HashMap<>();
        this.useBitFlag = 0;
        this.canRegister = Boolean.TRUE;
    }

    public IntegerDataHelper(int initialCapacity) {
        this.dataMap = new HashMap<>(initialCapacity);
        this.useBitFlag = 0;
        this.canRegister = Boolean.TRUE;
    }

    public IntegerDataHelper(int initialCapacity, float loadFactor) {
        this.dataMap = new HashMap<>(initialCapacity, loadFactor);
        this.useBitFlag = 0;
        this.canRegister = Boolean.TRUE;
    }

    //method//////////////////////////////////////////////////////////////////////////////

    public boolean registerData(String name, int start, int size) {
        if(this.exist(name))
            return false;
        if(!this.canRegisterData(start, size))
            return false;

        int filter = getFilter(start, size);
        this.dataMap.put(name, new int[]{start, filter});
        this.setUseBit(filter);
        return true;
    }

    public boolean registerFlag(String name, int start) {
        return this.registerData(name, start, 1);
    }

    public int setData(int data, String name, int input) {
        int[] datas = this.dataMap.get(name);
        if(datas == null) return data;
        return setByFilter(data, datas[0], datas[1], input);
    }

    public int setFlag(int data, String name, boolean input) {
        return input ? this.setData(data, name, 1) : this.setData(data, name, 0);
    }

    public Integer getData(int data, String name) {
        int[] datas = this.dataMap.get(name);
        if(datas == null) return null;
        return getByFilter(data, datas[0], datas[1]);
    }

    public Boolean getFlag(int data, String name) {
        Integer flag = this.getData(data, name);
        if(flag == null) return null;
        return flag != 0;
    }

    public boolean canRegisterData(int start, int size) {
        return this.canRegister && !(start < 0 || size <= 0 || (start + size - 1) > 31) && ((this.useBitFlag & getFilter(start, size)) == 0);
    }

    private void setUseBit(int filter) {
        this.useBitFlag = this.useBitFlag | filter;
        if(this.useBitFlag == 0xFFFFFFFF)
            this.canRegister = Boolean.FALSE;
    }

    public boolean exist(String name) {
        return this.dataMap.get(name) != null;
    }

    //static method///////////////////////////////////////////////////////////////////////

    public static int getByFilter(int data, int start, int filter) {
        return (data & filter) >> start;
    }

    public static int get(int data, int start, int size) {
        return (data >> start) & ((0x01 << size) - 1);
    }

    public static int setByFilter(int data, int start, int filter, int input) {
        input = (input << start) & filter;
        data = data & ~filter;
        return data | input;
    }

    public static int set(int data, int start, int size, int input) {
        return setByFilter(data, start, getFilter(start, size), input);
    }

    public static int removeByFilter(int data, int filter) {
        return data & ~filter;
    }

    public static int remove(int data, int start, int size) {
        return removeByFilter(data, getFilter(start, size));
    }

    public static int getFilter(int start, int size) {
        return ((0x01 << size) - 1) << start;
    }

    /*public static int getBitSize(int value) {
        for(int i = 31; i > 0; i--)
            if(((value >> i) & 0x01) == 1)
                return i + 1;
        return 1;
    }*/

    /*public static int getBitSize(int value) {
        for(int i = (value >> 16) != 0 ? 31 : 15; i > 0; i--)
            if(((value >> i) & 0x01) == 1)
                return i + 1;
        return 1;
    }*/

    public static int getBitSize(int value) {
        int i = (value >> 16) != 0 ? (value >> 24) != 0 ? 31 : 23 : (value >> 8) != 0 ? 15 : 7;
        do {
            if (((value >> i) & 0x01) == 1)
                return i + 1;
            i--;
        } while (i > 0);
        return 1;
    }
}