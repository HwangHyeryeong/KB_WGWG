package kb.wgwg.banking.repository;

import kb.wgwg.banking.domain.Banking;
import kb.wgwg.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BankingRepository extends JpaRepository<Banking, Long> {

    @Query(value = "select * from banking b where extract(year from b.banking_date) = ?1 AND extract(month from b.banking_date) = ?2 AND b.user_seq = ?3 order by b.banking_date", nativeQuery = true)
    Page<Banking> findMonth(int year, int month, Long userSeq, Pageable pageable);


    @Query(value = "SELECT category, SUM(amount) AS TOTAL " +
            "FROM banking b " +
            "JOIN user_entity u ON b.user_seq = u.user_seq " +
            "WHERE u.user_seq = :userSeq " +
            "AND b.banking_date BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') " +
            "AND b.type = '지출' "+
            "GROUP BY b.category " +
            "ORDER BY TOTAL DESC" , nativeQuery = true)
    List<Map<String, Integer>> readCategoryProportion(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("userSeq") Long userSeq);

//    @Query(value = "SELECT category, SUM(amount) AS TOTAL " +
//            "FROM banking b " +
//            "JOIN user_entity u ON b.user_seq = u.user_seq " +
//            "WHERE u.user_seq = :userSeq " +
//            "AND b.banking_date BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') " +
//            "AND b.type = '지출' "+
//            "GROUP BY b.category " +
//            "ORDER BY TOTAL DESC" , nativeQuery = true)
//    List<Map<String, Integer>> readCategoryProportion(
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            @Param("userSeq") Long userSeq);

    @Query(value = "SELECT b.* " +
            "FROM banking b " +
            "JOIN user_entity u ON b.user_seq = u.user_seq " +
            "WHERE b.banking_date BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') " +
            "AND u.user_seq = :userSeq", nativeQuery = true)
    List<Banking> findByBankingDateBetweenAndUserSeq(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("userSeq") Long userSeq
    );
    List<Banking> findAllByOwnerAndTypeAndCategory(User theUser, String type, String category);
    List<Banking> findAllByOwnerAndCategoryAndTypeIn(User theUser, String category, List<String> types);
}
