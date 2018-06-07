package com.gspann.itrack.adapter.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gspann.itrack.adapter.rest.error.BadRequestAlertException;
import com.gspann.itrack.adapter.rest.util.HeaderUtil;
import com.gspann.itrack.adapter.rest.util.PaginationUtil;
import com.gspann.itrack.domain.model.common.dto.AccountDTO;
import com.gspann.itrack.domain.model.common.dto.AddAccountPageVM;

import com.gspann.itrack.domain.service.api.AccountManagementService;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Accounts.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class AccountsResource {
	


//    private final Logger log = LoggerFactory.getLogger(AccountsResource.class);

    private static final String ENTITY_NAME = "account";

    private final AccountManagementService accountsManagementService;

    public AccountsResource(AccountManagementService accountsManagementService) {
        this.accountsManagementService = accountsManagementService;
    }

    
    @GetMapping("/initAddAccount")
    @Timed
    public AddAccountPageVM initAddAccountPage(final Principal principal) {
        log.debug("REST request to getAddAccountPageVM() ------>>>");
		//String manojResCode = "20001";
        return accountsManagementService.getAddAccountPageVM();
        
        
        
    }
    @GetMapping("/initEditAccount/{id}")
    @Timed
    public AddAccountPageVM initEditAccountPage(final Principal principal,@PathVariable String id) {
        log.debug("REST request to getEditAccountVM() ------>>>");
		//String manojResCode = "20001";
        return accountsManagementService.getEditAccountVM(id);
        
        
        
    }
    
    
    /**
     * POST  /accounts : Create a new accounts.
     *
     * @param accountsDTO the accountsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountsDTO, or with status 400 (Bad Request) if the accounts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accounts")
    @Timed
    public ResponseEntity<AccountDTO> createAccounts(@Valid @RequestBody AccountDTO accountDTO) throws URISyntaxException {
        log.debug("REST request to save Accounts : {}", accountDTO);
        if (accountDTO.getAccountCode() != null) {
            throw new BadRequestAlertException("A new accounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountDTO accountDTO1 = accountsManagementService.addAccount(accountDTO);
       
        
        return ResponseEntity.created(new URI("/api/accounts/" + accountDTO1.getAccountCode()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, accountDTO1.getAccountCode()))
            .body(accountDTO1);
    }

    /**
     * PUT  /accounts : Updates an existing accounts.
     *
     * @param accountsDTO the accountsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountsDTO,
     * or with status 400 (Bad Request) if the accountsDTO is not valid,
     * or with status 500 (Internal Server Error) if the accountsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accounts")
    @Timed
    public ResponseEntity<AccountDTO> updateAccounts(@Valid @RequestBody AccountDTO accountDTO) throws URISyntaxException {
        log.debug("REST request to update Accounts : {}", accountDTO);
        if (accountDTO.getAccountCode() == null) {
            return createAccounts(accountDTO);
        }
      // AccountDTO accountDTO1 = accountsManagementService.addAccount(accountDTO.getCustomerName(),accountDTO.getCustomerEntity(), accountDTO.getCustomerReportingManager(), accountDTO.getAccountManagerCode(), accountDTO.getCityId(),accountDTO.getCustomerTimeTracking());
       AccountDTO accountDTO1=accountsManagementService.updateAccount(accountDTO);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountDTO1.getAccountCode()))
            .body(accountDTO1);
    }

    /**
     * GET  /accounts : get all the accounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accounts in body
     */
    @GetMapping("/accounts")
    @Timed
    public ResponseEntity<List<AccountDTO>> getAllAccounts(Pageable pageable) {
        log.debug("REST request to get a page of Account");
        Page<AccountDTO> page = accountsManagementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /accounts/:id : get the "id" accounts.
     *
     * @param id the id of the accountsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/accounts/{id}")
    @Timed
    public ResponseEntity<AccountDTO> getAccounts(@PathVariable String id) {
        log.debug("REST request to get Accounts : {}", id);
        AccountDTO accountDTO = accountsManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
        
    }

    /**
     * DELETE  /accounts/:id : delete the "id" accounts.
     *
     * @param id the id of the accountsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccounts(@PathVariable String id) {
        log.debug("REST request to delete Accounts : {}", id);
        accountsManagementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }


}
