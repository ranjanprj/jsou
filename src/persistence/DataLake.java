package persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: DataLake
 *
 */
@Entity

public class DataLake implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long childOfId;
	
	private static final long serialVersionUID = 1L;
	
	@Lob
	private String payload;

	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedOn = Calendar.getInstance().getTime();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastProcessedOn;
	
	@Lob
	private String targetUrl;
	
	private String searchTerm,jobSiteType;
	
	public DataLake() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public Date getInsertedOn() {
		return insertedOn;
	}
	public void setInsertedOn(Date insertedOn) {
		this.insertedOn = insertedOn;
	}
	public Date getLastProcessedOn() {
		return lastProcessedOn;
	}
	public void setLastProcessedOn(Date lastProcessedOn) {
		this.lastProcessedOn = lastProcessedOn;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public long getChildOfId() {
		return childOfId;
	}
	public void setChildOfId(long childOfId) {
		this.childOfId = childOfId;
	}
	public String getJobSiteType() {
		return jobSiteType;
	}
	public void setJobSiteType(String jobSiteType) {
		this.jobSiteType = jobSiteType;
	}
   
	
}
