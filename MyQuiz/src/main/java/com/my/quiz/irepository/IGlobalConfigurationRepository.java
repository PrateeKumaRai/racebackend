package com.my.quiz.irepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.my.quiz.domain.GlobalConfiguration;

@Repository
public interface IGlobalConfigurationRepository extends CrudRepository<GlobalConfiguration, Long>{

	GlobalConfiguration findByConfigName(String configName);

}
