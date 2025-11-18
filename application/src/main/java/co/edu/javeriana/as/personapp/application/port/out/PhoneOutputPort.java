package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;

@Port
public interface PhoneOutputPort {
	public Phone save(Phone phone) throws NoExistException;
	public Boolean delete(String number) throws NoExistException;
	public List<Phone> find();
	public Phone findById(String number) throws NoExistException;
}
