package com.julianduru.email.emailservice.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
@EnableConfigurationProperties({ArtemisProperties.class})
public class JmsConfig {



    @Bean
    @Qualifier("artemisConnectionFactory")
    public ConnectionFactory artemisConnectionFactory(ArtemisProperties artemisProperties) throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();

        cf.setBrokerURL(
            String.format(
                "tcp://%s:%d",
                artemisProperties.getHost(), artemisProperties.getPort()
            )
        );
        cf.setUser(artemisProperties.getUser());
        cf.setPassword(artemisProperties.getPassword());

        return cf;
    }


    @Bean
    public JmsComponent jmsComponent(
        @Qualifier("artemisConnectionFactory") ConnectionFactory connectionFactory
    ) throws JMSException {
        JmsComponent jms = new JmsComponent();
        jms.setConnectionFactory(connectionFactory);
        return jms;
    }


}