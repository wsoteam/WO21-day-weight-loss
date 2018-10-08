package wk.wildvso.com.twentyonetrening;

import wk.wildvso.com.twentyonetrening.POJOs.GlobalObject;

public class ObjectHolder {
    private static GlobalObject globalObject;


    public void createObjFromGirebase(GlobalObject globalObject){
        if(this.globalObject == null){
            this.globalObject = globalObject;
        }
    }

    public static GlobalObject getGlobalObject(){
        return globalObject;
    }

}

