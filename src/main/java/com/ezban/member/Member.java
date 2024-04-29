package com.ezban.member;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_no", nullable = false)
	private Integer memberNo;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "member_mail", nullable = false, length = 50)
	private String memeberMail;
	
	@NotNull
	@Size(max = 20)
	@Column(name = "member_pwd", nullable = false, length = 20)
	private String memberPwd;
	
	@Size(max = 50)
	@Column(name = "member_name", length = 50)
	private String memberName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "gender")
	private Integer gender;
	
	@Size(max = 15)
	@Column(name = "member_phone", length = 15)
	private String memberPhone;
	
	@Size(max = 200)
	@Column(name = "address", length = 200)
	private String address;
	
	@Size(max = 50)
	@Column(name = "common_recipient", length = 50)
	private String commonRecipient;
	
	@Size(max = 15)
	@Column(name = "common_recipient_phone", length = 15)
	private String commonRecipientPhone;
	
	@Size(max = 200)
	@Column(name = "common_recipient_address", length = 200)
	private String commonRecipientAddress;
	
	@Column(name = "member_status")
	private Byte memberStatus;
	
	@Column(name = "member_points")
	private Integer memberPoints;
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(Integer memberNo, String memeberMail, String memberPwd, String memberName, Date birthday,
			Integer gender, String memberPhone, String address, String commonRecipient, String commonRecipientPhone,
			String commonRecipientAddress, Byte memberStatus, Integer memberPoints) {
		super();
		this.memberNo = memberNo;
		this.memeberMail = memeberMail;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.birthday = birthday;
		this.gender = gender;
		this.memberPhone = memberPhone;
		this.address = address;
		this.commonRecipient = commonRecipient;
		this.commonRecipientPhone = commonRecipientPhone;
		this.commonRecipientAddress = commonRecipientAddress;
		this.memberStatus = memberStatus;
		this.memberPoints = memberPoints;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemeberMail() {
		return memeberMail;
	}

	public void setMemeberMail(String memeberMail) {
		this.memeberMail = memeberMail;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommonRecipient() {
		return commonRecipient;
	}

	public void setCommonRecipient(String commonRecipient) {
		this.commonRecipient = commonRecipient;
	}

	public String getCommonRecipientPhone() {
		return commonRecipientPhone;
	}

	public void setCommonRecipientPhone(String commonRecipientPhone) {
		this.commonRecipientPhone = commonRecipientPhone;
	}

	public String getCommonRecipientAddress() {
		return commonRecipientAddress;
	}

	public void setCommonRecipientAddress(String commonRecipientAddress) {
		this.commonRecipientAddress = commonRecipientAddress;
	}

	public Byte getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(Byte memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Integer getMemberPoints() {
		return memberPoints;
	}

	public void setMemberPoints(Integer memberPoints) {
		this.memberPoints = memberPoints;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memeberMail=" + memeberMail + ", memberPwd=" + memberPwd
				+ ", memberName=" + memberName + ", birthday=" + birthday + ", gender=" + gender + ", memberPhone="
				+ memberPhone + ", address=" + address + ", commonRecipient=" + commonRecipient
				+ ", commonRecipientPhone=" + commonRecipientPhone + ", commonRecipientAddress="
				+ commonRecipientAddress + ", memberStatus=" + memberStatus + ", memberPoints=" + memberPoints + "]";
	}
	
	

}

