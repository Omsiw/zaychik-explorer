package com.zaychik.backend.player.entity;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.status.model.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
public class Player implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private DataFormatReaders.Match match;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    public Player(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


    @Deprecated
    protected Player(){
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
