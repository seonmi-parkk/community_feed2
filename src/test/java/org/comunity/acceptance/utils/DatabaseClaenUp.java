package org.comunity.acceptance.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("test")
@Component
@Slf4j
public class DatabaseClaenUp  implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;
    private List<String> tableNames;
    private List<String> notGereratedIdTableNames;

    @Override
    //afterPropertiesSet() 은 빈팩토리에 의해서 모든 빈들이 설정되고 난 후 실행되는 메서드
    public void afterPropertiesSet() throws Exception {
        // 엔티티매니저로 엔티티들을 가져와서 필터로 엔티티라는 어노테이션이 있는 것들만 남기고
        // 이 엔티티들에서 테이블 이름을 가져와서 리스트에 저장
        tableNames = entityManager.getMetamodel().getEntities()
                .stream()
                .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
                .map(entity -> entity.getJavaType().getAnnotation(Table.class).name())
                .toList();

        // 자동 생성되는 id(autoIncrement) 값들을 전부 1로 초기화 해줘야 항상 같은 환경에서 테스트를 진행할 수 있음.
        notGereratedIdTableNames = List.of("community_user_auth","community_user_relation", "community_like");
    }

    @Transactional
    // 모든 테이블을 truncate 시키고 autoIncrement id 값을 1로 초기화 시켜주어
    // 여러번 테스트를 진행하거나 여러개를 한번에 진행해도 항상 같은 환경에서 테스트 진행 가능
    public void execute() {
        // flush()로 혹시 모를 실행 안된 쿼리들을 처리
        entityManager.flush();
        // referential integrity를 false로 바꿔서 데이블 데이터 삭제 전에
        // 외래키 제약조건 등을 해제하여 테이블 삭제에 제한이 걸리지 않도록 함.
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for(String tableName : tableNames) {
            // 테이블을 truncate 시키고
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            if(!notGereratedIdTableNames.contains(tableName)){
                // autoIncrement 사용하는 테이블 id 값을 1로 초기화 시켜줌
                entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
            }
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
