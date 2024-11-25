package com.cpt.payments.dao;

import java.util.List;

public interface ValidationRuleDao {
	List<String> loadActiveValidatorNames();
}
