package io.github.ppp16bit.Magalu.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import io.github.ppp16bit.Magalu.entity.ChannelValues;
import io.github.ppp16bit.Magalu.entity.StatusValues;
import io.github.ppp16bit.Magalu.repository.ChannelRepository;
import io.github.ppp16bit.Magalu.repository.StatusRepository;

@Configuration
public class DataLoad implements CommandLineRunner {
    private final ChannelRepository channelRepository;
    private final StatusRepository statusRepository;

    public DataLoad(ChannelRepository channelRepository, StatusRepository statusRepository) {
        this.channelRepository = channelRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(ChannelValues.values())
                .map(ChannelValues::toChannel)
                .forEach(channelRepository::save);
                
        Arrays.stream(StatusValues.values())
                .map(StatusValues::toStatus)
                .forEach(statusRepository::save);
    }
}