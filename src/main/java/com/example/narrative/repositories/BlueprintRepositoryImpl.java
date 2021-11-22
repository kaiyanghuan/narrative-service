package com.example.narrative.repositories;

import com.example.narrative.controllers.requests.AppPageRequest;
import com.example.narrative.controllers.requests.BlueprintQueryRequest;
import com.example.narrative.entities.Blueprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlueprintRepositoryImpl implements BlueprintRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Blueprint> findAllByBlueprintQueryRequest(BlueprintQueryRequest blueprintQueryRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Blueprint> criteriaQuery = criteriaBuilder.createQuery(Blueprint.class);

        Root<Blueprint> blueprintRoot = criteriaQuery.from(Blueprint.class);
        List<Predicate> predicates = new ArrayList<>();

        if (blueprintQueryRequest.getName() != null) {
            predicates.add(criteriaBuilder.like(blueprintRoot.<String>get("name"),  "%"+ blueprintQueryRequest.getName() + "%"));
        }

        if (blueprintQueryRequest.getTags() != null) {
            blueprintQueryRequest.getTags().forEach(tag -> {
                predicates.add(criteriaBuilder.like(blueprintRoot.<String>get("tags"),  "%\""+ tag + "\"%"));
            });
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public Page<Blueprint> findAllPageByBlueprintQueryRequest(BlueprintQueryRequest blueprintQueryRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Blueprint> criteriaQuery = criteriaBuilder.createQuery(Blueprint.class);

        Root<Blueprint> blueprintRoot = criteriaQuery.from(Blueprint.class);
        List<Predicate> predicates = new ArrayList<>();

        if (blueprintQueryRequest.getName() != null) {
            predicates.add(criteriaBuilder.equal(blueprintRoot.get("name"), blueprintQueryRequest.getName()));
        }

        if (blueprintQueryRequest.getTags() != null) {
            blueprintQueryRequest.getTags().forEach(tag -> {
                predicates.add(criteriaBuilder.in(blueprintRoot.get("tags")).value(tag));
            });

        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        AppPageRequest pageRequest = blueprintQueryRequest.getPageRequest();
        Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getDirection(), pageRequest.getField());
        Query query = entityManager.createQuery(criteriaQuery);
        return new PageImpl<>(query.getResultList(), pageable, pageRequest.getTotal());
    }
}
