package dmz.insurance.rest;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dmz.insurance.DTO.OsiguranjeDTO;
import dmz.insurance.bean.BuyPolicyDTO;

@RestController
@CrossOrigin
@RequestMapping("/dmzMain")
public class MainController {

	@Value("${corp.url}")
	private String corpUrl;
	
	@Value("${paymentDMZ.url}")
	private String paymentDMZUrl; 
	
	@Value("${error.origin.name}")
	private String errorOriginName; 
	
	private RestTemplate rt = new RestTemplate();
	
	@PostConstruct
	public void init() {
		logger.info("TEST!!!");
	}

	private final Log logger = LogFactory.getLog(this.getClass());
	
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

	@RequestMapping(method = RequestMethod.POST, value = "/buyInsurance")
	public ResponseEntity<?> buyInsurance(@RequestBody OsiguranjeDTO osiguranjeDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OsiguranjeDTO> request = new HttpEntity<>(osiguranjeDTO);
		
		ResponseEntity<BuyPolicyDTO> response = rt.postForEntity( "http://" + this.paymentDMZUrl +
				"/paymentDMZMain/buyPolicy", request , BuyPolicyDTO.class );
		
		System.out.println(response.getBody());
		return response;	
		
	}
	
	//rest template ne dobije 200ok
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> exceptionHandlerHttpError(HttpClientErrorException ex) {
		String body = ex.getResponseBodyAsString();
		RestClientExceptionInfo info = new RestClientExceptionInfo(); 
		
		
		if(RestClientExceptionInfo.parse(body) == null) {
			//ova aplikacija je uzrok exceptiona
			//priprema se exception za propagiranje dalje i loguje se
			info.setOrigin(errorOriginName);
			info.setInfo(body);
		}
		else {
			info.setOrigin(RestClientExceptionInfo.parse(body).getOrigin() );
			info.setInfo(RestClientExceptionInfo.parse(body).getInfo() );
		}
		logger.error("HttpClientErrorException, info:" + RestClientExceptionInfo.toJSON(info));
		
		
		return ResponseEntity.status(ex.getStatusCode()).body(RestClientExceptionInfo.toJSON(info));
	}	
}