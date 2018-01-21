package dmz.insurance.rest;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

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
	
	@RequestMapping(method = RequestMethod.GET, value = "/dobaviStarosneGrupe")
	public ResponseEntity<?> dobaviStarosneGrupe() {
		ResponseEntity<?> response = rt.getForEntity("https://" + this.corpUrl + "/corpMain/dobaviStarosneGrupe", String.class);
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

	@RequestMapping(method = RequestMethod.POST, value = "/buyInsurance")
	public ResponseEntity<?> buyInsurance(@RequestBody OsiguranjeDTO osiguranjeDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OsiguranjeDTO> request = new HttpEntity<>(osiguranjeDTO);
		
		ResponseEntity<BuyPolicyDTO> response = rt.postForEntity( "https://" + this.paymentDMZUrl +
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