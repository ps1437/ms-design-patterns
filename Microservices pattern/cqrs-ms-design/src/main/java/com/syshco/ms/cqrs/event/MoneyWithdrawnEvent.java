package com.syshco.ms.cqrs.event;

import java.math.BigDecimal;

public record MoneyWithdrawnEvent(String accountId, BigDecimal amount) {}
