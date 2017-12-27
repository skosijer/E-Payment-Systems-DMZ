package dmz.insurance.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin
@RequestMapping("/dmz-insurance-forms")
public class MainController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        RestTemplate rt =  new RestTemplate();
        ResponseEntity<String> re = rt.getForEntity("http://localhost:8081/corp-insurance-forms/regions/getAll", String.class);
        return re.getBody();
    }


}
