package com.github.demo.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OauthRefreshToken {
    @Id
    @Column(length = 250)
    private String tokenId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "token", columnDefinition = "BLOB", nullable = true)
    private String token;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "authentication", columnDefinition = "BLOB", nullable = true)
    private String authentication;
}
