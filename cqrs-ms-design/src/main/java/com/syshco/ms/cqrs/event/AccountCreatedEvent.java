package com.syshco.ms.cqrs.event;

import java.math.BigDecimal;

public record AccountCreatedEvent(String accountId, BigDecimal balance) {}