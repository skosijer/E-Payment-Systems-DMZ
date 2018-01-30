package dmz.insurance.rest;

import javax.annotation.PostConstruct;

import dmz.insurance.DTO.CompletePaymentDTO;
import dmz.insurance.DTO.PolisaDTO;
import dmz.insurance.bean.BuyPolicyDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private RestTemplate rt;
	
	@PostConstruct
	public void init(){
	}

	private final Log logger = LogFactory.getLog(this.getClass());

	@PostMapping(value = "/polisa")
	public ResponseEntity<?> postPolisa(@RequestBody PolisaDTO polisa) {
		ResponseEntity<?> response = rt.postForEntity("https://" + this.corpUrl + "/corpMain/polisa", polisa, String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviStarosneGrupe")
	public ResponseEntity<?> dobaviStarosneGrupe() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviStarosneGrupe", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getOsiguranjaDoIznosa")
	public ResponseEntity<?> dobaviOsiguranjaDoIznosa() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/getOsiguranjaDoIznosa", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dobaviRegione")
	public ResponseEntity<?> dobaviDestinacije() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviRegione", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviSvrheOsiguranja")
	public ResponseEntity<?> dobaviSvrheOsiguranja() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviSvrheOsiguranja", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviPaketeOsiguranja")
	public ResponseEntity<?> dobaviPaketeOsiguranja() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviPaketeOsiguranja", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviStarostiStana")
	public ResponseEntity<?> dobaviStarostiStana() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviStarostiStana", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviProcenjeneVrednostiStana")
	public ResponseEntity<?> dobaviProcenjeneVrednostiStana() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviProcenjeneVrednostiStana", String.class);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviOsiguranjaStana")
	public ResponseEntity<?> dobaviOsiguranjaStana() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviOsiguranjaStana", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSlepovanje")
	public ResponseEntity<?> dobaviSlepovanje() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/getSlepovanje", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getPopravka")
	public ResponseEntity<?> dobaviPopravku() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/getPopravka", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSmestaj")
	public ResponseEntity<?> dobaviSmestaj() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/getSmestaj", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getPrevoz")
	public ResponseEntity<?> dobaviPrevoz() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/getPrevoz", String.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getPovrsina")
	public ResponseEntity<?> dobaviPovrsinu() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/getPovrsina", String.class);
		return response;
	}

	//OVDE KO JE RADIO NEKA POGLEDA NOVE vrsta_placanja I URADI SA NJIMA
	@RequestMapping(method = RequestMethod.POST, value = "/buyInsurance")
	public ResponseEntity<?> buyInsurance(@RequestBody PolisaDTO polisaDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PolisaDTO> request = new HttpEntity<>(polisaDTO);

		ResponseEntity<BuyPolicyDTO> response = rt.postForEntity( "https://" + this.paymentDMZUrl +
				"/paymentDMZMain/buyPolicy", request , BuyPolicyDTO.class );

		System.out.println(response.getBody());
		return response;

	}
	
	//OVDE KO JE RADIO NEKA POGLEDA NOVE vrsta_placanja I URADI SA NJIMA
		@RequestMapping(method = RequestMethod.POST, value = "/completePayment")
		public ResponseEntity<?> completePayment(@RequestBody CompletePaymentDTO dto) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			dto.transactionIdMerchant = dto.orderId; 

			HttpEntity<CompletePaymentDTO> request = new HttpEntity<>(dto);

			ResponseEntity<String> response = rt.postForEntity( "https://" + this.paymentDMZUrl +
					"/paymentDMZMain/completePaymentResponse", request , String.class );

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