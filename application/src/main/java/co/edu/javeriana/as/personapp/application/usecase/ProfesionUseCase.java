package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class ProfesionUseCase implements ProfesionInputPort {

	private ProfesionOutputPort profesionPersintence;
	
	public ProfesionUseCase(@Qualifier("profesionOutputAdapterMaria") ProfesionOutputPort profesionPersintence) {
		this.profesionPersintence = profesionPersintence;
	}
	
	@Override
	public void setPersintence(ProfesionOutputPort profesionPersintence) {
		this.profesionPersintence = profesionPersintence;
	}

	@Override
	public Profession create(Profession profession) {
		log.debug("Into create on Application Domain");
		return profesionPersintence.save(profession);
	}

	@Override
	public Profession edit(Integer identification, Profession profession) throws NoExistException {
		Profession oldProfession = profesionPersintence.findById(identification);
		if (oldProfession != null)
			return profesionPersintence.save(profession);
		throw new NoExistException(
				"The profession with id " + identification + " does not exist into db, cannot be edited");
	}

	@Override
	public Boolean drop(Integer identification) throws NoExistException {
		Profession oldProfession = profesionPersintence.findById(identification);
		if (oldProfession != null)
			return profesionPersintence.delete(identification);
		throw new NoExistException(
				"The profession with id " + identification + " does not exist into db, cannot be dropped");
	}

	@Override
	public List<Profession> findAll() {
		log.info("Output: " + profesionPersintence.getClass());
		return profesionPersintence.find();
	}

	@Override
	public Profession findOne(Integer identification) throws NoExistException {
		Profession oldProfession = profesionPersintence.findById(identification);
		if (oldProfession != null)
			return oldProfession;
		throw new NoExistException("The profession with id " + identification + " does not exist into db, cannot be found");
	}

	@Override
	public Integer count() {
		return findAll().size();
	}

	@Override
	public List<Study> getStudies(Integer identification) throws NoExistException {
		Profession oldProfession = profesionPersintence.findById(identification);
		if (oldProfession != null)
			return oldProfession.getStudies();
		throw new NoExistException(
				"The profession with id " + identification + " does not exist into db, cannot be getting studies");
	}
}
