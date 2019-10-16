package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.GlobalConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface GlobalConfigurationRepository extends JpaRepository<GlobalConfiguration, Long> {
    GlobalConfiguration save(GlobalConfiguration globalConfiguration);
    GlobalConfiguration findByEnterpriseId(Long enterpriseId);
}
