package co.edu.javeriana.as.personapp.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
import co.edu.javeriana.as.personapp.model.response.EstudiosResponse;

@Mapper
public class EstudiosMapperRest {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public EstudiosResponse fromDomainToAdapterRestMaria(Study study) {
		return fromDomainToAdapterRest(study, "MariaDB");
	}
	
	public EstudiosResponse fromDomainToAdapterRestMongo(Study study) {
		return fromDomainToAdapterRest(study, "MongoDB");
	}
	
	public EstudiosResponse fromDomainToAdapterRest(Study study, String database) {
		return new EstudiosResponse(
				study.getProfession() != null ? study.getProfession().getIdentification() + "" : "", 
				study.getPerson() != null ? study.getPerson().getIdentification() + "" : "", 
				study.getGraduationDate() != null ? study.getGraduationDate().format(DATE_FORMATTER) : "", 
				study.getUniversityName() != null ? study.getUniversityName() : "", 
				database,
				"OK");
	}

	public Study fromAdapterToDomain(EstudiosRequest request) {
		Study study = new Study();
		
		if (request.getIdProf() != null && !request.getIdProf().isEmpty()) {
			Profession profession = new Profession();
			profession.setIdentification(Integer.parseInt(request.getIdProf()));
			study.setProfession(profession);
		}
		
		if (request.getCcPer() != null && !request.getCcPer().isEmpty()) {
			Person person = new Person();
			person.setIdentification(Integer.parseInt(request.getCcPer()));
			study.setPerson(person);
		}
		
		if (request.getFecha() != null && !request.getFecha().isEmpty()) {
			study.setGraduationDate(LocalDate.parse(request.getFecha(), DATE_FORMATTER));
		}
		
		study.setUniversityName(request.getUniver());
		
		return study;
	}
}
