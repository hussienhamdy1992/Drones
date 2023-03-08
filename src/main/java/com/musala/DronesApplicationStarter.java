package com.musala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class DronesApplicationStarter
{
    public static void main( String[] args )
    {

        new SpringApplicationBuilder(DronesApplicationStarter.class).run(args);

    }


    @Component
    class Default implements CommandLineRunner {

        @Autowired
        private Environment environment;

        @Override
        public void run(String... args) throws Exception {

            System.out.println("Active profiles: " +
                    Arrays.toString(environment.getActiveProfiles()));
        }
    }

    @Component
    @Profile(value="dev")
    class DevProfile implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {

            System.out.println("In development");
        }
    }

    @Component
    @Profile(value="prod")
    class ProdProfile implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {

            System.out.println("In production");
        }
    }
}
