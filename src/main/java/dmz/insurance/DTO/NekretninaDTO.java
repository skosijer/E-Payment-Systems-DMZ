package dmz.insurance.DTO;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class NekretninaDTO {
	@NotNull
	public OsobaDTO vlasnik;
	public ArrayList<RizikDTO> rizici;
	
	//kod merchanta proveri id, password duzinu itd
}
