package com.example.springboottest.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long tokenid;
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name="token_id")


	@Column(name="confirmation_token")
	private String confirmationToken;


	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

    @Column(name="user_id")
    private Long userId;
	
//	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "ID")
//    private User user;
	
	public ConfirmationToken() {
	}
	
	public ConfirmationToken(User user) {
		this.userId = user.getId();
		createdDate = new Date();
		confirmationToken = UUID.randomUUID().toString();
	}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public long getTokenid() {
		return tokenid;
	}

	public void setTokenid(long tokenid) {
		this.tokenid = tokenid;
	}
}
