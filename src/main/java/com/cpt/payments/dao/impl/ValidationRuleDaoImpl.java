package com.cpt.payments.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.ValidationRuleDao;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ValidationRuleDaoImpl implements ValidationRuleDao {


	private NamedParameterJdbcTemplate jdbcTemplate;

	public ValidationRuleDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<String> loadActiveValidatorNames() {
		log.info("Loading active validator name");
		
		String sql = "SELECT validatorName " +
				"FROM validations.validation_rules " +
				"WHERE isActive = TRUE " +
				"ORDER BY priority ASC";

		List<String> activeValidatorRules= jdbcTemplate.queryForList(sql, new java.util.HashMap<>(), String.class);
		
		log.info("Active validator names: {}", activeValidatorRules);
		
		return activeValidatorRules;
	}

}
