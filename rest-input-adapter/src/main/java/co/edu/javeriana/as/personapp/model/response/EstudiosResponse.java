package co.edu.javeriana.as.personapp.model.response;

import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;

public class EstudiosResponse extends EstudiosRequest {
	
	private String status;
	
	public EstudiosResponse(String idProf, String ccPer, String fecha, String univer, String database, String status) {
		super(idProf, ccPer, fecha, univer, database);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
