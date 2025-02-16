package com.syshco.ms.cqrs.event;

import java.math.BigDecimal;

public record MoneyDepositedEvent(String accountId, BigDecimal amount) {}
