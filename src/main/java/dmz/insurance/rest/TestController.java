package dmz.insurance.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping(method = RequestMethod.GET, value = "/dobaviKategoriju")
	public ResponseEntity<?> fillInsuranceDatabase() {
		RestTemplate restTemplate = new RestTemplate();
		String dcResourceUrl
		  = "http://localhost:8082/kategorija/dobaviKategoriju";
		ResponseEntity<String> response
		  = restTemplate.getForEntity(dcResourceUrl + "/1", String.class);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
