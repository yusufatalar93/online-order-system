package com.yusuf.online.order.system.core.entity;


import com.yusuf.online.order.system.core.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User  extends BaseEntity<String> implements UserDetails {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Integer id;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "BUSINESS_NAME")
  private String businessName;

  @Column(name = "ADDRESS" ,columnDefinition = "TEXT")
  private String address;
  @Enumerated(EnumType.STRING)
  @Column(name = "USER_TYPE")
  private UserType userType;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
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
