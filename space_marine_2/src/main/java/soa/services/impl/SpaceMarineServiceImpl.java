package soa.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soa.exception.NotFoundException;
import soa.model.SpaceMarine;
import soa.services.SpaceMarineService;

import java.util.Optional;

@Service
public class SpaceMarineServiceImpl implements SpaceMarineService {
    private final RestTemplate restTemplate;

    private final String endPoint;

    public SpaceMarineServiceImpl(RestTemplate restTemplate,
                                  String endPoint) {
        this.restTemplate = restTemplate;
        this.endPoint = endPoint;
    }

    public Optional<SpaceMarine> getSpaceMarine(Integer spaceMarineId){
        try {
            return Optional.ofNullable(restTemplate.getForObject(endPoint + "space/marine/" + spaceMarineId, SpaceMarine.class));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
