package com.gspann.itrack.adapter.persistence;

import lombok.Value;

@Value
public class PersistenceConstant {

	public static class Hibernate {
		
		public static final String GLOBAL_SEQ_ID_GENERATOR = "GLOBAL_SEQ_ID_GENERATOR";
		public static final String GLOBAL_SEQ_NAME = "global_sequence";
		public static final String GLOBAL_SEQ_INITIAL_VALUE = "1000";
		public static final String PREFIXED_SEQUENTIAL_CODE_GENERATOR = "PREFIXED_SEQUENTIAL_CODE_GENERATOR";
		public static final String POOLED_TABLE_GENERATOR = "POOLED_TABLE_GENERATOR";
		
		// Change the ID generator here and the same for all entities will change
		public static final String iTrack_ID_GENERATOR = GLOBAL_SEQ_ID_GENERATOR; // OR POOLED_TABLE_GENERATOR

		public static final String TYPE_DEF_JADIRA_MONEY = "jadira_money";
		public static final String TYPE_DEF_JADIRA_YEAR = "jadira_year";
		public static final String TYPE_DEF_JADIRA_DURATION = "jadira_duration";
		public static final String TYPE_DEF_JADIRA_MONTH_OF_YEAR = "jadira_month_of_year";
	}

	public static class TableMetaData {

		public static final String UNQ_RESOURCES_LOGIN_ID = "unq_resources_login_id";
		public static final String UNQ_RESOURCES_EMAIL_ID = "unq_resources_email_id";
		public static final String UNQ_RESOURCES_GREYT_HR_ID = "unq_resources_greyt_hr_id";
		public static final String IDX_RESOURCES_NAME = "idx_resources_name";
		public static final String IDX_RESOURCES_EMAIL_ID = "idx_resources_email_id";
		public static final String IDX_RESOURCES_LOGIN_ID = "idx_resources_login_id";
		public static final String IDX_RESOURCES_GREYT_HR_ID = "idx_resources_greyt_hr_id";

		public static final String PK_RESOURCE_CODE = "pk_resource_code";
		public static final String FK_RESOURCES_DESIGNATION_ID = "fk_resources_designation_id";
		public static final String FK_RESOURCES_EMP_TYPE_CODE = "fk_resources_emp_type_code";
		public static final String FK_RESOURCES_EMP_STATUS_CODE = "fk_resources_emp_status_code";
		public static final String FK_RESOURCES_BASE_LOC_ID = "fk_resources_base_loc_id";
		public static final String FK_RESOURCES_DEPUTED_LOC_ID = "fk_resources_deputed_loc_id";
		public static final String FK_RESOURCES_IMAGE_ID = "fk_resources_image_id";

		public static final String PK_RESOURCE_PRACTICE_MAP = "pk_resource_practice_map";
		public static final String FK_RESOURCE_PRACTICE_MAP_RESOURCE_CODE = "fk_resource_practice_map_resource_code";
		public static final String FK_RESOURCE_PRACTICE_MAP_PRACTICE_CODE = "fk_resource_practice_map_practice_code";

		public static final String PK_RESOURCE_RESUME_MAP = "pk_resource_resume_map";
		public static final String FK_RESOURCE_RESUME_MAP_RESOURCE_CODE = "fk_resource_resume_map_resource_code";
		public static final String FK_RESOURCE_RESUME_MAP_RESUME_ID = "fk_resource_resume_map_resume_id";

		public static final String PK_COST_DETAILS_ID = "pk_cost_details_id";
		public static final String FK_COST_DETAILS_RESOURCE_CODE = "fk_cost_details_resource_code";
		public static final String PK_FTE_COST_DETAILS_ID = "pk_fte_cost_details_id";
		public static final String FK_COST_DETAILS_ID = "fk_cost_details_id";

		public static final String FK_TIME_SHEET_ENTRIES_PROJECT_CODE = "fk_time_sheet_entries_project_code";
		public static final String PK_WEEKLY_TIME_SHEET_ID = "pk_weekly_time_sheet_id";
		public static final String UNQ_WEEKLY_TIME_SHEETS_STATUS_ID = "UNQ_WEEKLY_TIME_SHEETS_STATUS_ID";
		public static final String FK_WEEKLY_TIME_SHEETS_RESOURCE_CODE = "fk_weekly_time_sheets_resource_code";
		public static final String FK_WEEKLY_TIME_SHEETS_STATUS_ID = "fk_weekly_time_sheets_status_id";
		public static final String IDX_WEEKLY_TIME_SHEETS_RESOURCE_CODE = "idx_weekly_time_sheets_resource_code";
		public static final String IDX_WEEKLY_TIME_SHEETS_WEEK_START_DATE = "idx_weekly_time_sheets_week_start_date";
		public static final String IDX_WEEKLY_TIME_SHEETS_WEEK_END_DATE = "idx_weekly_time_sheets_week_end_date";
		public static final String FK_DAILY_TIME_SHEETS_WEEKLY_TIME_SHEET_ID = "fk_daily_time_sheets_weekly_time_sheet_id";
		public static final String FK_WEEKLY_TIME_SHEETS_CLIENT_TIMESHEET_SCREEN_SHOT_ID = "fk_weekly_time_sheets_client_timesheet_screen_shot_id";
		public static final String PK_TIME_SHEET_ENTRY_ID = "pk_time_sheet_entry_id";
		public static final String FK_TIME_SHEET_ENTRIES_DAILY_TIME_SHEET_ID = "fk_time_sheet_entries_daily_time_sheet_id";
		public static final String UNQ_DAILY_TIME_SHEETS_DATE_ENTRY = "unq_daily_time_sheets_date_entry";
		public static final String UNQ_TIME_SHEET_ENTRIES_PROJECT_ENTRY = "unq_time_sheet_entries_project_entry";

		public static final String PK_WEEKLY_TIME_SHEET_STATUS_ID = "pk_weekly_time_sheet_status_id";
		public static final String PK_COMPOSITE_PROJECT_TIME_SHEET_STATUSES = "pk_composite_project_time_sheet_statuses";
		public static final String IDX_PROJECT_TIME_SHEET_STATUSES_PROJECT_CODE = "idx_project_time_sheet_statuses_project_code";
		public static final String FK_PROJECT_TIME_SHEET_STATUSES_PROJECT_CODE = "fk_project_time_sheet_statuses_project_code";
		public static final String FK_PROJECT_TIME_SHEET_STATUSES_WEEKLY_TIME_SHEET_STATUS_ID = "fk_project_time_sheet_statuses_weekly_time_sheet_status_id";
		public static final String FK_PROJECT_TIME_SHEET_STATUS_APPROVER_CODE = "fk_project_time_sheet_status_approver_code";
		
		public static final String PK_PROJECT_CODE = "pk_project_code";
		public static final String UNQ_PROJECTS_NAME = "unq_projects_name";
		public static final String UNQ_PROJECTS_CUSTOMER_PROJECT_ID = "unq_projects_customer_project_id";
		public static final String UNQ_PROJECTS_CUSTOMER_PROJECT_NAME = "unq_projects_customer_project_name";
		public static final String IDX_PROJECT_NAME = "idx_project_name";
		public static final String IDX_CUSTOMER_PROJECT_ID = "idx_customer_project_id";
		public static final String IDX_CUSTOMER_PROJECT_NAME = "idx_customer_project_name";

		public static final String PK_PROJECT_TYPE_CODE = "pk_project_type_code";
		public static final String UNQ_PRJ_TYPE_DESCRIPTION = "unq_prj_type_description";

		public static final String PK_PROJECT_STATUS_CODE = "pk_project_status_code";
		public static final String UNQ_PRJ_STATUS_DESCRIPTION = "unq_prj_status_description";

		public static final String FK_PROJECTS_PRJ_TYPE_CODE = "fk_projects_prj_type_code";
		public static final String FK_PROJECTS_PRJ_STATUS_CODE = "fk_projects_prj_status_code";
		public static final String FK_PROJECTS_ACCOUNT_CODE = "fk_projects_account_code";
		public static final String FK_PROJECTS_OFFSHORE_MGR_CODE = "fk_projects_offshore_mgr_code";
		public static final String FK_PROJECTS_ONSHORE_MGR_CODE = "fk_projects_onshore_mgr_code";
		public static final String FK_PROJECTS_LOC_ID = "fk_projects_loc_id";

		public static final String PK_PROJECT_PRACTICE_MAP = "pk_project_practice_map";
		public static final String FK_PROJECT_PRACTICE_MAP_PROJECT_CODE = "fk_project_practice_map_project_code";
		public static final String FK_PROJECT_PRACTICE_MAP_PRACTICE_CODE = "fk_project_practice_map_practice_code";
		
		

		public static final String PK_COMPANY_ID = "pk_company_id";
		public static final String UNQ_COMP_NAME = "unq_comp_name";

		public static final String PK_COMPOSITE_COMPANY_LOCATION_MAP = "pk_composite_company_location_map";
		public static final String FK_COMPANY_LOCATION_MAP_COMPANY_ID = "fk_company_location_map_company_id";
		public static final String FK_COMPANY_LOCATION_MAP_CITY_ID = "fk_company_location_map_city_id";

		public static final String PK_DEPARTMENT_ID = "pk_department_id";
		public static final String UNQ_DEPT_NAME = "unq_dept_name";
		public static final String FK_DEPARTMENTS_COMPANY_ID = "fk_departments_company_id";

		public static final String PK_DESIGNATION_ID = "pk_designation_id";
		public static final String UNQ_DESIG_NAME = "unq_desig_name";
		public static final String UNQ_DESIG_LEVEL = "unq_desig_level";
		public static final String FK_DESIGNATIONS_DEPARTMENT_ID = "fk_designations_department_id";

		public static final String PK_EMP_TYPE_CODE = "pk_emp_type_code";
		public static final String UNQ_EMP_TYPE_DESCRIPTION = "unq_emp_type_description";

		public static final String PK_EMP_STATUS_CODE = "pk_emp_status_code";
		public static final String UNQ_EMP_STATUS_DESCRIPTION = "unq_emp_status_description";

		public static final String PK_PRACTICE_CODE = "pk_practice_code";
		public static final String UNQ_PRACTICE_NAME = "unq_practice_name";
		public static final String FK_PRACTICES_LEAD_RESOURCE_CODE = "fk_practices_lead_resource_code";

		public static final String PK_HOLIDAY_CALENDAR_YEAR = "pk_holiday_calendar_year";
		public static final String PK_HOLIDAY_ID = "pk_holiday_id";
		public static final String FK_HOLIDAYS_HOLIDAY_CALENDARS_YEAR = "fk_holidays_holiday_calendars_year";

		public static final String PK_OCCASION_ID = "pk_occasion_id";
		public static final String UNQ_OCCASION_NAME = "unq_occasion_name";

		public static final String PK_HOLIDAY_LOCATION_MAP_ID = "pk_holiday_location_map_id";
		public static final String FK_HOLIDAY_LOCATION_MAP_HOLIDAY_ID = "fk_holiday_location_map_holiday_id";
		public static final String FK_HOLIDAY_LOCATION_MAP_LOCATION_ID = "fk_holiday_location_map_location_id";
		public static final String FK_HOLIDAY_LOCATION_MAP_OCCASION_ID = "fk_holiday_location_map_occasion_id";

		public static final String PK_DOCUMENT_ID = "pk_document_id";

		public static final String PK_BILLING_CODE = "pk_billing_code";
		public static final String FK_BILLINGS_BILL_STATUS_CODE = "fk_billings_bill_status_code";
		public static final String PK_BILLING_ID = "pk_billing_id";
		public static final String FK_BILLINGS_ALLOCATION_ID = "fk_billings_allocation_id";
		public static final String UNQ_BILLABILITY_STATUS_DESCRIPTION = "unq_billability_status_description";

		public static final String PK_REBATE_ID = "pk_rebate_id";
		public static final String FK_REBATES_ACCOUNT_CODE = "fk_rebates_account_code";

		public static final String PK_ACCOUNT_CODE = "pk_account_code";
		public static final String UNQ_CUSTOMER_NAME = "unq_customer_name";
		public static final String IDX_CUSTOMER_NAME = "idx_customer_name";
		public static final String UNQ_CUSTOMER_ENTITY = "unq_customer_entity";
		public static final String IDX_CUSTOMER_ENTITY = "idx_customer_entity";
		public static final String FK_ACCOUNTS_MGR_RESOURCE_CODE = "fk_accounts_mgr_resource_code";
		public static final String FK_ACCOUNTS_CITY_ID = "fk_accounts_city_id";
		public static final String PK_ALLOCATION_ID = "pk_allocation_id";
		public static final String FK_ALLOCATIONS_RESOURCE_CODE = "fk_allocations_resource_code";
		public static final String FK_ALLOCATIONS_POJECT_CODE = "fk_allocations_poject_code";

		public static final String PK_COUNTRY_CODE = "pk_country_code";
		public static final String UNQ_COUNTRY_NAME = "unq_country_name";

		public static final String PK_STATE_ID = "pk_state_id";
		public static final String UNQ_STATE = "unq_state";
		public static final String FK_STATES_COUNTRY_CODE = "fk_states_country_code";

		public static final String PK_CITY_ID = "pk_city_id";
		public static final String UNQ_CITY = "unq_city";
		public static final String FK_CITIES_STATE_ID = "fk_cities_state_id";

		public static final String PK_SOW_ID = "pk_sow_id";
		public static final String FK_SOWS_ACCOUNT_CODE = "fk_sows_account_code";
		public static final String UNQ_SOW_NUMBER_ACCOUNT_CODE = "unq_sow_number_account_code";

		public static final String PK_SOW_DOCUMENT_MAP = "pk_sow_document_map";
		public static final String FK_SOW_DOCUMENT_MAP_SOW_ID = "fk_sow_document_map_sow_id";
		public static final String FK_SOW_DOCUMENT_MAP_DOCUMENT_ID = "fk_sow_document_map_document_id";

		public static final String PK_SOW_PROJECT_MAP = "pk_sow_project_map";
		public static final String FK_SOW_PROJECT_MAP_SOW_ID = "fk_sow_project_map_sow_id";
		public static final String FK_SOW_PROJECT_MAP_PROJECT_CODE = "fk_sow_project_map_project_code";

		public static final String PK_SOW_RESOURCE_MAP = "pk_sow_resource_map";
		public static final String FK_SOW_RESOURCE_MAP_SOW_ID = "fk_sow_resource_map_sow_id";
		public static final String FK_SOW_RESOURCE_MAP_RESOURCE_CODE = "fk_sow_resource_map_resource_code";
		public static final String FK_SOWS_PARENT_SOW_ID = "fk_sows_parent_sow_id";

		public static final String PK_SOW_PO_MAP = "pk_sow_po_map";
		public static final String FK_SOW_PO_MAP_SOW_ID = "fk_sow_po_map_sow_id";
		public static final String FK_SOW_PO_MAP_PO_ID = "fk_sow_po_map_po_id";

		public static final String PK_PO_ID = "pk_po_id";
		public static final String IDX_POS_PO_NUMBER = "idx_pos_po_number";

		public static final String PK_PO_DOCUMENT_MAP = "pk_po_document_map";
		public static final String FK_PO_DOCUMENT_MAP_PO_ID = "fk_po_document_map_po_id";
		public static final String FK_PO_DOCUMENT_MAP_DOCUMENT_ID = "fk_po_document_map_document_id";
		public static final String FK_POS_PARENT_PO_ID = "fk_pos_parent_po_id";

		public static final String PK_INVOICE_ID = "pk_invoice_id";
		public static final String FK_INVOICES_PO_ID = "fk_invoices_po_id";


		public static final String PK_TECHNOLOGY_ID = "pk_technology_id";
		public static final String UNQ_TECHNOLOGY_NAME = "unq_technology_name";
		public static final String PK_PROJECT_TECHNOLOGY_MAP = "pk_project_technology_map";
		public static final String FK_PROJECT_TECHNOLOGY_MAP_PROJECT_CODE = "fk_project_technology_map_project_code";
		public static final String FK_PROJECT_TECHNOLOGY_MAP_TECHNOLOGY_ID = "fk_project_technology_map_technology_id";
		
	}
}
