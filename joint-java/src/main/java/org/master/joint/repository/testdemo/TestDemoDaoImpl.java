package org.master.joint.repository.testdemo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/24
 * Modified By:
 */
public class TestDemoDaoImpl implements TestDemoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

}
