package ey.org.model;

// Generated 2014-8-28 10:39:03 by Hibernate Tools 4.0.0

/**
 * Org generated by hbm2java
 */
public class Org implements java.io.Serializable {

	private String id;
	private String orgname;
	private String shortname;
	private String iseffect;
	private String type;
	private Integer sort;
	private String code;
	private String pid;
	private String orgwholename;

	public Org() {
	}

	public Org(String id) {
		this.id = id;
	}

	public Org(String id, String orgname, String shortname, String iseffect,
			String type, Integer sort, String code, String pid,
			String orgwholename) {
		this.id = id;
		this.orgname = orgname;
		this.shortname = shortname;
		this.iseffect = iseffect;
		this.type = type;
		this.sort = sort;
		this.code = code;
		this.pid = pid;
		this.orgwholename = orgwholename;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getIseffect() {
		return this.iseffect;
	}

	public void setIseffect(String iseffect) {
		this.iseffect = iseffect;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrgwholename() {
		return this.orgwholename;
	}

	public void setOrgwholename(String orgwholename) {
		this.orgwholename = orgwholename;
	}

}