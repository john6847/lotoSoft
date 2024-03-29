package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Notification;
import com.b.r.loteriab.r.Model.NumberTwoDigits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByEnterpriseIdAndUsersIdAndReadFalse(Long enterpriseId, Long userId);
    List<Notification> findAllByEnterpriseIdAndReadFalse(Long enterpriseId);
}
