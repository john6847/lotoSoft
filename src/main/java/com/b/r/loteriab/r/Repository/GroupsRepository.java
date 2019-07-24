package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    Groups findGroupsById(Long id);
    Groups findGroupsByIdAndEnabled(Long id, boolean enabled);
    Page<Groups> findAll(Pageable pageable);
    Page<Groups> findAllByEnabled(Pageable pageable, boolean enabled);
    List<Groups> findAllByEnabled(boolean enabled);


    void deleteById(Long id);

    Groups findByParentSellerId(Long id);

/*    String q1 = "SELECT * FROM seller s INNER JOIN groups g ON g.id = s.groups_id" +
            "WHERE g.id NOT IN (SELECT sel.group_id FROM seller sel) and g.parent_seller_id IS NULL";
    @Query(value = q1, nativeQuery = true)
    List<Groups> selectGroups(String name, boolean enabled);//*/

    Groups save(Groups groups);
}
