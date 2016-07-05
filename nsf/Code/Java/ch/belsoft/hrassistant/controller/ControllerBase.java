package ch.belsoft.hrassistant.controller;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import ch.belsoft.hrassistant.config.model.ConfigDefault;
import ch.belsoft.hrassistant.dao.IDataItem;
import ch.belsoft.hrassistant.model.DataItem;
import ch.belsoft.tools.Logging;
import ch.belsoft.tools.XPagesUtil;

public abstract class ControllerBase implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private static final String PARAM_ID = "id";
	protected static String PAGETITLE_NEW = "New Data Item";
	private static String INFO_CREATED = "Created on {CREATED}.";
	private static String INFO_CREATED_MODIFIED = "Created on {CREATED}. Modified on {MODIFIED}";
	private static String INFO_CREATED_REPLACE = "{CREATED}";
	private static String INFO_MODIFIED_REPLACE = "{MODIFIED}";
	protected static String PAGETITLE_EXISTING = "Data Item {NAME}: {DESCRIPTION}";
	protected static final String PAGETITLE_REPLACE_NAME = "{NAME}";
	protected static final String PAGETITLE_REPLACE_DESCRIPTION = "{DESCRIPTION}";

	protected ApplicationController applicationController = null;

	protected boolean newDataItem = false;

	protected String searchQuery = "";

	protected String getId() {
		return XPagesUtil.getQueryString(PARAM_ID);
	}

	protected void handleException(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		XPagesUtil.showErrorMessage(errors.toString());
	}

	public String getCreatedModifiedInfo(IDataItem dataItem) {
		String result = "";
		try {
			String created = XPagesUtil.getDateTimeStringLocalized(dataItem
					.getCreated());

			if (dataItem.getId().equals("")) {
				result = INFO_CREATED.replace(INFO_CREATED_REPLACE, created);
			} else {
				String modified = XPagesUtil
						.getDateTimeStringLocalized(dataItem.getModified());
				result = INFO_CREATED_MODIFIED.replace(INFO_CREATED_REPLACE,
						created);
				result = result.replace(INFO_MODIFIED_REPLACE, modified);
			}

		} catch (Exception e) {
			Logging.logError(e);
		}
		return result;
	}

	public ApplicationController getApplicationController() {
		return applicationController;
	}

	public void setApplicationController(
			ApplicationController applicationController) {
		this.applicationController = applicationController;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	/*
	 * public void postSearch(List<?> searchedDataItems) { try { if
	 * (searchedDataItems == null || searchedDataItems.size() == 0) { XPagesUtil
	 * .showErrorMessage("not items found with search query: " +
	 * this.searchQuery, "pnlSearchInfoNoItemsFound"); } } catch (Exception e) {
	 * Logging.logError(e); } }
	 */

	public int getListCount(List<?> list) {
		int result = 0;
		try {
			if (list != null) {
				result = list.size();
			}
		} catch (Exception e) {
			Logging.logError(e);
		}
		return result;

	}

	public void clearSearchQuery() {
		this.searchQuery = "";
	}

}
