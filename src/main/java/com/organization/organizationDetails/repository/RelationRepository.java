package com.organization.organizationDetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organization.organizationDetails.model.Relation;



@Repository
public interface RelationRepository extends JpaRepository<Relation, Integer> {

}
