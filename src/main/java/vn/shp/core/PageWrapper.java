package vn.shp.core;

import java.util.ArrayList;
import java.util.List;

/**
 * PageWrapper This class use for paging
 * 
 */
public class PageWrapper<T> {
	/** Show max page */
	private int maxPage = 5;
	/** Size element T of page */
	private int sizeOfPage = 2;
	/** Current page */
	private int currentPage;
	/** List page */
	private List<PageItem> items;
	/** Total pages */
	private int totalPages;
	/** List element T of page */
	private List<T> data;
	/** Show start page */
	private int start;

	public PageWrapper(int currentPage, int sizeOfPage) {
		if (currentPage == 0) {
			currentPage = 1;
		}

		this.currentPage = currentPage;
		this.sizeOfPage = sizeOfPage;
	}

	public void setDataAndCount(List<T> data, int countAll) {
		items = new ArrayList<PageItem>();

		this.data = data;

		this.totalPages = countAll / this.sizeOfPage;

		int residuals = countAll % this.sizeOfPage;
		if (residuals > 0) {
			this.totalPages = this.totalPages + 1;
		}

		if (this.currentPage < this.maxPage) {
			if (this.currentPage % this.maxPage > 0) {
				this.start = this.currentPage / this.maxPage + 1;
			} else {
				this.start = this.currentPage / this.maxPage;
			}
		} else {
			int offset = 0;
			if (this.currentPage % this.maxPage > 0) {
				offset = this.currentPage / this.maxPage + 1;
			} else {
				offset = this.currentPage / this.maxPage;
			}
			this.start = (offset - 1) * maxPage + 1;
		}
	}

	public List<PageItem> getItems() {
		int showPages = this.totalPages - this.start >= this.maxPage ? this.maxPage : this.totalPages - this.start + 1;
		for (int i = 0; i < showPages; i++) {
			this.items.add(new PageItem(this.start + i, this.start + i == this.currentPage));
		}
		return this.items;
	}

	public int getNextMaxPage() {
		// current page temp
		int currentPage = this.start + this.maxPage;
		return currentPage <= this.totalPages ? currentPage : -1;
	}

	public int getPreMaxPage() {
		// current page temp
		int currentPage = this.start - this.maxPage;
		return currentPage > 0 ? currentPage : -1;
	}
	
	public int getNextPage() {
		return this.currentPage + 1;
	}

	public int getPrePage() {
		return this.currentPage - 1;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getSizeOfPage() {
		return sizeOfPage;
	}

	public void setSizeOfPage(int sizeOfPage) {
		this.sizeOfPage = sizeOfPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setItems(List<PageItem> items) {
		this.items = items;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartIndexCurrent() {
		return (currentPage - 1) * sizeOfPage + 1;
	}
}