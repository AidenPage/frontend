package com.computerstore.frontend.factories.peripherals;


import com.computerstore.frontend.domain.peripherals.Mouse;

/**
 * Created by Aiden on 2016/10/23.
 */
public class MouseFactory
{
    public static Mouse getMouse(String name, String price)
    {
        Mouse mouse = new Mouse.Builder()
                .name(name)
                .price(price)
                .build();
        return mouse;
    }
}
