package com.b.r.loteriab.r.Model.Filter.Specification;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Filter.Dto.PosListRequest;
import com.b.r.loteriab.r.Model.Pos;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class PosListSpecification extends BaseSpecification<Pos, PosListRequest> {


    @Override
    public Specification<Pos> getFilter(PosListRequest request) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(descriptionContains(request.getDescription()))
                    .and(serialContains(request.getSerial()))
                    .and(creationDateContains(request.getCreationDate()))
                    .and(idContains(request.getEnterpriseId()))
                    .and(isEnabled(request.getEnabled()))
                    .toPredicate(root, query, cb);
        };

    }

    private Specification<Pos> descriptionContains(String description) {
        return posAttributeContains("description", description);
    }

    private Specification<Pos> serialContains(String serial) {
        return posAttributeContains("serial", serial);
    }

    private Specification<Pos> creationDateContains(String creationDate) {
        return posAttributeContains("creationDate", creationDate);
    }

    private Specification<Pos> posAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Pos> posAttributeIs(Boolean value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            if (value){
                return cb.isTrue(
                        root.get("enabled")
                );
            } else {
                return cb.isFalse(
                        root.get("enabled")
                );
            }

        };
    }


    private Specification<Pos> isEnabled(Boolean enabled) {
        return posAttributeIs(enabled);
    }

    private Specification<Pos> idContains(String id) {
        return enterpriseAttributeContains(id);
    }

    private Specification<Pos> enterpriseAttributeContains(String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            Join<Pos, Enterprise> enterprise = root.join("enterprise");

            return cb.like(
                    cb.lower(enterprise.get("id")),
                    containsLowerCase(value)
            );
        };
    }
}
