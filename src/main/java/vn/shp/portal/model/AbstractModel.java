package vn.shp.portal.model;

import lombok.Getter;
import lombok.Setter;
import vn.shp.portal.common.PageMode;

import javax.validation.Valid;
import java.util.List;

@Setter
@Getter
public abstract class AbstractModel<T> {
    
	@Valid
	private T entity;
	private List<T> data;
	private PageMode pageMode;
	
}
