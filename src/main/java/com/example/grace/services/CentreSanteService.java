package com.example.grace.services;

import com.example.grace.entities.CentreSante;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CentreSanteService {

    void createCentreSante(CentreSante model);
    List<CentreSante> getCentreSantes();
    CentreSante getOneCentreSante(long id);
    Map<String, Object> findAllCentreSantes(int page, int size);
    public Page<CentreSante> getCentreSantes(int page, int size);
    void delete(long id);

//    Site update(Site site);

    CentreSante updateSite(CentreSante model, Long id);
}
