package co.edu.javeriana.as.personapp.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfesionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.mapper.ProfesionMapperRest;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfesionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class ProfesionInputAdapterRest {

	@Autowired
	@Qualifier("profesionOutputAdapterMaria")
	private ProfesionOutputPort profesionOutputPortMaria;

	@Autowired
	@Qualifier("profesionOutputAdapterMongo")
	private ProfesionOutputPort profesionOutputPortMongo;

	@Autowired
	private ProfesionMapperRest profesionMapperRest;

	ProfesionInputPort profesionInputPort;

	private String setProfesionOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			profesionInputPort = new ProfesionUseCase(profesionOutputPortMaria);
			return DatabaseOption.MARIA.toString();
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			profesionInputPort = new ProfesionUseCase(profesionOutputPortMongo);
			return DatabaseOption.MONGO.toString();
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public List<ProfesionResponse> historial(String database) {
		log.info("Into historial ProfesionEntity in Input Adapter");
		try {
			if (setProfesionOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return profesionInputPort.findAll().stream().map(profesionMapperRest::fromDomainToAdapterRestMaria)
						.collect(Collectors.toList());
			} else {
				return profesionInputPort.findAll().stream().map(profesionMapperRest::fromDomainToAdapterRestMongo)
						.collect(Collectors.toList());
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new ArrayList<ProfesionResponse>();
		}
	}

	public ProfesionResponse crearProfesion(ProfesionRequest request) {
		try {
			String database = setProfesionOutputPortInjection(request.getDatabase());
			Profession profession = profesionInputPort.create(profesionMapperRest.fromAdapterToDomain(request));
			if (database.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return profesionMapperRest.fromDomainToAdapterRestMaria(profession);
			} else {
				return profesionMapperRest.fromDomainToAdapterRestMongo(profession);
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	public ProfesionResponse buscarPorId(String database, Integer id) {
		log.info("Into buscarPorId ProfesionEntity in Input Adapter");
		try {
			String db = setProfesionOutputPortInjection(database);
			Profession profession = profesionInputPort.findOne(id);
			if (profession == null) {
				return null;
			}
			if (db.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return profesionMapperRest.fromDomainToAdapterRestMaria(profession);
			} else {
				return profesionMapperRest.fromDomainToAdapterRestMongo(profession);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		}
	}

	public ProfesionResponse actualizarProfesion(String database, Integer id, ProfesionRequest request) {
		log.info("Into actualizarProfesion ProfesionEntity in Input Adapter");
		try {
			String db = setProfesionOutputPortInjection(database);
			Profession profession = profesionMapperRest.fromAdapterToDomain(request);
			profession.setIdentification(id);
			Profession updatedProfession = profesionInputPort.edit(id, profession);
			if (db.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return profesionMapperRest.fromDomainToAdapterRestMaria(updatedProfession);
			} else {
				return profesionMapperRest.fromDomainToAdapterRestMongo(updatedProfession);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		}
	}

	public Boolean eliminarProfesion(String database, Integer id) {
		log.info("Into eliminarProfesion ProfesionEntity in Input Adapter");
		try {
			setProfesionOutputPortInjection(database);
			return profesionInputPort.drop(id);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}
	}

}
