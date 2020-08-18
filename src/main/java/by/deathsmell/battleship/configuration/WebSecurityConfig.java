package by.deathsmell.battleship.configuration;

import by.deathsmell.battleship.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserManager userManager;

    @Autowired
    public WebSecurityConfig(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Create custom web security http configuration");

        http.
                csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/login**","/registration**","/error**")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                        .permitAll()
                .and()
                .logout()
                    .permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userManager)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}
