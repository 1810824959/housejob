package com.liyang.housejob.config;

import com.liyang.housejob.security.AuthProvider;
import com.liyang.housejob.security.LoginAuthFailHandler;
import com.liyang.housejob.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
//@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        //        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles(":ADMIN");
        auth.authenticationProvider(authProvider()).eraseCredentials(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/logout/**").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/user/login").permitAll()
                //静态资源
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/lib/**").permitAll()
                .antMatchers("/fonts/**").permitAll()

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/api/user/**").hasAnyRole("ADMIN","USER")
               // 开启任何 url 都会进行验证
//                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .failureHandler(loginAuthFailHandler())
                .and()

                .logout()
                .logoutUrl("/logout") // 注销请求的 url
                .logoutSuccessUrl("/logout/page")// 注销成功后跳转的 url
                .deleteCookies("JSESSIONID")// 删除coolies
                .invalidateHttpSession(true)// 让 session 失效
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(loginUrlEntryPoint())
                .accessDeniedPage("/403");




        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

//    @Autowired
//    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles(":ADMIN");
//            auth.authenticationProvider(authProvider()).eraseCredentials(true);
//    }

    @Bean
    public AuthProvider authProvider(){
        return new AuthProvider();
    }

    @Bean
    public LoginUrlEntryPoint loginUrlEntryPoint(){
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public LoginAuthFailHandler loginAuthFailHandler(){
        return new LoginAuthFailHandler(loginUrlEntryPoint());
    }
}
