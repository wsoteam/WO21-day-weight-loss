package onepic.bkcom.com.twentyonetrening;

import onepic.bkcom.com.twentyonetrening.POJOs.GlobalObject;

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

