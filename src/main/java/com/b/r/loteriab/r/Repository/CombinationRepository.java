package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Combination;
import com.b.r.loteriab.r.Model.CombinationType;
import com.b.r.loteriab.r.Model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface CombinationRepository extends JpaRepository<Combination, Long> {

    Combination findCombinationById(Long id);

//    List<Combination> findByNumberThreeDigits_NumberInStringFormat(Pageable pageable);

//    String q1 = "SELECT " +
//            "       c.ID as combinationId,c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED, " +
//            "       n.NUMBER_IN_STRING_FORMAT as numberThreeDigits, n.ID as numberThreeDigitsId,  " +
//            "       ct.PRODUCTS_ID as productsId, p.NAME as productsName " +
//            "FROM combination c " +
//            "INNER JOIN number_three_digits n ON n.id = c.number_three_digits_id " +
//            "INNER JOIN combination_type ct ON ct.id = c.combination_type_id " +
//            "INNER JOIN products p ON ct.products_id = p.id " +
//            "WHERE n.number_in_string_format LIKE ?1 || '%' AND p.name = ?3 AND c.enterprise_id=?4 ORDER BY c.sequence " +
//            "LIMIT ?2";
//
//    @Query(value = q1, nativeQuery = true)
//    <T>List<T>selectAllByNumberThreeDigitsByEnterpriseId(String numberInStringFormat, int limit, String type, Long enterpriseId, Class<T> classType);

//
//    String q2 = "SELECT " +
//            "       c.ID as combinationId, c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED, " +
//            "       n.Id as numberTwoDigitsId, n.NUMBER_IN_STRING_FORMAT as numberTwoDigits, " +
//            "       ct.PRODUCTS_ID as productsId, p.NAME as productsName " +
//            "FROM combination c " +
//            "INNER JOIN number_two_digits n ON n.id = cn.number_two_digits_id " +
//            "INNER JOIN combination_number_two_digits cn ON cn.combination_id = c.id " +
//            "INNER JOIN combination_type ct ON ct.id = c.combination_type_id " +
//            "INNER JOIN products p ON ct.products_id = p.id " +
//            "WHERE n.number_in_string_format LIKE ?1 || '%' AND p.name = ?3 AND c.enterprise_id=?4 ORDER BY c.sequence " +
//            "LIMIT ?2";
//
//    @Query(value = q2, nativeQuery = true)
//    <T>List<T> selectAllByNumberTwoDigitsByEnterpriseId(String numberInStringFormat, int limit, String type, Long enterpriseId, Class<T> classType);
    <T>List<T> findAllByResultCombinationAndCombinationTypeIdAndEnterpriseId(String resultCombination, Long combinationTypeId, Long enterpriseId, Class<T> classType); // bolet loto3 extra and maryaj when equal



//    String q3 ="SELECT\n" +
//        "             c.ID as combinationId, c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED,\n" +
//        "             nd.Id as numberTwoDigitsId, nd.NUMBER_IN_STRING_FORMAT as numberTwoDigits,\n" +
//        "             nt.ID as numberThreeDigitsId, nt.NUMBER_IN_STRING_FORMAT as numberThreeDigits,\n" +
//        "             ct.PRODUCTS_ID as productsId, p.NAME as productsName\n" +
//        "   FROM COMBINATION c\n" +
//        "             INNER JOIN combination_number_two_digits cn\n" +
//        "                                ON cn.combination_id = c.id\n" +
//        "             INNER JOIN number_three_digits nt\n" +
//        "                                ON nt.id = c.number_three_digits_id\n" +
//        "             INNER JOIN number_two_digits nd\n" +
//        "                                ON nd.id = cn.number_two_digits_id\n" +
//        "             INNER JOIN combination_type ct\n" +
//        "                                ON ct.id = c.combination_type_id\n" +
//        "             INNER JOIN products p\n" +
//        "                                ON ct.products_id = p.id\n" +
//        "            WHERE nt.NUMBER_IN_STRING_FORMAT LIKE ?2 || '%'  AND NOT EXISTS (SELECT * FROM NUMBER_TWO_DIGITS n\n" +
//        "                              WHERE n.NUMBER_IN_STRING_FORMAT IN (?1)\n" +
//        "                                AND NOT EXISTS\n" +
//        "                                  (SELECT * FROM COMBINATION_NUMBER_TWO_DIGITS cn\n" +
//        "                                   WHERE cn.COMBINATION_ID = c.id AND cn.enterprise_id=?5 \n" +
//        "                                     AND cn.NUMBER_TWO_DIGITS_ID = n.id))\n" +
//        "                                       AND p.name = ?4 AND c.enterprise_id=?5 ORDER BY c.sequence LIMIT ?3";
//
//    @Query(value = q3, nativeQuery = true)
//    <T>List<T> selectAllByNumberTwoDigitsAndNumberThreeDigitsByEnterpriseId(String numberInStringFormatTwoDigits, String numberInStringFormatThreeDigits, int limit, String type, Long enterpriseId, Class<T> classType);

    String q4 ="SELECT   " +
            "            c.ID as combinationId, c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED, " +
            "            nd.Id as numberTwoDigitsId, nd.NUMBER_IN_STRING_FORMAT as numberTwoDigits, " +
            "            ct.PRODUCTS_ID as productsId, p.NAME as productsName " +
            "FROM COMBINATION c " +
            "         INNER JOIN combination_number_two_digits cn  " +
            "                    ON cn.combination_id = c.id " +
            "         INNER JOIN number_two_digits nd " +
            "                    ON nd.id = cn.number_two_digits_id " +
            "         INNER JOIN combination_type ct  " +
            "                    ON ct.id = c.combination_type_id " +
            "         INNER JOIN products p " +
            "                    ON ct.products_id = p.id " +
            "WHERE NOT EXISTS (SELECT * FROM NUMBER_TWO_DIGITS n " +
            "                  WHERE n.NUMBER_IN_STRING_FORMAT IN (?1, ?2) " +
            "                    AND NOT EXISTS " +
            "                      (SELECT * FROM COMBINATION_NUMBER_TWO_DIGITS cn " +
            "                       WHERE cn.COMBINATION_ID = c.id AND cn.enterprise_id=?5 " +
            "                         AND cn.NUMBER_TWO_DIGITS_ID = n.id)) " +
            "                           AND p.name =?4 AND c.enterpriseId=?5 ORDER BY c.sequence LIMIT ?3";

    @Query(value = q4, nativeQuery = true)
    <T>List<T> selectAllByNumberTwoDigitsAndNumberTwoDigitsByEnterpriseId(String numberInStringFormat, String numberInStringFormat1, int limit, String type, Long enterpriseId, Class<T> classType);

    String q5 ="SELECT  " +
            "            c.ID as combinationId, c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED, " +
            "            nd.Id as numberTwoDigitsId, nd.NUMBER_IN_STRING_FORMAT as numberTwoDigits, " +
            "            ct.PRODUCTS_ID as productsId, p.NAME as productsName " +
            "FROM COMBINATION c " +
            "         INNER JOIN combination_number_two_digits cn " +
            "                    ON cn.combination_id = c.id " +
            "         INNER JOIN number_two_digits nd " +
            "                    ON nd.id = cn.number_two_digits_id " +
            "         INNER JOIN combination_type ct  " +
            "                    ON ct.id = c.combination_type_id " +
            "         INNER JOIN products p " +
            "                    ON ct.products_id = p.id " +
            "WHERE NOT EXISTS (SELECT * FROM NUMBER_TWO_DIGITS n " +
            "                  WHERE n.NUMBER_IN_STRING_FORMAT IN (?1, ?2) " +
            "                    AND NOT EXISTS " +
            "                      (SELECT * FROM COMBINATION_NUMBER_TWO_DIGITS cn " +
            "                       WHERE cn.COMBINATION_ID = c.id AND cn.enterprise_id=?6 " +
            "                         AND cn.NUMBER_TWO_DIGITS_ID = n.id)) " +
            "                           AND (p.name =?4 OR p.name = ?5) AND c.enterprise_id=?6 ORDER BY c.sequence LIMIT ?3 ";

    @Query(value = q5, nativeQuery = true)
    <T>List<T> selectAllByNumberTwoDigitsAndNumberTwoDigitsAndTypeAndEnterpriseId(String numberInStringFormat, String numberInStringFormat1, int limit, String type1, String type2, Long enterpriseId, Class<T> classType);

    String q6 ="SELECT\n" +
            "    c.ID as combinationId, c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED,\n" +
            "    nd.Id as numberTwoDigitsId, nd.NUMBER_IN_STRING_FORMAT as numberTwoDigits,\n" +
            "    ct.PRODUCTS_ID as productsId, p.NAME as productsName\n" +
            "FROM COMBINATION c\n" +
            "    INNER JOIN combination_number_two_digits cn\n" +
            "ON cn.combination_id = c.id\n" +
            "    INNER JOIN number_two_digits nd\n" +
            "    ON nd.id = cn.number_two_digits_id\n" +
            "    INNER JOIN combination_type ct\n" +
            "    ON ct.id = c.combination_type_id\n" +
            "    INNER JOIN products p\n" +
            "    ON ct.products_id = p.id\n" +
            "\n" +
            "    WHERE nd.NUMBER_IN_STRING_FORMAT IN (?1)\n" +
            "    AND (p.name =?2 OR p.name = ?3) AND c.enterprise_id=?4\n" +
            "GROUP BY combinationId\n" +
            "HAVING COUNT(combinationId) = 2";

    @Query(value = q6, nativeQuery = true)
    <T>List<T> selectAllBySameNumberTwoDigitsAndNumberTwoDigitsAndTypeLOTOANDOPSYONAndEnterpriseId(String numberInStringFormat, String type1, String type2, Long enterpriseId, Class<T> classType);
//    List<Combination> findAllByResultCombinationOrResultCombinationAndCombinationTypeIdOrCombinationTypeIdAndEnterpriseId(String resultCombination, String reverseResultCombination, Long combinationTypeId1, Long combinationTypeId2, Long enterpriseId);


    String q7 ="SELECT\n" +
            "    c.ID as combinationId, c.MAX_PRICE as maxPrice, c.COMBINATION_TYPE_ID as combinationTypeId, c.ENABLED,\n" +
            "    nd.Id as numberTwoDigitsId, nd.NUMBER_IN_STRING_FORMAT as numberTwoDigits,\n" +
            "    ct.PRODUCTS_ID as productsId, p.NAME as productsName\n" +
            "FROM COMBINATION c\n" +
            "    INNER JOIN combination_number_two_digits cn\n" +
            "ON cn.combination_id = c.id\n" +
            "    INNER JOIN number_two_digits nd\n" +
            "    ON nd.id = cn.number_two_digits_id\n" +
            "    INNER JOIN combination_type ct\n" +
            "    ON ct.id = c.combination_type_id\n" +
            "    INNER JOIN products p\n" +
            "    ON ct.products_id = p.id\n" +
            "\n" +
            "    WHERE nd.NUMBER_IN_STRING_FORMAT IN (?1)\n" +
            "    AND (p.name =?2) AND c.enterprise_id=?3\n" +
            "GROUP BY combinationId\n" +
            "HAVING COUNT(combinationId) = 2";

    @Query(value = q7, nativeQuery = true)
    <T>List<T> selectAllBySameNumberTwoDigitsAndNumberTwoDigitsAndTypeMARYAJAndEnterpriseId(String numberInStringFormat, String type1, Long enterpriseId, Class<T> classType);


    <T>List<T> findAllByResultCombinationOrResultCombinationAndCombinationTypeIdOrCombinationTypeIdAndEnterpriseId(String resultCombination, String reverseResultCombination, Long combinationTypeId1, Long combinationTypeId2, Long enterpriseId, Class<T> classType); // loto and opsyon when number are different
    <T>List<T> findAllByResultCombinationAndCombinationTypeIdOrCombinationTypeIdAndEnterpriseId(String resultCombination, Long combinationTypeId1, Long combinationTypeId2, Long enterpriseId, Class<T> classType); // loto and opsyon when number are the same
    <T>List<T> findAllByResultCombinationOrResultCombinationAndCombinationTypeIdAndEnterpriseId(String resultCombination, String reverseResultCombination, Long combinationTypeId1, Long enterpriseId, Class<T> classType); //Maryaj


//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and c.RESULT_COMBINATION = '44' and  c.COMBINATION_TYPE_ID = 1;borlet
//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and c.RESULT_COMBINATION = '944' and  c.COMBINATION_TYPE_ID = 2; Loto 3
//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and (c.RESULT_COMBINATION = '05 44' or c.RESULT_COMBINATION ='44 05') and  (c.COMBINATION_TYPE_ID = 3 or c.COMBINATION_TYPE_ID = 4); Loto and Opsyon

//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and c.RESULT_COMBINATION = '22 22' and  (c.COMBINATION_TYPE_ID = 3 or c.COMBINATION_TYPE_ID = 4); Loto and Opsyon
//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and (c.RESULT_COMBINATION = '05x44' or c.RESULT_COMBINATION ='44x05') and  c.COMBINATION_TYPE_ID = 5;Maryaj
//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and c.RESULT_COMBINATION = '44x44' and  c.COMBINATION_TYPE_ID = 5;Maryaj
//    select * from COMBINATION c where c.ENTERPRISE_ID = 2 and c.RESULT_COMBINATION = '944 05' and  c.COMBINATION_TYPE_ID = 6; extra

    List<Combination> findAllByCombinationTypeProductsNameAndEnterpriseId(String Name, Long enterpriseId); // TODO:

    List<Combination> findAllByEnabledAndEnterpriseId(boolean enabled, Long enterpriseId);

    List<Combination> findByEnterpriseIdAndCombinationTypeProductsNameOrderBySequenceDesc(Long enterpriseId, String name );

//
//    String q1 = " SELECT sequence FROM combination " +
//            "    ORDER BY sequence DESC   " +
//            "LIMIT 1 where combination_type_products_name = ?1";
//    @Query(value = q1, nativeQuery = true)
//    Combination selectCombinationByCombinationTypeProductsName(String name);//

    Combination save (Combination combination);

    @Modifying
    @Query("UPDATE Combination c SET c.maxPrice = :maxPrice, c.enabled= :enabled WHERE c.combinationType = :combinationType AND c.enterprise= :enterprise")
    int updateCombinationMaxPrice(@Param("combinationType") CombinationType combinationType, @Param("enabled") boolean enabled, @Param("maxPrice") double maxPrice, @Param("enterprise")Enterprise enterprise);

    @Modifying
    @Query("UPDATE Combination c SET c.enabled = :enabled  WHERE c.combinationType  = :combinationType AND c.enterprise= :enterprise")
    int updateCombinationState(@Param("combinationType") CombinationType combinationType, @Param("enabled") boolean enabled, @Param("enterprise")Enterprise enterprise);

    Combination findByResultCombinationAndCombinationTypeIdAndEnterpriseId(String resultCombination, long combinationTypeId, Long enterpriseId);
}

