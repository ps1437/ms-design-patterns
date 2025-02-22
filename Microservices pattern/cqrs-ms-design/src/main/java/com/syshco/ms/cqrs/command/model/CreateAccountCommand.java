package com.syshco.ms.cqrs.command.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;

public record CreateAccountCommand(
        @TargetAggregateIdentifier String accountId,
        BigDecimal initialBalance
) {}