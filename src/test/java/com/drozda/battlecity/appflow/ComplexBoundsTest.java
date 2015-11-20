package com.drozda.battlecity.appflow;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 18.11.2015.
 */
public class ComplexBoundsTest {
    public static void main(String[] args) {
        class Base {
            int h = 10;
        }
        class Inh extends Base {
            int b = 20;
        }

        class BaseListener {
            void handle(Base b) {
                System.out.println("b.h = [" + b.h + "]");
            }
        }
        class InhListener extends BaseListener {

            void handle(Inh b) {
                super.handle(b);
                System.out.println("b.b = [" + b.b + "]");
            }
        }

        List<BaseListener> list = asList(new BaseListener(), new InhListener());

        Inh inh = new Inh();
        for (BaseListener baseListener : list) {
            baseListener.handle(inh);
        }
    }
}
