package com.writerHub.practice.Controller;

import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.Company;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.CompanyService;
import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private WriterHubUserService writerHubUserService;

    @GetMapping("/companies")
    public ResponseEntity<?> getAllCompanies(){
        List<Company> companyList = companyService.getAllCompanies();
        return  ResponseEntity.ok().body(companyList);
    }

    @PostMapping("/company")
    public ResponseEntity<?> saveCompany(HttpServletRequest request, @RequestBody Company company){
        AuthenticationError error = new AuthenticationError();
        ResponseEntity<?> response;
        try {
            WriterHubUser user = writerHubUserService.getUser(request.getAttribute("username").toString());
            user.setCompany(company);
            WriterHubUser updatedUser = writerHubUserService.saveUser(user);
            response = new ResponseEntity<>(updatedUser.getCompany(), HttpStatus.CREATED);
        }
        catch (IllegalArgumentException exception){
            error.setStatusCode(HttpStatus.BAD_REQUEST);
            error.setMessage(exception.getMessage());
            response = new ResponseEntity<>(error,error.getStatusCode());
        }
        catch (Exception exception){
            error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            error.setMessage(exception.getMessage());
            response = new ResponseEntity<>(error,error.getStatusCode());
        }
        return response;
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id){
        Company company = companyService.getCompany(id);
        return ResponseEntity.ok().body(company);
    }

    @GetMapping("/company/{id}/authors")
    public ResponseEntity<?> getCompanyAuthors(@PathVariable Long id){
        Company company = companyService.getCompany(id);
        return ResponseEntity.ok().body(company.getAuthors());
    }
}
