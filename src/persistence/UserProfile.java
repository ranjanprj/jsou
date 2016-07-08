package persistence;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: UserProfile
 *
 */
@Entity
public class UserProfile implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String userName,currentSkill,futureSkill;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String city,state,country;
	
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
	public String getCurrentSkill() {
		return currentSkill;
	}
	public void setCurrentSkill(String currentSkill) {
		this.currentSkill = currentSkill;
	}
	public String getFutureSkill() {
		return futureSkill;
	}
	public void setFutureSkill(String futureSkill) {
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
   
}
