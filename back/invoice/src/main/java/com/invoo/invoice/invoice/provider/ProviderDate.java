package com.invoo.invoice.invoice.provider;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProviderDate implements IProviderDate {
    @Override
    public LocalDateTime generateCurrentDate() {
        return LocalDateTime.now();
    }
}
