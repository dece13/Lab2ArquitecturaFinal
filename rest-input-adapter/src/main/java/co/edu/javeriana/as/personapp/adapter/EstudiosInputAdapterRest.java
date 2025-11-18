package co.edu.javeriana.as.personapp.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.StudyUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mapper.EstudiosMapperRest;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
import co.edu.javeriana.as.personapp.model.response.EstudiosResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class EstudiosInputAdapterRest {

	@Autowired
	@Qualifier("studyOutputAdapterMaria")
	private StudyOutputPort studyOutputPortMaria;

	@Autowired
	@Qualifier("studyOutputAdapterMongo")
	private StudyOutputPort studyOutputPortMongo;

	@Autowired
	private EstudiosMapperRest estudiosMapperRest;

	StudyInputPort studyInputPort;

	private String setStudyOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			studyInputPort = new StudyUseCase(studyOutputPortMaria);
			return DatabaseOption.MARIA.toString();
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			studyInputPort = new StudyUseCase(studyOutputPortMongo);
			return DatabaseOption.MONGO.toString();
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public List<EstudiosResponse> historial(String database) {
		log.info("Into historial EstudiosEntity in Input Adapter");
		try {
			if (setStudyOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return studyInputPort.findAll().stream().map(estudiosMapperRest::fromDomainToAdapterRestMaria)
						.collect(Collectors.toList());
			} else {
				return studyInputPort.findAll().stream().map(estudiosMapperRest::fromDomainToAdapterRestMongo)
						.collect(Collectors.toList());
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new ArrayList<EstudiosResponse>();
		}
	}

	public EstudiosResponse crearEstudio(EstudiosRequest request) {
		try {
			String database = setStudyOutputPortInjection(request.getDatabase());
			Study study = studyInputPort.create(estudiosMapperRest.fromAdapterToDomain(request));
			if (database.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return estudiosMapperRest.fromDomainToAdapterRestMaria(study);
			} else {
				return estudiosMapperRest.fromDomainToAdapterRestMongo(study);
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	public EstudiosResponse buscarPorId(String database, Integer idProf, Integer ccPer) {
		log.info("Into buscarPorId EstudiosEntity in Input Adapter");
		try {
			String db = setStudyOutputPortInjection(database);
			Study study = studyInputPort.findOne(idProf, ccPer);
			if (study == null) {
				return null;
			}
			if (db.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return estudiosMapperRest.fromDomainToAdapterRestMaria(study);
			} else {
				return estudiosMapperRest.fromDomainToAdapterRestMongo(study);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		}
	}

	public EstudiosResponse actualizarEstudio(String database, Integer idProf, Integer ccPer, EstudiosRequest request) {
		log.info("Into actualizarEstudio EstudiosEntity in Input Adapter");
		try {
			String db = setStudyOutputPortInjection(database);
			Study study = estudiosMapperRest.fromAdapterToDomain(request);
			Study updatedStudy = studyInputPort.edit(idProf, ccPer, study);
			if (db.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return estudiosMapperRest.fromDomainToAdapterRestMaria(updatedStudy);
			} else {
				return estudiosMapperRest.fromDomainToAdapterRestMongo(updatedStudy);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		}
	}

	public Boolean eliminarEstudio(String database, Integer idProf, Integer ccPer) {
		log.info("Into eliminarEstudio EstudiosEntity in Input Adapter");
		try {
			setStudyOutputPortInjection(database);
			return studyInputPort.drop(idProf, ccPer);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}
	}

}
