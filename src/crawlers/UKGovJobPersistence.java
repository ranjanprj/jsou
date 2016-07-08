package crawlers;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: UKJob
 *
 */
@Entity
public class UKGovJobPersistence implements Serializable {
	private static final long serialVersionUID = 1L;

	public UKGovJobPersistence() {
		super();
	}
	
	@Id
	private long jobId;
	
	@Temporal(TemporalType.DATE)
	private Date postedOn;
	
	private String postedBy,location,position,industries,jobType,salary,jobRefCode,eduLevel;
	
	@Lob
	private String jobDescription,link;

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	


	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getIndustries() {
		return industries;
	}

	public void setIndustries(String industries) {
		this.industries = industries;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJobRefCode() {
		return jobRefCode;
	}

	public void setJobRefCode(String jobRefCode) {
		this.jobRefCode = jobRefCode;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}
	
}
