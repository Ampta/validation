package com.cpt.payments.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

	@Override
	public Map<String, String> loadValidatorRuleParams(String validatorRuleName) {
		String sql = "SELECT paramName, paramValue FROM validation_rules_params WHERE validatorName = :validatorName";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("validatorName", validatorRuleName);

        return jdbcTemplate.query(sql, params, rs -> {
            Map<String, String> resultMap = new java.util.HashMap<>();
            while (rs.next()) {
                resultMap.put(rs.getString("paramName"), rs.getString("paramValue"));
            }
            return resultMap;
        });
	}

}
