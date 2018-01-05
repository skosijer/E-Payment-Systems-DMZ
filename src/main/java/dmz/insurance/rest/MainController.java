package dmz.insurance.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
	public ResponseEntity<?> dobaviRegione() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = rt.getForEntity("http://" + this.corpUrl + "/corpMain/dobaviRegione", String.class);
		return response;
	}
}
