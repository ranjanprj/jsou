package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Skill
 *
 */
@Entity

public class Skill implements Serializable {

	public enum SKILL_LEVEL{
		BEGINNER,INTERMEDIATE,ADVANCE,EXPERT
	}
	
	public enum SKILL_TYPE{
		PROGRAMMING,BUSINESS,MANAGEMENT,CONCEPT
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private static final long serialVersionUID = 1L;

	private String skillName;
	
	@Enumerated
	private SKILL_LEVEL skillLevel;

	@Enumerated
	private SKILL_TYPE skillType;

	public Skill() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public SKILL_LEVEL getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(SKILL_LEVEL skillLevel) {
		this.skillLevel = skillLevel;
	}

	public SKILL_TYPE getSkillType() {
		return skillType;
	}

	public void setSkillType(SKILL_TYPE skillType) {
		this.skillType = skillType;
	}
   
	
}
