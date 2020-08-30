package com.github.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OauthAccessToken {
    @Column(length = 256)
    private String tokenId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "token", columnDefinition = "BLOB", nullable = true)
    private String token;

    @Id
    @Column(length = 250)
    private String authenticationId;

    @Column(length = 256)
    private String userName;

    @Column(length = 256)
    private String clientId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "authentication", columnDefinition = "BLOB", nullable = true)
    private String authentication;

    @Column(length = 256)
    private String refreshToken;

}
