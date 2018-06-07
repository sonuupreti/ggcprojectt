package com.gspann.itrack.domain.service.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.gspann.itrack.domain.model.common.dto.AccountDTO;
import com.gspann.itrack.domain.model.common.dto.AddAccountPageVM;




public interface AccountManagementService {
	
    /**
     * Save a account.
     *
     * @param accountDTO the entity to save
     * @return the persisted entity
     */
	//public AccountDTO addAccount(final String customerName, final String customerEntity, final String customerReportingManager, String accountManagerCode, final int cityId, final boolean customerTimeTracking);

	public AccountDTO addAccount(AccountDTO accountDTO);

	
    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AccountDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accounts.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AccountDTO findOne(String id);

    /**
     * Delete the "id" accounts.
     *
     * @param id the id of the entity
     */
    void delete(String id);

	public AccountDTO updateAccount(@Valid AccountDTO accountDTO);


	public AddAccountPageVM getAddAccountPageVM();


	public AddAccountPageVM getEditAccountVM(String accountCode);
	

}
