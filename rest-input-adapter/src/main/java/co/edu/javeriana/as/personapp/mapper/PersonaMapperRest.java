package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;

@Mapper
public class PersonaMapperRest {
	
	public PersonaResponse fromDomainToAdapterRestMaria(Person person) {
		return fromDomainToAdapterRest(person, "MariaDB");
	}
	public PersonaResponse fromDomainToAdapterRestMongo(Person person) {
		return fromDomainToAdapterRest(person, "MongoDB");
	}
	
	public PersonaResponse fromDomainToAdapterRest(Person person, String database) {
		return new PersonaResponse(
				person.getIdentification()+"", 
				person.getFirstName(), 
				person.getLastName(), 
				person.getAge()+"", 
				person.getGender().toString(), 
				database,
				"OK");
	}

	public Person fromAdapterToDomain(PersonaRequest request) {
		Person person = new Person();
		person.setIdentification(Integer.parseInt(request.getDni()));
		person.setFirstName(request.getFirstName());
		person.setLastName(request.getLastName());
		person.setAge(request.getAge() != null && !request.getAge().isEmpty() ? Integer.parseInt(request.getAge()) : null);
		person.setGender(validateGender(request.getSex()));
		return person;
	}
	
	private co.edu.javeriana.as.personapp.domain.Gender validateGender(String sex) {
		if (sex == null || sex.isEmpty()) {
			return co.edu.javeriana.as.personapp.domain.Gender.OTHER;
		}
		switch (sex.toUpperCase()) {
			case "M":
			case "MALE":
				return co.edu.javeriana.as.personapp.domain.Gender.MALE;
			case "F":
			case "FEMALE":
				return co.edu.javeriana.as.personapp.domain.Gender.FEMALE;
			default:
				return co.edu.javeriana.as.personapp.domain.Gender.OTHER;
		}
	}
		
}
