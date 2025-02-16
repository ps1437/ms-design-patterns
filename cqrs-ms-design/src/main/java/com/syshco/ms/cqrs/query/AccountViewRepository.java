package com.syshco.ms.cqrs.query;

import com.syshco.ms.cqrs.query.model.AccountView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountViewRepository extends JpaRepository<AccountView, String> {}
