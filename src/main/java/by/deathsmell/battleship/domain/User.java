package by.deathsmell.battleship.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "username cannot be empty")
    private String username;

//    private String password;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-mm-yyyy")
    private LocalDateTime timeCreate;


    public enum UserRole implements GrantedAuthority {
        ROLE_USER, ROLE_ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return "123";
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static UserBuilder withUsername(String username) {
        return builder().username(username);
    }

//    public static UserBuilder withPassword(String password) {
//        return builder().password(password);
//    }

    public static UserBuilder withRole(UserRole role) {
        return builder().role(role);
    }

    public UserBuilder withRoles(Set<UserRole> roles) {
        return builder().roles(roles);
    }

    public static class UserBuilder {

        private Long id;
        private String username;
        //        private String password;
        private Set<UserRole> roles;
        private LocalDateTime timeCreate;


        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

//        public UserBuilder password(String password) {
//            this.password = password;
//            return this;
//        }

        public UserBuilder roles(Set<UserRole> roles) {
            if (this.roles != null) {
                this.roles.addAll(roles);
            } else if (roles == null) {
                roles = new HashSet<>();
                roles.add(UserRole.ROLE_USER);
            }
            this.roles = roles;
            return this;
        }

        public UserBuilder role(UserRole role) {
            if (this.roles == null) {
                this.roles = new HashSet<>();
            } else if (role == null) {
                role = UserRole.ROLE_USER;
            }
            this.roles.add(role);
            return this;
        }

        public UserBuilder time(LocalDateTime time) {
            this.timeCreate = time;
            return this;
        }

        public User build() {
            return new User(id, username, roles, timeCreate);
        }
    }
}
