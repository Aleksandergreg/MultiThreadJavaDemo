package com.example.multithreadjavademo;

import com.example.multithreadjavademo.dto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RemoteApiTester implements CommandLineRunner {

    private Mono<String> callSlowEndpoint() {
        Mono<String> slowResponse = WebClient.create()
                .get()
                .uri("http://localhost:8080/random-string-slow")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> System.out.println("UUUPS : " + e.getMessage()));
        return slowResponse;
    }

    public void callEndpointBlocking() {
        long start = System.currentTimeMillis();
        List<String> ramdomStrings = new ArrayList<>();

        Mono<String> slowResponse = callSlowEndpoint();
        ramdomStrings.add(slowResponse.block()); //Three seconds spent

        slowResponse = callSlowEndpoint();
        ramdomStrings.add(slowResponse.block());//Three seconds spent

        slowResponse = callSlowEndpoint();
        ramdomStrings.add(slowResponse.block());//Three seconds spent
        long end = System.currentTimeMillis();
        ramdomStrings.add(0, "Time spent BLOCKING (ms): " + (end - start));

        //System.out.println(ramdomStrings.stream().collect(Collectors.joining(",")));
        System.out.println(String.join(",", ramdomStrings));
    }

    public void callSlowEndpointNonBlocking() {
        long start = System.currentTimeMillis();
        Mono<String> sr1 = callSlowEndpoint();
        Mono<String> sr2 = callSlowEndpoint();
        Mono<String> sr3 = callSlowEndpoint();

        var rs = Mono.zip(sr1, sr2, sr3).map(tuple3 -> {
            List<String> randomStrings = new ArrayList<>();
            randomStrings.add(tuple3.getT1());
            randomStrings.add(tuple3.getT2());
            randomStrings.add(tuple3.getT3());
            long end = System.currentTimeMillis();
            randomStrings.add(0, "Time spent NON-BLOCKING (ms): " + (end - start));
            return randomStrings;
        });
        List<String> randoms = rs.block(); //We only block when all the three Mono's has fulfilled
        System.out.println(randoms.stream().collect(Collectors.joining(",")));
    }

    Mono<Gender> getGenderForName(String name) {
        WebClient client = WebClient.create();
        Mono<Gender> gender = client.get()
                .uri("https://api.genderize.io?name=" + name)
                .retrieve()
                .bodyToMono(Gender.class);
        return gender;
    }

    private final WebClient webClient = WebClient.create();

    public Mono<GenderDTO> getGender(String name) {
        return webClient.get()
                .uri("https://api.genderize.io?name=" + name)
                .retrieve()
                .bodyToMono(GenderDTO.class);
    }

    public Mono<AgeDTO> getAge(String name) {
        return webClient.get()
                .uri("https://api.agify.io?name=" + name)
                .retrieve()
                .bodyToMono(AgeDTO.class);
    }

    public Mono<NationalityDTO> getNationality(String name) {
        return webClient.get()
                .uri("https://api.nationalize.io?name=" + name)
                .retrieve()
                .bodyToMono(NationalityDTO.class);
    }

    public Mono<NameInfoDTO> getNameInfo(String name) {
        Mono<GenderDTO> genderMono = getGender(name);
        Mono<AgeDTO> ageMono = getAge(name);
        Mono<NationalityDTO> nationalityMono = getNationality(name);

        return Mono.zip(genderMono, ageMono, nationalityMono)
                .map(tuple -> {
                    GenderDTO genderData = tuple.getT1();
                    AgeDTO ageData = tuple.getT2();
                    NationalityDTO nationalityData = tuple.getT3();

                    NameInfoDTO nameInfo = new NameInfoDTO();
                    nameInfo.setName(name);
                    nameInfo.setGender(genderData.getGender());
                    nameInfo.setGenderProbability(genderData.getProbability() * 100); // as a percentage
                    nameInfo.setAge(ageData.getAge());
                    nameInfo.setAgeCount(ageData.getCount());

                    if (!nationalityData.getCountry().isEmpty()) {
                        NationalityDTO.Country country = nationalityData.getCountry().get(0); //Taking the most popular country
                        nameInfo.setCountry(country.getCountry_id());
                        nameInfo.setCountryProbability(country.getProbability() * 100); // as a percentage
                    }

                    return nameInfo;
                });
    }

//    public Mono<NameInfoDTO> getNameInfo1(String name){
//        Flux<Object> combinedResult = Flux.merge(
//                getGender(name),
//                getAge(name),
//                getNationality(name)
//        );
//        return combinedResult.collectList().map(results ->{
//            NameInfoDTO nameInfo = new NameInfoDTO();
//            nameInfo.setName(name);
//        })
//
//    }


    @Override
    public void run(String... args) throws Exception {
//            System.out.println(callSlowEndpoint().toString());
//            String randomStr = callSlowEndpoint().block();
//              System.out.println(randomStr);
           // callEndpointBlocking();
//        callSlowEndpointNonBlocking();
       //Gender gender = getGenderForName("Aleksander").block();
        //System.out.println("Gender for person : " + gender);

    }
}


//https://api.genderize.io/?name=Aleksander
