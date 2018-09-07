package et.tsingtaopad.login.domain;

import java.io.Serializable;
import java.util.Date;

public class BsVisitEmpolyeeStc implements Serializable {
	private String userid;
	private String username;
	private String gender;
	private String idcode;
	private String tel;
	private String mobile;
	private String province;
	private String city;
	private String county;
	private String towns;
	private String address;
	private String userengname;
	private String faxno;
	private String birthday;
	private String photoname;
	private String photopath;
	private String email;
	private String status;
	private String positionid;
	private String departmentid;
	private String ischeck;
	private String enddate;
	private String leavereason;
	private String lockreason;
	private String lockdate;
	private String unlockdate;
	private String remarks;
	private String deleteflag;
	private Integer version;
	private Date credate;
	private String creuser;
	private Date updatetime;
	private String updateuser;
	private String gridId;
	private String gridName;
	private String loginDate;
	private String pDiscs;
	private String isDel;
	private String bfgl;
	private String yxgl;
	private String xtgl;
	private String isrepassword;

	public BsVisitEmpolyeeStc() {
	}

	public BsVisitEmpolyeeStc(String userid, String username,
                              String departmentid, String gridId, String gridName,
                              String loginDate) {
		this.userid = userid;
		this.username = username;
		this.departmentid = departmentid;
		this.gridId = gridId;
		this.gridName = gridName;
		this.loginDate = loginDate;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGridId() {
		return this.gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getGridName() {
		return this.gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdcode() {
		return this.idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTowns() {
		return this.towns;
	}

	public void setTowns(String towns) {
		this.towns = towns;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserengname() {
		return this.userengname;
	}

	public void setUserengname(String userengname) {
		this.userengname = userengname;
	}

	public String getFaxno() {
		return this.faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhotoname() {
		return this.photoname;
	}

	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public String getPhotopath() {
		return this.photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPositionid() {
		return this.positionid;
	}

	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}

	public String getDepartmentid() {
		return this.departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getLeavereason() {
		return this.leavereason;
	}

	public void setLeavereason(String leavereason) {
		this.leavereason = leavereason;
	}

	public String getLockreason() {
		return this.lockreason;
	}

	public void setLockreason(String lockreason) {
		this.lockreason = lockreason;
	}

	public String getLockdate() {
		return this.lockdate;
	}

	public void setLockdate(String lockdate) {
		this.lockdate = lockdate;
	}

	public String getUnlockdate() {
		return this.unlockdate;
	}

	public void setUnlockdate(String unlockdate) {
		this.unlockdate = unlockdate;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeleteflag() {
		return this.deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCredate() {
		return this.credate;
	}

	public void setCredate(Date credate) {
		this.credate = credate;
	}

	public String getCreuser() {
		return this.creuser;
	}

	public void setCreuser(String creuser) {
		this.creuser = creuser;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginDate() {
		return this.loginDate;
	}

	public void setpDiscs(String pDiscs) {
		this.pDiscs = pDiscs;
	}

	public String getpDiscs() {
		return this.pDiscs;
	}
	
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
	 * @return the bfgl
	 */
	public String getBfgl() {
		return bfgl;
	}

	/**
	 * @param bfgl the bfgl to set
	 */
	public void setBfgl(String bfgl) {
		this.bfgl = bfgl;
	}

	/**
	 * @return the yxgl
	 */
	public String getYxgl() {
		return yxgl;
	}

	/**
	 * @param yxgl the yxgl to set
	 */
	public void setYxgl(String yxgl) {
		this.yxgl = yxgl;
	}

	/**
	 * @return the xtgl
	 */
	public String getXtgl() {
		return xtgl;
	}

	/**
	 * @param xtgl the xtgl to set
	 */
	public void setXtgl(String xtgl) {
		this.xtgl = xtgl;
	}

	/**
	 * @return the isrepassword
	 */
	public String getIsrepassword() {
		return isrepassword;
	}

	/**
	 * @param isrepassword the isrepassword to set
	 */
	public void setIsrepassword(String isrepassword) {
		this.isrepassword = isrepassword;
	}
	
}