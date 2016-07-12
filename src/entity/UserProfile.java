package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: UserProfile
 *
 */
@Entity
public class UserProfile implements Serializable {
	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String userName;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String city,state,country;
	private String currentPostion,futurePostion;
	
	@OneToMany
	private List<Skill> currentSkill,futureSkill;
		
	private static final long serialVersionUID = 1L;

	public UserProfile() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<Skill> getCurrentSkill() {
		return currentSkill;
	}
	public void setCurrentSkill(List<Skill> currentSkill) {
		this.currentSkill = currentSkill;
	}
	public List<Skill> getFutureSkill() {
		return futureSkill;
	}
	public void setFutureSkill(List<Skill> futureSkill) {
		this.futureSkill = futureSkill;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrentPostion() {
		return currentPostion;
	}
	public void setCurrentPostion(String currentPostion) {
		this.currentPostion = currentPostion;
	}
	public String getFuturePostion() {
		return futurePostion;
	}
	public void setFuturePostion(String futurePostion) {
		this.futurePostion = futurePostion;
	}
   
}
