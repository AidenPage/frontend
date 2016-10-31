package com.computerstore.frontend.factories.peripherals;


import com.computerstore.frontend.domain.peripherals.Printer;

/**
 * Created by Aiden on 2016/10/23.
 */
public class PrinterFactory
{
    public static Printer getPrinter(String name, String price)
    {
        Printer printer = new Printer.Builder()
                .name(name)
                .price(price)
                .build();
        return printer;
    }
}
