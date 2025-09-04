package com.invoo.invoice.invoice;

import com.invoo.invoice.invoice.provider.IProviderDate;

import java.time.LocalDateTime;

public class ProviderDate implements IProviderDate {

    public LocalDateTime date;

    @Override
    public LocalDateTime generateCurrentDate() {
        return date;
    }
}
