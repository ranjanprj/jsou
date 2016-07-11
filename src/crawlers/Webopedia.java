package crawlers;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Webopedia
 *
 */
@Entity
public class Webopedia implements Serializable {

	private String Name;
	private static final long serialVersionUID = 1L;
	@Id
	private long techID;

	public long getTechID() {
		return techID;
	}

	public void setTechID(long techID) {
		this.techID = techID;
	}

	public Webopedia() {
		super();
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

}
