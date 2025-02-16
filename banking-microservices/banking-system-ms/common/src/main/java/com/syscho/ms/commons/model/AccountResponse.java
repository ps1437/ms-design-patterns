package com.syscho.ms.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record AccountResponse(
        @JsonIgnore
        Long id,
        String accountNumber,
        String accountHolderName,
        double balance) {}
