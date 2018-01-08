package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.constant.PageMode;

import javax.validation.Valid;
import java.util.List;

@Setter
@Getter
public abstract class AbstractBean<T> {
    
	@Valid
	private T entity;
	private List<T> lstData;
	private PageMode pageMode;
	private String action;
	private List<T> data;
	
}
