package net.ddns.jaronsky.debter.config

import net.ddns.jaronsky.debter.security.JwtAuthenticationEntryPoint
import net.ddns.jaronsky.debter.security.JwtAuthorizationTokenFilter
import net.ddns.jaronsky.debter.security.properties.JwtProperties
import net.ddns.jaronsky.debter.security.service.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.StandardPasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*


@Configuration
@EnableWebSecurity
//@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class WebSecurityConfig(
        private val unauthorizedHandler: JwtAuthenticationEntryPoint,
        private val jwtUserDetailsService: JwtUserDetailsService,
        internal var authenticationTokenFilter: JwtAuthorizationTokenFilter,
        @Suppress("SpringJavaInjectionPointsAutowiringInspection") internal var jwtProperties: JwtProperties

) : WebSecurityConfigurerAdapter() {


    // Custom JWT based security filter


    //    @Value("${jwt.header}")
    //    private String tokenHeader;
    //
    //    @Value("${jwt.route.authentication.path}")
    //    private String authenticationPath;




    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
                .userDetailsService<JwtUserDetailsService>(jwtUserDetailsService)
                .passwordEncoder(passwordEncoderBean())
    }

    @Bean
    fun passwordEncoderBean(): PasswordEncoder {
        val defaultEncoder = StandardPasswordEncoder()
        val encoders = HashMap<String, PasswordEncoder>()
        encoders["bcrypt"] = BCryptPasswordEncoder()
        encoders["scrypt"] = SCryptPasswordEncoder()

        val passworEncoder = DelegatingPasswordEncoder(
                "bcrypt", encoders)
        passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder)

        return passworEncoder


    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
//        httpSecurity
//                .requiresChannel()
//                .antMatchers("/**").requiresSecure()

        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .cors().and()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // Un-secure H2 Database
                .antMatchers("/h2/**/**").permitAll()

                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/users/register").permitAll()
                .anyRequest().authenticated()

        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        // disable page caching
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        // AuthenticationTokenFilter will ignore the below paths
        web!!
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        jwtProperties.route.authentication.path
//                        jwtProperties.routeAuthPath
                )

                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                )

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/h2/**/**")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource{
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}
