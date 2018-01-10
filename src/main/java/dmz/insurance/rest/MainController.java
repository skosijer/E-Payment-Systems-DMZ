package dmz.insurance.rest;

import dmz.insurance.DTO.OsiguranjeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/dmzMain")
public class MainController {

	@Value("${corp.url}")
	private String corpUrl;

	private RestTemplate rt = new RestTemplate();

	@RequestMapping(method = RequestMethod.GET, value = "/dobaviStarosneGrupe")
	public ResponseEntity<?> dobaviStarosneGrupe() {
		ResponseEntity<?> response = rt.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviStarosneGrupe", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dobaviRegione")
	public ResponseEntity<?> dobaviDestinacije() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviRegione", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviSvrheOsiguranja")
	public ResponseEntity<?> dobaviSvrheOsiguranja() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviSvrheOsiguranja", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviPaketeOsiguranja")
	public ResponseEntity<?> dobaviPaketeOsiguranja() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviPaketeOsiguranja", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviStarostiStana")
	public ResponseEntity<?> dobaviStarostiStana() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviStarostiStana", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviProcenjeneVrednostiStana")
	public ResponseEntity<?> dobaviProcenjeneVrednostiStana() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviProcenjeneVrednostiStana", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviOsiguranjaStana")
	public ResponseEntity<?> dobaviOsiguranjaStana() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviOsiguranjaStana", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveInsurance")
	public ResponseEntity<?> saveInsurance(@RequestBody OsiguranjeDTO osiguranjeDTO) {
		System.out.println(osiguranjeDTO);
		return null;
	}
}

