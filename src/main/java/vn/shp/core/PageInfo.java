package vn.shp.core;

/**
 * PageInfo This class use for function back page
 * 
 */
public class PageInfo {

	private String url;

	public PageInfo(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = true;

		if (!(obj instanceof PageInfo)) {
			result = false;
		} else {
			PageInfo page = (PageInfo) obj;
			result = page.getUrl().equals(url);
		}
		return result;
	}
}
