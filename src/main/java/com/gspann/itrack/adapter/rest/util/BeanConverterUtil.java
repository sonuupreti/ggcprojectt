package com.gspann.itrack.adapter.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.dto.AccountDTO;

public class BeanConverterUtil {

	private BeanConverterUtil() {
	}

	private static final Logger log = LoggerFactory.getLogger(BeanConverterUtil.class);

	public static Account accountDTOtoEntity(AccountDTO accountDTO) {

		
		
		return null;
	}

	public static AccountDTO accountEntitytoDto(Account account) {

		AccountDTO accountDTO=new AccountDTO();
		
		accountDTO.setAccountCode(account.code());
		accountDTO.setCustomerName(account.customerName());
		accountDTO.setCustomerEntity(account.customerEntity());
		accountDTO.setCustomerReportingManager(account.customerReportingManager());
		accountDTO.setCustomerTimeTracking(account.customerTimeTracking());
		accountDTO.setLocation(account.location().name());
		accountDTO.setAccountManagerCode(account.accountManager().code());
		accountDTO.setAccountManagerName(account.accountManager().name());
		return accountDTO;
	}

}
