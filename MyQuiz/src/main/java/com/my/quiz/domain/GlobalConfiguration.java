package com.my.quiz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GlobalConfiguration {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long configId;
	
    @NotNull(message = "configName should not be null or empty")
	private String configName;
    
    @NotNull(message = "configValue should not be null or empty")
    private String configValue;
}
