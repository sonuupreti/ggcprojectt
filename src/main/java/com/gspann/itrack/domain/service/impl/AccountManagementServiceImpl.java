package com.gspann.itrack.domain.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gspann.itrack.adapter.persistence.repository.AccountRepository;
import com.gspann.itrack.adapter.persistence.repository.LocationRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.adapter.rest.util.BeanConverterUtil;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.dto.AccountDTO;
import com.gspann.itrack.domain.model.common.dto.AddAccountPageVM;

import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Location;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.service.api.AccountManagementService;

/**
 * Service Implementation for managing Accounts.
 */
@Service
@Transactional
public class AccountManagementServiceImpl implements AccountManagementService {

	private final Logger log = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	// private final AccountsMapper accountsMapper;

	/**
	 * Save a account.
	 *
	 * @param accountDTO
	 *            the entity to save
	 * @return the persisted entity
	 * 
	 * @Override public AccountDTO addAccount(final String customerName, final
	 *           String customerEntity, final String customerReportingManager,
	 *           String accountManagerCode, final int cityId, final boolean
	 *           customerTimeTracking) { // log.debug("Request to save Accounts :
	 *           {}", accountDTO);
	 * 
	 *           Resource accountManager =
	 *           resourceRepository.getOne(accountManagerCode);
	 * 
	 *           City location = locationRepository.loadCity(cityId);
	 * 
	 *           Account account=
	 *           accountRepository.save(Account.ofCustomer(customerName).reportingTo(customerReportingManager).
	 *           withEntity(customerEntity).isCustomerTimeTrackingAvailable(customerTimeTracking).
	 *           managedBy(accountManager).locatedAt(location).build());
	 * 
	 *           return BeanConverterUtil.accountEntitytoDto(account);
	 * 
	 * 
	 *           }
	 */
	
	
	/**
	 * Save a account.
	 *
	 * @param accountDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	
	public AccountDTO addAccount(AccountDTO accountDTO) {
		// log.debug("Request to save Accounts : {}", accountDTO);

		Resource accountManager = resourceRepository.getOne(accountDTO.getAccountManagerCode());

		City location = locationRepository.loadCity(accountDTO.getCityId());

		Account account = accountRepository.save(Account.ofCustomer(accountDTO.getCustomerName())
				.reportingTo(accountDTO.getCustomerReportingManager()).withEntity(accountDTO.getCustomerEntity())
				.isCustomerTimeTrackingAvailable(accountDTO.getCustomerTimeTracking()).managedBy(accountManager)
				.locatedAt(location).build());

		return BeanConverterUtil.accountEntitytoDto(account);

	}

	/**
	 * Get all the accounts.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<AccountDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Accounts");
		return accountRepository.findAll(pageable).map(BeanConverterUtil::accountEntitytoDto);
	}

	/**
	 * Get one accounts by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public AccountDTO findOne(String id) {
		log.debug("Request to get Accounts : {}", id);
		Optional<Account> account = accountRepository.findById(id);
		return BeanConverterUtil.accountEntitytoDto(account.get());
	}

	/**
	 * Delete the accounts by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete Accounts : {}", id);
		accountRepository.deleteById(id);
	}

	@Override
	public AccountDTO updateAccount(@Valid AccountDTO accountDTO) {

		Resource accountManager = resourceRepository.getOne(accountDTO.getAccountManagerCode());

		City location = locationRepository.loadCity(accountDTO.getCityId());

		Optional<Account> account = accountRepository.findById(accountDTO.getAccountCode());

		Account account1 = account.get();
		account1.updateCustomerEntity(accountDTO.getCustomerEntity());
		account1.updateCustomerName(accountDTO.getCustomerName());
		account1.updatecustomerReportingManager(accountDTO.getCustomerReportingManager());
		account1.updatecustomerTimeTracking(accountDTO.getCustomerTimeTracking());
		account1.assignAccountManager(accountManager);
		account1.updateLocation(location);

		return BeanConverterUtil.accountEntitytoDto(account1);

	}

	@Override
	public AddAccountPageVM getAddAccountPageVM() {

		List<Location> locations = locationRepository.findAllLocations();
		Collections.sort(locations);
		List<Pair<Integer, String>> locationPairs = new LinkedList<>();
		for (Location location : locations) {
			Pair<Integer, String> loc = new Pair<Integer, String>(location.cityId(), location.format());
			locationPairs.add(loc);
		}
		List<Pair<String, String>> resourceList = resourceRepository.findAllCodeAndName();
		return AddAccountPageVM.of(resourceList, locationPairs);

	}

	@Override
	public AddAccountPageVM getEditAccountVM(String accountCode) {
		
		
		Optional<Account> account = accountRepository.findById(accountCode);
		Account accountObj=account.get();
		String countryCode = accountObj.location().state().country().code();
		
		List<Location> locations = locationRepository.findAllLocationsByCountryCode(countryCode);
		Collections.sort(locations);
		List<Pair<Integer, String>> locationPairs = new LinkedList<>();
		for (Location location : locations) {
			Pair<Integer, String> loc = new Pair<Integer, String>(location.cityId(), location.format());
			locationPairs.add(loc);
		}
		List<Pair<String, String>> resourceList = resourceRepository.findAllCodeAndName();
		return AddAccountPageVM.of(resourceList, locationPairs);
	}

}
