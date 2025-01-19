package org.example.be.domain.dto.searchDto;

import java.util.ArrayList;
import java.util.List;
import org.example.be.domain.Product;
import org.example.be.domain.dto.searchDto.SearchRequestDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class SearchSpecification {

    public static Specification<Product> searchWith(SearchRequestDto request) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 1) 키워드(상품명) 검색 (부분일치, 대소문자 무시)
            if (StringUtils.hasText(request.getKeyword())) {
                String kw = request.getKeyword().toLowerCase();
                predicates.add(builder.like(
                    builder.lower(root.get("name")),
                    "%" + kw + "%"
                ));
            }

            // 2) 메인카테고리(in)
            if (!CollectionUtils.isEmpty(request.getMainCategory())) {
                // root.get("mainCategory")가 리스트 안에 하나라도 매칭
                Expression<String> mainCatExp = root.get("mainCategory");
                predicates.add(mainCatExp.in(request.getMainCategory()));
            }

            // 3) 서브카테고리(in)
            if (!CollectionUtils.isEmpty(request.getSubCategory())) {
                Expression<String> subCatExp = root.get("subCategory");
                predicates.add(subCatExp.in(request.getSubCategory()));
            }

            // 4) 배송방식(in)
            if (!CollectionUtils.isEmpty(request.getDelivery())) {
                Expression<String> deliveryExp = root.get("delivery");
                predicates.add(deliveryExp.in(request.getDelivery()));
            }

            // 5) 가격 최소/최대
            if (request.getPriceMin() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), request.getPriceMin()));
            }
            if (request.getPriceMax() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), request.getPriceMax()));
            }

            // 최종 AND로 묶음
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
