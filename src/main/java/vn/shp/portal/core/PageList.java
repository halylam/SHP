package vn.shp.portal.core;

import java.util.Stack;

/**
 * PageList This class use for function back page
 * 
 */
public class PageList {

	/** Pages */
	private Stack<PageInfo> pageHistoryLst;

	/** Current pages */
	private PageInfo currentPage;

	/**
	 * Constructor default
	 */
	public PageList() {
		pageHistoryLst = new Stack<>();
	}

	/**
	 * Add new page that is different previous page
	 * 
	 * @param page
	 *            type PageInfo
	 */
	public void push(PageInfo page) {
		if (currentPage == null) {
			// In case
			currentPage = page;
		} else {
			if (!currentPage.equals(page)) {
				// Set current page
				pageHistoryLst.push(currentPage);

				currentPage = page;
			}
		}
	}

	/**
	 * Pop pageInfo from pages
	 * 
	 */
	public PageInfo pop() {
		PageInfo pageInfo = null;

		if (!pageHistoryLst.isEmpty()) {
			// Get previous page
			pageInfo = pageHistoryLst.pop();
			// Set currentPage
			currentPage = pageInfo;
		}

		return pageInfo;
	}

	/**
	 * Reset pages
	 * 
	 */
	public void reset() {
		pageHistoryLst = new Stack<>();
		currentPage = null;
	}

	/**
	 * Check pages is empty
	 * 
	 */
	public boolean isEmpty() {
		return pageHistoryLst.isEmpty();
	}

	/**
	 * Get size pages
	 * 
	 */
	public int size() {
		int size = 0;

		if (pageHistoryLst != null && !pageHistoryLst.isEmpty()) {
			size = pageHistoryLst.size();
		}
		return size;
	}
}
