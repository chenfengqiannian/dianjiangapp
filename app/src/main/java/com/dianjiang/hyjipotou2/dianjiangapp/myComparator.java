package com.dianjiang.hyjipotou2.dianjiangapp;

import java.util.Comparator;

/**
 * Created by hyjipotou2 on 16/4/21.
 */
public class myComparator implements Comparator {

    public static int PRICE_SHENGXU=0;
    public static int PRICE_JIANGXU=1;
    public static int LEVEL_SHENGXU=2;
    public static int LEVEL_JIANGXU=3;
    public static int PINGJIA_SHENGXU=4;
    public static int PINGJIA_JIANGXU=5;

    private int param;

    public myComparator(int param){
        this.param=param;
    }

    @Override
    public int compare(Object lhs, Object rhs) {

        int flag;

        if (param == LEVEL_JIANGXU) {
            dianjiangItemBean dianjiangItemBean1 = (dianjiangItemBean) lhs;
            dianjiangItemBean dianjiangItemBean0 = (dianjiangItemBean) rhs;
            flag = dianjiangItemBean0.level.compareTo(dianjiangItemBean1.level);
            return flag;
        } else if (param == LEVEL_SHENGXU) {
            dianjiangItemBean dianjiangItemBean0 = (dianjiangItemBean) lhs;
            dianjiangItemBean dianjiangItemBean1 = (dianjiangItemBean) rhs;
            flag = dianjiangItemBean0.level.compareTo(dianjiangItemBean1.level);
            return flag;
        } else if (param == PRICE_JIANGXU) {
            dianjiangItemBean dianjiangItemBean1 = (dianjiangItemBean) lhs;
            dianjiangItemBean dianjiangItemBean0 = (dianjiangItemBean) rhs;
            flag = dianjiangItemBean0.price.compareTo(dianjiangItemBean1.price);
            return flag;
        } else if (param == PRICE_SHENGXU) {
            dianjiangItemBean dianjiangItemBean0 = (dianjiangItemBean) lhs;
            dianjiangItemBean dianjiangItemBean1 = (dianjiangItemBean) rhs;
            flag = dianjiangItemBean0.price.compareTo(dianjiangItemBean1.price);
            return flag;
        }else if (param==PINGJIA_JIANGXU){
            dianjiangItemBean dianjiangItemBean1 = (dianjiangItemBean) lhs;
            dianjiangItemBean dianjiangItemBean0 = (dianjiangItemBean) rhs;
            flag = dianjiangItemBean0.pingjia.compareTo(dianjiangItemBean1.pingjia);
            return flag;
        }else {
            dianjiangItemBean dianjiangItemBean0 = (dianjiangItemBean) lhs;
            dianjiangItemBean dianjiangItemBean1 = (dianjiangItemBean) rhs;
            flag = dianjiangItemBean0.pingjia.compareTo(dianjiangItemBean1.pingjia);
            return flag;
        }

    }
}
